package com.best.zcdn.constants;

/**
 * Created by Knight on 2018/11/13.
 * 定义工作流中约定的变量名，如当前处理人， 处理人安全级别， 处理结果等
 */
public class WorkFlowConstants {
    //UserTask 处理人变量名
    public static final String CURRENT_HANDLER = "currentHandler";
    //安全级别变量名
    public static final String SECURITY_LEVEL = "securityLevel";
    //处理结果变量名
    public static final String HANDLE_RESULT = "handleResult";


    //多实例任务结束标志位key值
    public static final String END_KEY = "isEnded";
    //多实例任务结束标志位value值
    public static final String END_VALUE = "yes";



    /**
     * 多实例任务获取最后处理人的规则type
       1.  当前人员上级，递归执行至某角色
       2.  当前人员上级，指定岗位
       3. 指定部门/岗位/安全级别
       4. 当前人员部门对接人/岗位
       5. 指定人员
     */
    public static final int ASSIGN_TYPE1 = 1;
    public static final int ASSIGN_TYPE2 = 2;
    public static final int ASSIGN_TYPE3 = 3;
    public static final int ASSIGN_TYPE4 = 4;
    public static final int ASSIGN_TYPE5 = 5;








}
