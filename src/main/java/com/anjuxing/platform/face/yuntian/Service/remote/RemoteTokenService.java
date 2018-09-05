package com.anjuxing.platform.face.yuntian.Service.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xiongt
 * @Description
 */
@FeignClient(name = "${yuntian.token.name}", url = "${yuntian.token.url}")
public interface RemoteTokenService {

    @PostMapping( consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String getToken(
           @RequestParam("grant_type") String grantType,
           @RequestParam("username")String username,
           @RequestParam("password")String password,
           @RequestParam("scope")String scope,
           @RequestParam("client_id")String clientId,
           @RequestParam("client_secret")String clientSecret,
           @RequestHeader("Authorization")String authorization
    );
}
