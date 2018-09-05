package com.anjuxing.platform.face.yuntian.properties;

/**
 * @author xiongt
 * @Description
 */
public class TokenProperties {

    private String url = "http://190.35.192.2:8083/api/oauth/token";

    private String name = "yutian_service";



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
