package com.best.zcdn.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class empController {


    @RequestMapping(value = "/getempDepartment", method = RequestMethod.GET)
    public String emp(String body){
        body ="{'lastName':'animal','gender':'pig','email':'520520@1314','dId':'5'}";
       String  body1 ="{'lastName':'animal','gender':'pig','email':'520520@1314'}";
        long start = System.currentTimeMillis();
        JSONObject ret=null;
        JSONObject obj = JSONObject.parseObject(body);
        String lastName = obj.getString("lastName");
        Integer gender = obj.getInteger("gender");
        String email = obj.getString("email");
        Integer dId = obj.getInteger("dId");



        return null;
    }

}
