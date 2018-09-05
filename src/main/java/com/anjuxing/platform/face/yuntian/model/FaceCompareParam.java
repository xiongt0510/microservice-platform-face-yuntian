package com.anjuxing.platform.face.yuntian.model;

import java.io.Serializable;

/**
 * @author xiongt
 * @Description 1：1人脸比对参数
 */
public class FaceCompareParam implements Serializable {

    private long faceIdA;

    private long faceIdB;

    private float threshold;

    private int atype;

    private int btype;

    public long getFaceIdA() {
        return faceIdA;
    }

    public void setFaceIdA(long faceIdA) {
        this.faceIdA = faceIdA;
    }

    public long getFaceIdB() {
        return faceIdB;
    }

    public void setFaceIdB(long faceIdB) {
        this.faceIdB = faceIdB;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public int getAtype() {
        return atype;
    }

    public void setAtype(int atype) {
        this.atype = atype;
    }

    public int getBtype() {
        return btype;
    }

    public void setBtype(int btype) {
        this.btype = btype;
    }
}
