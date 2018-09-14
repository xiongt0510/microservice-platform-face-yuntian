package com.anjuxing.platform.face.yuntian.Service.impl;

import com.anjuxing.platform.face.yuntian.Service.LocalTokenService;
import com.anjuxing.platform.face.yuntian.Service.RedisRepository;
import com.anjuxing.platform.face.yuntian.Service.remote.RemoteTokenService;
import com.anjuxing.platform.face.yuntian.model.Token;
import com.anjuxing.platform.face.yuntian.properties.ClientProperties;
import com.anjuxing.platform.face.yuntian.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author xiongt
 * @Description
 */
@Service
public class LocalTokenServiceImpl implements LocalTokenService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RemoteTokenService remoteTokenService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RedisRepository redis;

    @Override
    public Token getLocalToken(ClientProperties client)  {

        String clientId = client.getClientId();
        String clientSecret = client.getClientSecret();
        String grantType = client.getGrantType();
        String username = client.getUsername();
        String password = client.getPassword();
        String scope = client.getScope();

        String authorization = StringUtils.getBasicAuthorization(clientId,clientSecret);

        String data = remoteTokenService.getToken(grantType,username,password,scope
                ,clientId,clientSecret,authorization);

        logger.info("return remote data :" + data);

        Token token = null;
        try {
            token = mapper.readValue(data,Token.class);
        } catch (IOException e) {
            throw new  RuntimeException("获取token 异常!");
        }

        //如果redis 中没有存储就存到redis 中
        if (org.apache.commons.lang3.StringUtils.isEmpty(redis.getAccessToken())){
            redis.save(token);
        }

        return token;
    }
}
