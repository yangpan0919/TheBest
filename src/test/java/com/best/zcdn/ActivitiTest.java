package com.best.zcdn;

import org.activiti.engine.*;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Knight on 2018/11/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiTest {
    private static Logger logger = LoggerFactory.getLogger(ActivitiTest.class);
    @Resource
    RepositoryService repositoryService;

    @Resource
    RuntimeService runtimeService;
    @Resource
    TaskService taskService;
    @Resource
    FormService formService;
    @Resource
    HistoryService historyService;
    @Test
    public void contextLoads() {
    }


    //使用路程定义进行部署
    @Test
    public void testProcessDeployment(){
        Deployment deployment = repositoryService//获取流程定义和部署对象相关的Service
                .createDeployment()//创建部署对象
                .name("test演示1")//声明流程的名称
                .addClasspathResource("processes/TestMultiInstance.bpmn20.xml")//加载资源文件，一次只能加载一个文件
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//1
        System.out.println("部署时间："+deployment.getDeploymentTime());
    }



    @Test
    public void testProcess(){
        //获取流程定义
//        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionKey("TestMultiInstance2").latestVersion().singleResult();
        //通过流程定义的key开始一个流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("TestMultiInstance");
        Map<String, Object> processVariables = processInstance.getProcessVariables();
        String activityId = processInstance.getActivityId();
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery().activityId(activityId);

        //查询当前所有正在进行的task并打印信息
        List<Task> tasks = taskService.createTaskQuery().list();
        for(Task task : tasks) {
            logger.info("task.getName(): "+task.getName()+"assignee: "+task.getAssignee()+" task.id: "+ task.getId());
        }
    }

    //完成任务
    @Test
    public void testComplete(){
        Task task = taskService.createTaskQuery().taskAssignee("AA").singleResult();
        //设置流程变量
        taskService.setVariable(task.getId(),"needFinal",true);
        taskService.setVariable(task.getId(),"currentOp","AA");
        taskService.complete(task.getId());
        logger.error("______________________________"+task.getId()+"______________________________");

    }

 //完成任务
    @Test
    public void testCompleteAll(){

        taskService.complete("10007");


    }


    @Test
    public void testSetVariable() {
        //查询当前assignee为AA的所有正在进行的task
        Task task= taskService.createTaskQuery().taskAssignee("AA").singleResult();
        logger.info("task.getName(): "+task.getName()+"assignee: "+task.getAssignee()+" task.id: "+ task.getId());
        Map<String,Object> variables = new HashMap<>();
        //设置流程变量
        taskService.setVariable(task.getId(),"needFinal",true);
        taskService.setVariable(task.getId(),"currentOp","AA");
        //获取流程变量
        Integer t = (Integer) taskService.getVariable(task.getId(),"p1");
//		Task task= taskService.createTaskQuery().taskAssignee("u1").singleResult();
        //完成任务
        taskService.complete(task.getId());
    }




    @Test
    public void testSetForm(){
        Task task= taskService.createTaskQuery().singleResult();
        logger.info("task.getName(): "+task.getName()+"assignee: "+task.getAssignee()+" task.id: "+ task.getId());
        //获取某个task对应的表单数据
        FormData formData = formService.getTaskFormData(task.getId());
        Map<String,Object> vars = new HashMap<>();
        for (FormProperty fp : formData.getFormProperties()) {
            logger.info("fp.getName(): "+fp.getName()+"value: "+fp.getValue()+" fp.type: "+ fp.getType());
            vars.put(fp.getId(),"123");
        }
        taskService.complete(task.getId(),vars);
    }

    @Test
    public void testMulti(){
        Task task = taskService.createTaskQuery().taskAssignee("DD").singleResult();
        Map<String,Object> vars = new HashMap<>();
        vars.put("needFinal",false);
        vars.put("currentOp","DD");
        taskService.complete(task.getId(),vars);
    }
    @Test
    public void testHistory(){
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().list();
        for (HistoricTaskInstance historicTaskInstance:list){
            System.out.println(""+historicTaskInstance.getAssignee());
            System.out.println(""+historicTaskInstance.getName());
            System.out.println(""+historicTaskInstance.getCreateTime());
            System.out.println(""+historicTaskInstance.getExecutionId());
            System.out.println(""+historicTaskInstance.getProcessDefinitionId());
            System.out.println(""+historicTaskInstance.getParentTaskId());
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }

}
