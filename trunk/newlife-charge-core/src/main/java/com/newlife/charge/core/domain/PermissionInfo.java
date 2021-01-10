/* -------------------------------------------
 * PermissionInfo.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import java.util.Date;



/**
 * 权限信息表 v1.0
 */


public class PermissionInfo {

    //主键-权限id
    private Integer id;

    //权限名称
    private String permissionName;

    //权限简称
    private String permissionSname;

    //权限链接
    private String permissionLink;

    //父id
    private Integer parentId;

    //是否启用(0否1是)
    private Integer enable;

    //显示顺序
    private Integer sortNo;

    //所属系统(0:总后台系统,1：桩站系统)
    private Integer projectType;

    //备注
    private String remark;

    //创建时间
    private Date createTime;

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
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getPermissionSname() {
        return permissionSname;
    }

    public void setPermissionSname(String permissionSname) {
        this.permissionSname = permissionSname == null ? null : permissionSname.trim();
    }

    public String getPermissionLink() {
        return permissionLink;
    }

    public void setPermissionLink(String permissionLink) {
        this.permissionLink = permissionLink == null ? null : permissionLink.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}