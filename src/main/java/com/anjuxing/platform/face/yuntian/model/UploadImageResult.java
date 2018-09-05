package com.anjuxing.platform.face.yuntian.model;

/**
 * @author xiongt
 * @Description 图片上传返回对象
 */
public class UploadImageResult {

    private String id;

    private long time;

    private String uri;

    private String faceUri;

    private String redUri;

    private int faces;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getFaceUri() {
        return faceUri;
    }

    public void setFaceUri(String faceUri) {
        this.faceUri = faceUri;
    }

    public String getRedUri() {
        return redUri;
    }

    public void setRedUri(String redUri) {
        this.redUri = redUri;
    }

    public int getFaces() {
        return faces;
    }

    public void setFaces(int faces) {
        this.faces = faces;
    }

    @Override
    public String toString() {
        return "UploadImage{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", uri='" + uri + '\'' +
                ", faceUri='" + faceUri + '\'' +
                ", redUri='" + redUri + '\'' +
                ", faces=" + faces +
                '}';
    }
}
