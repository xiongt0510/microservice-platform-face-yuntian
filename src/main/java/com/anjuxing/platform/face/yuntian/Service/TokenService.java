package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.Token;
import com.anjuxing.platform.face.yuntian.properties.ClientProperties;

import java.io.IOException;


/**
 * @author xiongt
 * @Description
 */
public interface TokenService {

    Token getToken(ClientProperties client) throws IOException;


    boolean isInvalidToken(String result) throws IOException;

}
