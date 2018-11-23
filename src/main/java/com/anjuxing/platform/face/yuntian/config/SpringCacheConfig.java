package com.anjuxing.platform.face.yuntian.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author xiongt
 * @Description
 */
@Configuration
public class SpringCacheConfig {

    @Bean
    @Autowired
    public CacheManager cacheManager(CacheBuilder cacheBuilder){
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(cacheBuilder);
        return cacheManager;
    }
    
    @Bean
    public CacheBuilder<Object,Object> cacheBuilder(){
       return CacheBuilder.newBuilder().expireAfterWrite(12,TimeUnit.HOURS);
    }
    
    @Bean
    @Autowired
    public Cache cache(CacheBuilder<Object,Object> cacheBuilder){
      return  cacheBuilder.build();
    }
}
