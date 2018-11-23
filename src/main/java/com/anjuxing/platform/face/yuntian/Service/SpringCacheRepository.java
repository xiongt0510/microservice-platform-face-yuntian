package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.Token;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * @author xiongt
 * @Description
 */
@Component
public class SpringCacheRepository {

    private Logger logger = LoggerFactory.getLogger(SpringCacheRepository.class);


    @Autowired
    private YuntianPropeties yt;

    private final static String PREFIX_TOKEN_KEY = "YUNTIAN_";

    private final static String SUFIX_TOKEN_KEY = "_TOKEN";

   @Autowired
   private CacheBuilder cacheBuilder;

   @Autowired
   private Cache<Object, Object> cache;

    /**
     * 存储token
     * @param token
     */
    public  void save(Token token){
        cache.put(getTokenKey(),token.getAccess_token());
    }

    public String getAccessToken(){
        Object token = cache.getIfPresent(getTokenKey());
      return Objects.isNull(token)?"":token.toString();
    }

    public void removeToken (){
        cache.invalidate(getTokenKey());
    }



    public String getTokenKey(){
        return PREFIX_TOKEN_KEY + yt.getClient().getClientId() + SUFIX_TOKEN_KEY;
    }




}
