package com.anjuxing.platform.face.yuntian.properties;

/**
 * @author xiongt
 * @Description
 */
public class ClientProperties {

    private String grantType = "password";

    private String username = "anjuxing";

    private String password = "71d9ee5abf901bfb99b6fc0502aaae1f";

    private String scope = "read+write";

    private String clientId = "clientapp";

    private String clientSecret = "123456";

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
