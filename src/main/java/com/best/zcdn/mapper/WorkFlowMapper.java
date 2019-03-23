package com.best.zcdn.mapper;


import com.best.zcdn.bean.AssigneeRuleDTO;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

/**
 * 用于操作工作流相关的表接口
 */
public interface WorkFlowMapper {
    /**
     * 获取对应流程的规则
     * @param processDefinitionEntity
     * @return
     */
    AssigneeRuleDTO queryWorkFlowRule(ProcessDefinitionEntity processDefinitionEntity);



}
