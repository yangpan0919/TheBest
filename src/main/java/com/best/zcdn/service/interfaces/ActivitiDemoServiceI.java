package com.best.zcdn.service.interfaces;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.Task;

/**
 * Created by Knight on 2018/11/8.
 */
public interface ActivitiDemoServiceI {
    String getLeader(String currentOp, Task task);

    boolean isEnded(String currentOp, boolean needFinal,String taskId);

    void endTask(DelegateTask task);

    void setAssignee(String currentOp, TaskEntity taskEntity);
}
