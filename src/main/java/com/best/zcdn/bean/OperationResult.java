package com.best.zcdn.bean;


import com.best.zcdn.common.OssConstants;

/**
 * Created by Knight on 2017/7/18.
 * 调用OSS组件返回的结果
 */
public class OperationResult<T> {

    private String resultCode;
    private String resultMessage;
    private T content;

    public OperationResult(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public OperationResult() {
        this.resultCode = OssConstants.RESULT_SUCCESS;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
