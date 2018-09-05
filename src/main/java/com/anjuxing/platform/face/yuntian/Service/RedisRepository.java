package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.Token;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xiongt
 * @Description
 */
@Component
public class RedisRepository {

    private String PREFIX_TOKEN_KEY = "YUNTIAN_";

    private String SUFIX_TOKEN_KEY = "_TOKEN";

    private String SUFIX_REFRESH_TOKEN_KEY = "_REFRESH_TOKEN";

    @Autowired
    private YuntianPropeties yt;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    public void save(Token token){
        redisTemplate.opsForValue().set(getTokenKey(),token.getAccess_token(),token.getExpires_in(), TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(getRefreshTokenKey(),token.getRefresh_token(),token.getExpires_in(),TimeUnit.SECONDS);
    }

    public String getAccessToken(){
        return (String) redisTemplate.opsForValue().get(getTokenKey());
    }

    public String getRefreshToken(){
        return (String) redisTemplate.opsForValue().get(getRefreshToken());
    }


    public String getTokenKey(){
        return PREFIX_TOKEN_KEY + yt.getClient().getClientId() + SUFIX_TOKEN_KEY;
    }

    public String getRefreshTokenKey(){
        return PREFIX_TOKEN_KEY + yt.getClient().getClientId() + SUFIX_REFRESH_TOKEN_KEY;
    }


}
