package com.anjuxing.platform.face.yuntian.Service.impl;

import com.anjuxing.platform.face.yuntian.Service.RemoteService;
import com.anjuxing.platform.face.yuntian.Service.TokenService;
import com.anjuxing.platform.face.yuntian.Service.RedisRepository;
import com.anjuxing.platform.face.yuntian.Service.remote.RemoteTokenService;
import com.anjuxing.platform.face.yuntian.model.Token;
import com.anjuxing.platform.face.yuntian.properties.ClientProperties;
import com.anjuxing.platform.face.yuntian.util.HttpClientUtils;
import com.anjuxing.platform.face.yuntian.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongt
 * @Description
 */
@Service
public class TokenServiceImpl implements TokenService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RemoteTokenService remoteService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RedisRepository redis;

    @Override
    public Token getToken(ClientProperties client) throws IOException {

        String clientId = client.getClientId();
        String clientSecret = client.getClientSecret();
        String grantType = client.getGrantType();
        String username = client.getUsername();
        String password = client.getPassword();
        String scope = client.getScope();

        String authorization = StringUtils.getBasicAuthorization(clientId,clientSecret);

        List<NameValuePair> pairs = new ArrayList<>();

        NameValuePair idPair = new BasicNameValuePair("client_id", clientId);
        NameValuePair secretPair = new BasicNameValuePair("client_secret", clientSecret);
        NameValuePair grantPair = new BasicNameValuePair("grant_type", grantType);
        NameValuePair usernamePair = new BasicNameValuePair("username", username);
        NameValuePair passwordPair = new BasicNameValuePair("password", password);
        NameValuePair scopePair = new BasicNameValuePair("scope", scope);

        pairs.add(idPair);
        pairs.add(secretPair);
        pairs.add(grantPair);
        pairs.add(usernamePair);
        pairs.add(passwordPair);
        pairs.add(scopePair);

        String url = "http://190.35.194.192:8083/api/oauth/token";
        String data = HttpClientUtils.httpResponseString(url,authorization,new UrlEncodedFormEntity(pairs, HTTP.UTF_8));

//        String data = remoteService.getToken(grantType,username,password,scope
//                ,clientId,clientSecret,authorization);

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
