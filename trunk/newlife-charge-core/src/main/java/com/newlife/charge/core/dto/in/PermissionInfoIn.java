/* -------------------------------------------
 * Permission.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 权限查询
 */
@ApiModel
public class PermissionInfoIn {

    //角色id
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    //所属系统
    @ApiModelProperty(value = "所属系统")
    private Integer projectType;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }
}