/* -------------------------------------------
 * RolePermission.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-12-07 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;


import com.newlife.charge.core.domain.RolePermissionRef;

/**
 * 角色权限 扩展表  关联
 */
public class RolePermissionEx extends RolePermissionRef {

    //权限简称
    private String permissionSname;

    public String getPermissionSname() {
        return permissionSname;
    }

    public void setPermissionSname(String permissionSname) {
        this.permissionSname = permissionSname;
    }
}