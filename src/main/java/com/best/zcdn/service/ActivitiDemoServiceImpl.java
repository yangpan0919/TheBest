package com.best.zcdn.service;


import com.best.zcdn.bean.AssigneeRuleDTO;
import com.best.zcdn.bean.User;
import com.best.zcdn.constants.WorkFlowConstants;
import com.best.zcdn.mapper.UserMapper;
import com.best.zcdn.mapper.WorkFlowMapper;
import com.best.zcdn.service.interfaces.ActivitiDemoServiceI;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Knight on 2018/11/8.
 */
@Service("activitiDemoService")
public class ActivitiDemoServiceImpl implements ActivitiDemoServiceI{

    private static Logger logger = LoggerFactory.getLogger(ActivitiDemoServiceImpl.class);
    @Resource
    private WorkFlowMapper workFlowMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    MyServiceImpl myService;
    @Resource
    RepositoryService repositoryService;
    @Resource
    RuntimeService runtimeService;
    @Resource
    TaskService taskService;
    @Resource
    FormService formService;

    @Override
    public String getLeader(String currentOp,Task task) {
        //判断当前处理人是否是最后处理人   放到上一步
        String taskId = task.getId();
        Object variable = taskService.getVariable(taskId, WorkFlowConstants.END_KEY);
        if(variable != null&& WorkFlowConstants.END_VALUE.equals((String)variable)){
            logger.info("当前用户就是该多实例任务的最后处理人："+currentOp);
            taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
            return null;
        }

//        //1:使用任务ID，查询任务对象  
//        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //2:获取流程定义ID  
        String processDefinitionId = task.getProcessDefinitionId();
        //3:查询ProcessDefinitionEntity对象  
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity)repositoryService.getProcessDefinition(processDefinitionId);

        User cUser = myService.queryUserDetailByUserId(Long.parseLong(currentOp));
        User leader = null;
        if(cUser == null) {
            logger.warn("当前用户不存在！用户ID:"+currentOp);
            taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
            return null;
        }
        AssigneeRuleDTO assigneeRuleDTO = workFlowMapper.queryWorkFlowRule(processDefinitionEntity);
        if(assigneeRuleDTO == null){
            logger.warn("当前任务没有对应的规则？当前用户ID:"+currentOp+",任务taskID:"+taskId);
            taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
            return null;
        }



        switch (assigneeRuleDTO.getType()){
            case WorkFlowConstants.ASSIGN_TYPE1:
                List<String> list1 =userMapper.queryRoleIdByUserId(cUser.getUserId());
                if(list1.size()>0&&list1.contains(assigneeRuleDTO.getRole())){
                    taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
                    return null;
                }
               break;
            case WorkFlowConstants.ASSIGN_TYPE2:
                List<Long> list2 =userMapper.queryPostIdByUserId(cUser.getUserId());
                if(list2.size()>0&&list2.contains(assigneeRuleDTO.getPostId())){
                    taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
                    return null;
                }
                break;
            case WorkFlowConstants.ASSIGN_TYPE3:
                break;
            case WorkFlowConstants.ASSIGN_TYPE4:
                break;
            case WorkFlowConstants.ASSIGN_TYPE5:
                break;
            default:
                taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
                break;
        }


        if (cUser.getLeaderId() == null) {
            logger.warn("当前用户上级不存在！用户ID:"+currentOp);
            taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
            return null;
        }else{
            leader = myService.queryUserDetailByUserId(cUser.getLeaderId());
        }
        if(leader == null) {
            logger.warn("当前用户上级不存在！当前用户ID:"+currentOp+"上级领导ID:"+cUser.getLeaderId());
            taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
            return null;
        }
        return  leader.getUserId().toString();




//        taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);
//
//        List<UserQueryDTO> userQueryDTOs = userService.queryUserByName(cUser.getLeaderName());
//
//        return String.valueOf(userQueryDTOs.get(0).getUserId());
    }


    @Override
    public boolean isEnded(String currentOp, boolean needFinal,String taskId) {
        Object variable = taskService.getVariable(taskId, WorkFlowConstants.END_KEY);
        if(variable != null&& WorkFlowConstants.END_VALUE.equals((String)variable)){
            logger.info("当前用户就是该多实例任务的最后处理人："+currentOp);
            return true;
        }
        System.out.println("+++++++++++++++++++++++++++++++"+currentOp+"+++++++++"+needFinal+"++++++++++++++++++++++++++++");
        return  false;



//        User cUser = myService.queryUserDetailByUserId(Long.parseLong(currentOp));
//        if(cUser == null) {
//            return true;
//        }
//        if(cUser.getLeaderName() == null) {
//            return true;
//        }
//        if(!needFinal&&cUser.getLeaderName().equals("COO")) {
//            return true;
//        } else {
//            return false;
//        }
    }
    @Override
    public void endTask(DelegateTask task) {
        logger.info("-----------------create called ------------------------");
        Object variable = taskService.getVariable(task.getId(), WorkFlowConstants.END_KEY);
        if(variable != null&& "null".equals((String)variable)){
            logger.info("任务无法获得最后处理人直接结束多实例任务："+taskService.getVariable(task.getId(), "currentOp")+",最后任务ID:"+task.getId());
            taskService.complete(task.getId());
            return ;
        }
    }
    @Override
    public void setAssignee(String currentOp, TaskEntity taskEntity){

        //判断当前处理人是否是最后处理人   放到上一步
        String taskId = taskEntity.getId();
        System.out.println("+++++++++++++++++++++++++++++++"+taskId+"+++++++++++++++++++++++++++++++++++++");
        taskService.setVariable(taskEntity.getId(),"taskId",taskEntity.getId());

        taskService.setVariable(taskId, WorkFlowConstants.END_KEY, WorkFlowConstants.END_VALUE);

        taskEntity.setAssignee("qq");

//       new Thread(){
//           @Override
//           public void run() {
//               try {
//                   this.currentThread().sleep(1000L);
//               } catch (InterruptedException e) {
//                   e.printStackTrace();
//               }
//               taskService.complete(taskId);
//           }
//       }.start();

//        taskService.complete(taskId);


//        User cUser = myService.queryUserDetailByUserId(Long.parseLong(currentOp));
//        if(cUser == null) {
//            taskService.complete(taskEntity.getId());
//        }
//        if(cUser.getLeaderName() == null) {
//            taskService.complete(taskEntity.getId());
//        } else {
////            String leader = String.valueOf(myService.queryUserByName(cUser.getLeaderName()).get(0).getUserId());
////            delegateTask.setAssignee(leader);
//        }
    }
}
