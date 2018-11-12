package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.Token;
import com.anjuxing.platform.face.yuntian.properties.ClientProperties;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * @author xiongt
 * @Description
 */
public interface TokenService {

    Token getToken(ClientProperties client) throws IOException;

}
