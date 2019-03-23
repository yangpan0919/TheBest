package com.best.zcdn.bean;

import com.alibaba.druid.util.StringUtils;

import java.util.List;

/**
 * Created by EDZ on 2018/10/29.
 */
public class Permission {
    /**
     * 权限id
     */
    private Integer id;
    /**
     * 权限名
     */
    private String permissionName;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否有该权限标志位，1--有，0--没有
     */
    private Integer flag = 0;

    private  List<Permission> list;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFlag() {
        return flag;
    }
    public void setFlag(Integer flag) {
        if(flag != null && flag != 0){
            this.flag = 1;
        }
    }

    public List<Permission> getList() {
        return list;
    }

    public void setList(List<Permission> list) {
        this.list = list;
    }
}
