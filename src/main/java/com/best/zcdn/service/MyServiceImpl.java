package com.best.zcdn.service;

import com.best.zcdn.bean.QABean;
import com.best.zcdn.bean.User;
import com.best.zcdn.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(ActivitiDemoServiceImpl.class);
    @Autowired
    UserMapper userMapper;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public  void ceshi() {
        QABean bean = new QABean();
        bean.setQaId(1);
        bean.setQuestions("questions");
        bean.setAnswers("answers");
        userMapper.delUserSysDialog(5);
        userMapper.delUserSysDialog(5);
        userMapper.delUserSysDialog(5);
        for(int i=0;i<4;i++){

        }
        userMapper.delUserSysDialog(5);
        userMapper.delUserSysDialog1(5);
        userMapper.inserUserSysDialog(bean);
    }


    public String getLeader(String UserId){
        return  "123";
    }

    public User queryUserDetailByUserId(Long userId) {
        User user = userMapper.queryUserDetail(userId);
        return user;
    }
    /**
     * 当前人员上级，递归执行至某角色
     */
    public User queryLeaderByRoleForAssignee(String roleId, Long userId) {
        Long tempId = userId;
        for(int i=0;i<30;i++){
            User userInfoDTO = userMapper.queryUserDetail(tempId);
            if(userInfoDTO.getLeaderId() == null){
                logger.warn("当前人员:"+userId+",没有该角色："+roleId+",的上级，最高上级为："+tempId);
//                throw new ErrorCodeException(ErrorEnum.FIND_ASSIGNEE_ERROR);
            }
            List<String> list =userMapper.queryRoleIdByUserId(userInfoDTO.getLeaderId());
            if(list.size()>0&&list.contains(roleId)){
                User user =  userMapper.queryUserDetail(userInfoDTO.getLeaderId());
                if(user == null){
//                    throw new ErrorCodeException(ErrorEnum.FIND_ASSIGNEE_ERROR);
                }
                return  user;
            }else{
                tempId = userInfoDTO.getLeaderId();
            }
        }
        logger.error("数据库存在脏数据请及时处理：当前人员上级，递归执行至某角色。","角色："+roleId+",当前用户："+userId);
//        throw new ErrorCodeException(ErrorEnum.FIND_ASSIGNEE_ERROR);
        return  null;
    }
    /**
     * 当前人员上级，递归执行至某岗位
     */
    public User queryLeaderByPostForAssignee(Long postId, Long userId) {
        Long tempId = userId;
        for(int i=0;i<30;i++){
            User userInfoDTO = userMapper.queryUserDetail(tempId);
            if(userInfoDTO.getLeaderId() == null){
                logger.warn("当前人员:"+userId+",没有该岗位："+postId+",的上级，最高上级为："+tempId);

            }
            List<Long> list =userMapper.queryPostIdByUserId(userInfoDTO.getLeaderId());
            if(list.size()>0&&list.contains(postId)){
                User user =  userMapper.queryUserDetail(userInfoDTO.getLeaderId());
                if(user == null){

                }
                return  user;
            }else{
                tempId = userInfoDTO.getLeaderId();
            }
        }
        logger.error("数据库存在脏数据请及时处理：当前人员上级，递归执行至某岗位。","岗位："+postId+",当前用户："+userId);
        return  null;
    }

    /**
     * 当前人员部门对接人
     */
    public User queryDeptUserForAssignee( Long userId) {
        User userInfoDTO = userMapper.queryDeptUserByUserId(userId);
        if(userInfoDTO == null){
//            throw new ErrorCodeException(ErrorEnum.FIND_ASSIGNEE_ERROR);
        }
        return userInfoDTO;
    }
    /**
     * 查询当前人员部门的对应指定岗位人员
     */
    public  List<User> queryUserByDeptPostIdForAssignee( Long postId,Long userId) {
        List<User> userList = userMapper.queryDeptPostByUserIdPostId(userId,postId);
        if(userList.size() == 0){
//            throw new ErrorCodeException(ErrorEnum.FIND_ASSIGNEE_ERROR);
        }
        return  userList;
    }

}
