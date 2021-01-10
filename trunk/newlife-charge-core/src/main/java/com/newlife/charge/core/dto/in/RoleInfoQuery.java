/* -------------------------------------------
 * RoleInfo.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.models.auth.In;

import java.util.Date;


/**
 * 角色信息表（分为总后台角色和桩站角色，桩站角色会绑定对应的桩站ID） v1.0
 */


public class RoleInfoQuery {

    //主键-角色id
    private Integer id;

    //所属桩站ID
    private Integer stationId;

    //角色名称
    private String roleName;

    //所属系统(0:总后台系统,1：桩站系统)
    private Integer project;

    private Integer userId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}