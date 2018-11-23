package com.anjuxing.platform.face.yuntian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author xiongt
 * @Description
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@EnableCaching
public class MicroservicePlatformFaceYuntianApplication {

    public static void main(String [] args){
        SpringApplication.run(MicroservicePlatformFaceYuntianApplication.class,args);
    }
}
