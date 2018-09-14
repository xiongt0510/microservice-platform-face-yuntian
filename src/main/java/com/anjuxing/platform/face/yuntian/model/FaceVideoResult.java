package com.anjuxing.platform.face.yuntian.model;

import java.util.List;

/**
 * @author xiongt
 * @Description
 */
public class FaceVideoResult {

    /** success 或 fail */
    private String state ;

    /** 错误原因。当为 fail 时才有值。 */
    private String msg;


    private List<ImageData> imageData;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ImageData> getImageData() {
        return imageData;
    }

    public void setImageData(List<ImageData> imageData) {
        this.imageData = imageData;
    }

    public static class ImageData{
        /** 图片的base64字符串(从视频中提取出来的图片)*/
        private String imgBase64;

        /** 比对相似度(视频里提取出来的图片与所上传图片的比对结果) */
        private String similarity;

        public String getImgBase64() {
            return imgBase64;
        }

        public void setImgBase64(String imgBase64) {
            this.imgBase64 = imgBase64;
        }

        public String getSimilarity() {
            return similarity;
        }

        public void setSimilarity(String similarity) {
            this.similarity = similarity;
        }
    }
}
