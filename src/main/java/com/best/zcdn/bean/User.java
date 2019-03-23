package com.best.zcdn.bean;

import java.util.List;

/**
 * Created by EDZ on 2018/10/26.
 */
public class User {

    private String passWord;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 员工号
     */
    private String workId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 性别(0-男  1-女)
     */
    private Integer gender;

    /**
     * 用户岗位部门对应
     */
//    private List<DeptPostDTO> deptPostDTOList;

    /**
     * 职称
     */
    private String jobTitle;

    /**
     * 职级
     */
    private String level;

    /**
     * 职责描述
     */
    private String postDesc;

    /**
     * 状态
     */
    private Integer workStatus;

    /**
     * 办公地点
     */
    private String workPlace;

    /**
     * 身份证
     */
    private String identity;

    /**
     * 公司邮箱
     */
    private String companyMail;

    /**
     * 用工性质
     */
    private Integer employeeNature;

    /**
     * 合同开始日期
     */
    private String contractStartDate;

    /**
     * 合同结束日期
     */
    private String contractEndDate;

    /**
     * 试用期结束日期
     */
    private String probationPeriodDate;

    /**
     * 移动电话
     */
    private String mobilePhone;

    /**
     * 办公电话
     */
    private String otherPhone;

    /**
     * 个人邮箱
     */
    private String personalMail;

    /**
     * 直接上级id
     */
    private Long leaderId;

    /**
     * 直接上级
     */
    private String leaderName;

    /**
     * 安全级别
     */
    private Integer securityLevel;

    /**
     * 下属
     */
    private List<String> underlingList;

    /**
     *最后登录时间
     */
    private String lastLoginTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 最后修改时间
     */
    private String updateTime;

    /**
     * 修改人
     */
    private String updaterName;

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }



    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public Integer getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCompanyMail() {
        return companyMail;
    }

    public void setCompanyMail(String companyMail) {
        this.companyMail = companyMail;
    }

    public Integer getEmployeeNature() {
        return employeeNature;
    }

    public void setEmployeeNature(Integer employeeNature) {
        this.employeeNature = employeeNature;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getProbationPeriodDate() {
        return probationPeriodDate;
    }

    public void setProbationPeriodDate(String probationPeriodDate) {
        this.probationPeriodDate = probationPeriodDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getPersonalMail() {
        return personalMail;
    }

    public void setPersonalMail(String personalMail) {
        this.personalMail = personalMail;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public List<String> getUnderlingList() {
        return underlingList;
    }

    public void setUnderlingList(List<String> underlingList) {
        this.underlingList = underlingList;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdaterName() {
        return updaterName;
    }

    public void setUpdaterName(String updaterName) {
        this.updaterName = updaterName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(Integer securityLevel) {
        this.securityLevel = securityLevel;
    }
}
