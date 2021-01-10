/* -------------------------------------------
 * Permission.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


/**
 * 权限
 */
@ApiModel(value = "权限信息")
public class PermissionInfoOut {

    //权限ID
    @ApiModelProperty(value = "权限ID")
    private Integer id;

    //权限名
    @ApiModelProperty(value = "权限名")
    private String permissionName;

    //父节点ID
    @ApiModelProperty(value = "父节点ID")
    private Integer parentId;

    //用户权限
    @ApiModelProperty(value = "用户权限")
    private Integer rolePermissionId;

    //权限子列表
    @ApiModelProperty(value = "权限子列表")
    private List<PermissionInfoOut> children = Lists.newArrayList();

    //权限简称
    @ApiModelProperty(value = "权限简称")
    private String permissionSname;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(Integer rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }

    public List<PermissionInfoOut> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionInfoOut> children) {
        this.children = children;
    }

    public String getPermissionSname() {
        return permissionSname;
    }

    public void setPermissionSname(String permissionSname) {
        this.permissionSname = permissionSname;
    }
}