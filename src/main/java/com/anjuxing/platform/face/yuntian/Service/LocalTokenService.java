package com.anjuxing.platform.face.yuntian.Service;

import com.anjuxing.platform.face.yuntian.model.Token;
import com.anjuxing.platform.face.yuntian.properties.ClientProperties;


/**
 * @author xiongt
 * @Description
 */
public interface LocalTokenService {

    Token getLocalToken(ClientProperties client) ;

}
