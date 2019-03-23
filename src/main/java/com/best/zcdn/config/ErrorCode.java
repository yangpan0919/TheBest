package com.best.zcdn.config;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ErrorCode {

    public static final int SUCCED = 0;
    public static final int INIT_ERR = 1;
    public static final int INVALID_PARAM = 2;
    public static final int BUSINESS_ERR = 3;
    public static final int SYSTEM_ERR = 4;
    public static final int OUTTIME_ERR = 5;

    private static  final Map<Integer,String> CODEMSGMAP = new HashMap<>();

    static {
        CODEMSGMAP.put(SUCCED,"成功");
        CODEMSGMAP.put(INIT_ERR,"系统正在初始化");
        CODEMSGMAP.put(INVALID_PARAM,"参数错误");
        CODEMSGMAP.put(BUSINESS_ERR,"系统忙");
        CODEMSGMAP.put(SYSTEM_ERR,"系统异常");
        CODEMSGMAP.put(OUTTIME_ERR,"任务超时");
    }
    private int code = 0;
    private String text;
    private String sessionId;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ErrorCode() {
    }

    public ErrorCode(int code) {
        this.code = code;
    }

    public ErrorCode(int code, String text) {
        this.code = code;
        this.text = text;
    }

    public ErrorCode(int code, String text, String sessionId) {
        this.code = code;
        this.text = text;
        this.sessionId = sessionId;
    }
    public JSONObject toJSONObject(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("code",getCode());
            obj.put("message",CODEMSGMAP.get(code));
            obj.put("text",getText());
            obj.put("sessionId",getSessionId());
            obj.put("answer","");
            return obj;
        }catch (JSONException e){
            return  null;
        }


    }
}
