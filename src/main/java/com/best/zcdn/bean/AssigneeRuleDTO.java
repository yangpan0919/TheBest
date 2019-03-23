package com.best.zcdn.bean;

/**
 * Created by EDZ on 2018/11/13.
 */
public class AssigneeRuleDTO {
    /**
     * 流程ID
     */
    private String workFlowId;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 关联类型
     */
    private Integer type;
    /**
     * 指定工号
     */
    private String workId;
    /**
     * 截至指定岗位
     */
    private Long postId;
    /**
     * 截至指定部门
     */
    private String dept;

    /**
     * 截至指定角色
     */
    private String role;

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
