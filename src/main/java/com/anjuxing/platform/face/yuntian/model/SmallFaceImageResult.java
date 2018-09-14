package com.anjuxing.platform.face.yuntian.model;

/**
 * @author xiongt
 * @Description
 */
public class SmallFaceImageResult {

    private String id;

    private String sourceId;

    private int sourceType;

    private String fromImageId;

    private String imageData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    public String getFromImageId() {
        return fromImageId;
    }

    public void setFromImageId(String fromImageId) {
        this.fromImageId = fromImageId;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
