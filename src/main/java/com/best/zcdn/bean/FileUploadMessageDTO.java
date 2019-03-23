package com.best.zcdn.bean;

/**
 * Created by YP on 2018/11/15.
 */
public class FileUploadMessageDTO {

    //文件地址
    private String url;
    //原文件名
    private String srcName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }
}
