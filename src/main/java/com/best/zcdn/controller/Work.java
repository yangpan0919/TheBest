package com.best.zcdn.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.best.zcdn.bean.ConfResultBean;
import com.best.zcdn.config.Constant;
import com.best.zcdn.service.ConfQuAnswerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by EDZ on 2018/11/6.
 */
@RestController
public class Work {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/confQuestionAnswerqwe", method = RequestMethod.POST)
    public String confQuestionAnswer(String body){
        long start = System.currentTimeMillis();
        logger.info("analyze begin");

        ConfResultBean confResult = null;
        String  op = null;
        JSONArray qaSet = null;

        try{
            JSONObject obj = JSONObject.parseObject(body);
            if(!obj.containsKey(Constant.OP)||!obj.containsKey(Constant.REQUEST_QASET)){
                ConfResultBean error = new ConfResultBean(ConfResultBean.INVALID_PARAM);
                return error.toString();
            }
            op = obj.getString(Constant.OP);
            qaSet = obj.getJSONArray(Constant.REQUEST_QASET);
        }catch (JSONException e){
            confResult = new ConfResultBean(ConfResultBean.SYSTEM_ERR);
            return  confResult.toString();

        }

        if(CollectionUtils.isEmpty(qaSet)|| StringUtils.isEmpty(op)){
            confResult = new ConfResultBean(ConfResultBean.INVALID_PARAM);
        }else if(null == confResult){
            ConfQuAnswerServiceImpl confQuAnswerService = new ConfQuAnswerServiceImpl();
//            confResult = confQuAnswerService.confQA(op,qaSet);
        }
        return  null;
    }
}
