package com.anjuxing.platform.face.yuntian.properties;

/**
 * @author xiongt
 * @Description 上传图片类型
 */
public enum UploadType {
    /** 检索上传 */
    RETRIEVE(0),
    /** 布控 */
    CONTROLLER(1),
    /** 红名单 */
    REDLIST(2);


    private UploadType(int value){
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
