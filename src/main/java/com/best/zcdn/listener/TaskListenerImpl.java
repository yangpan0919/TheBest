package com.best.zcdn.listener;

import com.best.zcdn.service.MyServiceImpl;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by EDZ on 2018/11/13.
 */
public class TaskListenerImpl implements TaskListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskListenerImpl.class);
    private Expression arg;

    public Expression getArg() {
        return arg;
    }

    public void setArg(Expression arg) {
        this.arg = arg;
    }

    @Override
    public void notify(DelegateTask delegateTask) {

        // 实现TaskListener中的方法
        String eventName = delegateTask.getEventName();
        LOGGER.info("任务监听器:{}", arg.getValue(delegateTask));
        if ("create".endsWith(eventName)) {
            LOGGER.info("create=========");
        } else if ("assignment".endsWith(eventName)) {

            LOGGER.info("assignment========");
        } else if ("complete".endsWith(eventName)) {
            LOGGER.info("complete===========");
        } else if ("delete".endsWith(eventName)) {
            LOGGER.info("delete=============");
        }

        //指定办理人
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        System.out.println(request);
        HttpSession session = request.getSession();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        MyServiceImpl service = context.getBean(MyServiceImpl.class);
        delegateTask.setAssignee("xiaosan");

    }
}
