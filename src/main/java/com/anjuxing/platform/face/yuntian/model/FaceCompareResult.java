package com.anjuxing.platform.face.yuntian.model;

/**
 * @author xiongt
 * @Description 人脸比对返回结果
 */
public class FaceCompareResult {

    private float threshold;

    private boolean result ;

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
