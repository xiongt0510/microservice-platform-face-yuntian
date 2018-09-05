package com.anjuxing.platform.face.yuntian.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * @author xiongt
 * @Description
 */
@Configuration
public class YunTianConfig {

    @Scope("prototype")
    @Primary
    @Configuration
    public static class EncoderSupportConfig {
        @Bean
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }

}
