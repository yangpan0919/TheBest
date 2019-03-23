package com.best.zcdn.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.best.zcdn.bean.ConfResultBean;
import com.best.zcdn.bean.QABean;
import com.best.zcdn.config.ErrorCode;
import com.best.zcdn.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class ConfQuAnswerServiceImpl {


    @Autowired
    UserMapper userMapper;
    @Autowired
    MyServiceImpl myService;


//    public ConfResultBean confQA(String op, JSONArray qaSet){
//        ConfResultBean confResultBean = new ConfResultBean(ErrorCode.SUCCED);
//        List<Integer> ids = new ArrayList<>();
//        List<Integer> succeedIds = new ArrayList<>();
//        confResultBean.setIds(ids);
//        confResultBean.setSucceedIds(succeedIds);
//        if(op.equals("add")||op.equals("update")){
//            if(add(qaList,ids,succeedIds)){
//                return  null;
//            }
//        }else if (op.equals("delete")){
//            for(JSONObject object :qaList) {
//                Integer id = object.getInteger("id");
//                if (id == null) {
//                    return null;
//                }
//                ids.add(id);
//                try{
//                    userMapper.delUserSysDialog(id);
//                }catch (Exception e){
//                    //加个日志
//                    continue;
//                }
//                succeedIds.add(id);
//            }
//        }else{
//            return null;
//        }
//        return  confResultBean;
//    }

    public ConfResultBean confQA(String op, List<JSONObject> qaList){
        ConfResultBean confResultBean = new ConfResultBean(ErrorCode.SUCCED);
        List<Integer> ids = new ArrayList<>();
        List<Integer> succeedIds = new ArrayList<>();
        confResultBean.setIds(ids);
        confResultBean.setSucceedIds(succeedIds);
        if(op.equals("add")||op.equals("update")){
            if(add(qaList,ids,succeedIds)){
                return  null;
            }
        }else if (op.equals("delete")){
            for(JSONObject object :qaList) {
                Integer id = object.getInteger("id");
                if (id == null) {
                    return null;
                }
                ids.add(id);
                try{
                    userMapper.delUserSysDialog(id);
                }catch (Exception e){
                    //加个日志
                    continue;
                }
                succeedIds.add(id);
            }
        }else{
            return null;
        }
        return  confResultBean;
    }

    public List<Integer> confQuestion(String op, List<JSONObject> qaList){

        List<Integer> list = new ArrayList<>();
        if(op.equals("add")||op.equals("update")){
            userMapper.createTable();
            for(JSONObject object :qaList){
                QABean bean = new QABean();
                Integer id = object.getInteger("id");
                if(id==null){
                    return null;
                }
                bean.setQaId(id);
                bean.setQuestions(object.getString("questions"));
                bean.setAnswers(object.getString("answers"));
                int i = 0;
                try {
                   i =  add(bean);
                }catch (Exception e){
                    //日志
                    continue;
                }
                if(i==1){
                    list.add(id);
                }
            }
        }else if (op.equals("delete")){
            for(JSONObject object :qaList) {
                Integer id = object.getInteger("id");
                if (id == null) {
                    return null;
                }
                try{
                    userMapper.delUserSysDialog(id);
                }catch (Exception e){
                    //加个日志
                    continue;
                }
                list.add(id);
            }
        }else{
            return null;
        }
        return  list;
    }

    @Transactional
    int add(QABean bean){
        userMapper.delUserSysDialog(5);
        bean.setQaId(6);
        System.out.println("删除成功了");
        int i = userMapper.inserUserSysDialog(bean);
        System.out.println("插入成功了");
        return i;
    }


    @Transactional
    boolean add(List<JSONObject> list,List<Integer> ids,List<Integer> succeedIds){
        for(JSONObject object :list){
            QABean bean = new QABean();
            Integer id = object.getInteger("id");
            if(id==null){
                return true;
            }
            ids.add(id);
            bean.setQaId(id);
            bean.setQuestions(object.getString("questions"));
            bean.setAnswers(object.getString("answers"));
            userMapper.delUserSysDialog(id);
            userMapper.inserUserSysDialog(bean);
            succeedIds.add(id);
        }
        return  false;
    }





    public List<Integer> conf(String op, JSONArray qaSet){
        List<Integer> list = new ArrayList<>();
        if(op.equals("add")||op.equals("update")){
//            for(Object obj :qaSet.toArray()){
//                JSONObject object = (JSONObject) obj;
//                QABean bean = new QABean();
//                Integer id = object.getInteger("id");
//                if(id==null){
//                    return null;
//                }
//                bean.setQaId(id);
//                bean.setQuestions(object.getString("questions"));
//                bean.setAnswers(object.getString("answers"));
//                userMapper.inserUserSysDialog(bean);
//                list.add(id);
//            }
            if(add(qaSet,list)){
                return  null;
            }
            return list;
      /*  }else if (op.equals("update")){
            for(Object obj :qaSet.toArray()){
                JSONObject object = (JSONObject) obj;
                QABean bean = new QABean();
                Integer id = object.getInteger("id");
                if(id==null){
                    return null;
                }
                bean.setQaId(id);
                bean.setQuestions(object.getString("questions"));
                bean.setAnswers(object.getString("answers"));
                userMapper.delUserSysDialog(id);
                userMapper.inserUserSysDialog(bean);
                list.add(id);
            }
            return list;*/
        } else if (op.equals("delete")){
            for(Object obj :qaSet.toArray()) {
                JSONObject object = (JSONObject) obj;
                Integer id = object.getInteger("id");
                if (id == null) {
                    return null;
                }
                try{
                    userMapper.delUserSysDialog(id);
                }catch (Exception e){
                    //加个日志
                    continue;
                }
                list.add(id);
            }
            return  list;
        }else{
            return null;
        }
    }
    @Transactional
    boolean add(JSONArray qaSet,List<Integer> list){
        for(Object obj :qaSet.toArray()){
            JSONObject object = (JSONObject) obj;
            QABean bean = new QABean();
            Integer id = object.getInteger("id");
            if(id==null){
                return true;
            }
            bean.setQaId(id);
            bean.setQuestions(object.getString("questions"));
            bean.setAnswers(object.getString("answers"));
            QABean qaBean =  userMapper.getQAByFilter(id);
            int i =0;
            if(qaBean == null){
                i= userMapper.inserUserSysDialog(bean);
            }else{
                i= userMapper.updateQA(bean);
            }



            list.add(id);
        }
        return  false;
    }
}
