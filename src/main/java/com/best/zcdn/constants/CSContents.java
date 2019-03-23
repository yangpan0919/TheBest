package com.best.zcdn.constants;

/**
 * User: zhuhaiwei
 * Date: 2016/11/4
 * Project: emp
 * Description:
 */
public class CSContents {

    /**
     *用户session
     */
    public static  final  String USER_SESSION = "USER_SESSION";

    /**
     * 用户登录页面
     */
    public static  final  String USER_LOGIN = "/user/login";

    /**
     * 用户首页
     */
    public static final String CS_INDEX = "/emp/newViews/login.html";

    /**
     * token存储key的前缀
     */
    public static  final  String TOKEN = "TOKEN_";

    /**
     * 账户密码错误最大次数
     */
    public static final int PWD_MAX_ERROR_TIMES=5;

    /**
     * token失效的时间  一天
     */
    public static final  int TOKEN_INVALID_TIME = 86400;
    /**
     * 账户密码错误冻结时间（小时）
     */
    public static final int USER_FREEZING_TIME=2;


    /**
     * 部门类型 总部-0 分部-1 部门-2
     */
    public static final Integer TYPE_COMPANY = 0;
    public static final Integer TYPE_BRANCH = 1;
    public static final Integer TYPE_DEPT = 2;

    /**
     * 人员状态 0-实习  1-正式  2-离职
     */
    public static final Integer PRACTICE_USER = 0;
    public static final Integer FORMAL_USER = 1;
    public static final Integer RESIGNED_USER = 2;

    /**
     * 账户冻结状态 0-正常，1-冻结
     */
    public static final Integer PASSWORD_NO_FREEZED = 0;
    public static final Integer PASSWORD_FREEZED = 1;

    /**
     * redis缓存权限路径对应关系标识头
     */
    public static final String PERMISSION_PATH_URI = "PERMISSION_PATH_URI";
    /**
     * 部门删除标识 0-未删除 1-删除
     */
    public static final Integer DEPT_DELETE_TRUE = 1;
    public static final Integer DEPT_DELETE_FALSE = 0;

}
