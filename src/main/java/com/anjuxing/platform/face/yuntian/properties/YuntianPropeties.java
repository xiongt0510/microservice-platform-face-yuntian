package com.anjuxing.platform.face.yuntian.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiongt
 * @Description
 */
@ConfigurationProperties(prefix = "yuntian")
public class YuntianPropeties {

    private ClientProperties client = new ClientProperties();

    private ImageUploadProperties upload = new ImageUploadProperties();

    private TokenProperties token = new TokenProperties();

    private FaceCompareProperties faceCompare = new FaceCompareProperties();

    public ClientProperties getClient() {
        return client;
    }

    public void setClient(ClientProperties client) {
        this.client = client;
    }

    public ImageUploadProperties getUpload() {
        return upload;
    }

    public void setUpload(ImageUploadProperties upload) {
        this.upload = upload;
    }

    public TokenProperties getToken() {
        return token;
    }

    public void setToken(TokenProperties token) {
        this.token = token;
    }

    public FaceCompareProperties getFaceCompare() {
        return faceCompare;
    }

    public void setFaceCompare(FaceCompareProperties faceCompare) {
        this.faceCompare = faceCompare;
    }
}
