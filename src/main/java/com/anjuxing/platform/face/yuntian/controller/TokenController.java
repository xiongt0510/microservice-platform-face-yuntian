package com.anjuxing.platform.face.yuntian.controller;

import com.anjuxing.platform.face.yuntian.Service.TokenService;
import com.anjuxing.platform.face.yuntian.model.Token;
import com.anjuxing.platform.face.yuntian.properties.YuntianPropeties;
import com.anjuxing.platform.face.yuntian.util.YuntianConstanse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author xiongt
 * @Description
 */
@RestController
@RequestMapping(YuntianConstanse.URL_TOKEN)
public class TokenController {

    @Autowired
    private YuntianPropeties yt;

    @Autowired
    private TokenService localTokenService;

    @GetMapping
    public Token getToken() throws IOException {

        Token token = localTokenService.getToken(yt.getClient());

        return token;
    }

}
