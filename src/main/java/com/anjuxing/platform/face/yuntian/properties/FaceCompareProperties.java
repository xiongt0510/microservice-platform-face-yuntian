package com.anjuxing.platform.face.yuntian.properties;

/**
 * @author xiongt
 * @Description 人脸比对
 */
public class FaceCompareProperties {

    private String url = "http://190.35.192.2:8083/api/intellif/face/compare";

    private float threshold = 1;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
}
