package com.anjuxing.platform.face.yuntian.test;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author xiongt
 * @Description
 */
public class Person implements Serializable {

    public Person(){}
    private Person(PersonBuilder builder){
        this.personId = builder.personId;
        this.groupIds = builder.groupIds;
        this.personName = builder.personName;
        this.tag = builder.tag;
        this.imageContents = builder.imageContents;
        this.url = builder.url;
        this.bucketName = builder.bucketName;
        this.file = builder.file;
    }

    //指定的个体ID
    private String personId;

    //加入到组的列表
    private String[] groupIds;

    //名字
    private String personName;

    //备注信息
    private String tag;

    //图片内容
    private byte[] imageContents;

    //图片url
    private String url;

    private String bucketName;

    private File file;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String[] getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String[] groupIds) {
        this.groupIds = groupIds;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public byte[] getImageContents() {
        return imageContents;
    }

    public void setImageContents(byte[] imageContents) {
        this.imageContents = imageContents;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /** 用build 模式来构造一个person对象 */
    public static  class  PersonBuilder {
        //指定的个体ID
        private String personId;

        //加入到组的列表
        private String[] groupIds;

        //名字
        private String personName;

        //备注信息
        private String tag;

        //图片内容
        private byte[] imageContents;

        //图片url
        private String url;

        private String bucketName;

        private File file;

        public PersonBuilder setPersonId(String personId) {
            this.personId = personId;
            return this;
        }

        public PersonBuilder setGroupIds(String[] groupIds) {
            this.groupIds = groupIds;
            return this;
        }

        public PersonBuilder setPersonName(String personName) {
            this.personName = personName;
            return this;
        }

        public PersonBuilder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public PersonBuilder setImageContents(byte[] imageContents) {
            this.imageContents = imageContents;
            return this;
        }

        public PersonBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public PersonBuilder setBucketName(String bucketName) {
            this.bucketName = bucketName;
            return this;
        }

        public  PersonBuilder setFile(File file){
            this.file = file;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", groupIds=" + Arrays.toString(groupIds) +
                ", personName='" + personName + '\'' +
                ", tag='" + tag + '\'' +
                ", imageContents=" + Objects.isNull(imageContents)+
                ", url='" + url + '\'' +
                ", bucketName='" + bucketName + '\'' +
                '}';
    }
}
