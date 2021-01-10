/* -------------------------------------------
 * Permission.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import com.newlife.charge.core.domain.PermissionInfo;


/**
 * 权限
 */
public class PermissionInfoEx extends PermissionInfo {

    //用户权限
    private Integer rolePermissionId;

    public Integer getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(Integer rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }
}