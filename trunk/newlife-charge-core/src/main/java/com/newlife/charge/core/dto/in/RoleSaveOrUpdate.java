/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;


/**
 * 角色保存
 */
public class RoleSaveOrUpdate {


    //角色ID
    private Integer id;

    //所属桩站ID
    private Integer stationId;

    //角色名称
    private String roleName;

    //所属系统(0:总后台系统,1：桩站系统)
    private Integer project;

    //备注
    private String remark;

    //所有选中的权限
    private Integer[] permissionArr;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer[] getPermissionArr() {
        return permissionArr;
    }

    public void setPermissionArr(Integer[] permissionArr) {
        this.permissionArr = permissionArr;
    }
}