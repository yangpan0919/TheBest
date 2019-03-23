package com.best.zcdn.bean;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.best.zcdn.config.ErrorCode;

import java.util.List;

/**
 * Created by EDZ on 2018/11/6.
 */
public class ConfResultBean extends ErrorCode {

    private List<Integer> ids;
    private List<Integer> succeedIds;

    public ConfResultBean(int code) {
        super(code);
    }

    public ConfResultBean(int code, List<Integer> ids) {
        super(code);
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getSucceedIds() {
        return succeedIds;
    }

    public void setSucceedIds(List<Integer> succeedIds) {
        this.succeedIds = succeedIds;
    }

    public ConfResultBean(int code, List<Integer> ids, List<Integer> succeedIds) {
        super(code);
        this.ids = ids;
        this.succeedIds = succeedIds;
    }

    public JSONObject toJSONObject(){
        try {
            JSONObject obj = super.toJSONObject();
            obj.put("",succeedIds);
            return  obj;
        }catch (JSONException e){
            return  null;
        }
    }

    @Override
    public String toString() {
      try {
          JSONObject obj =toJSONObject();
          if(null!=obj){
              return  obj.toJSONString();
          }
      }catch (JSONException e){
          return  null;
      }
        return  null;
    }
}
