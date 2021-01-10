package com.newlife.charge.service;


import com.newlife.charge.core.domain.exModel.RolePermissionEx;
import com.newlife.charge.core.dto.in.PermissionInfoIn;
import com.newlife.charge.core.dto.out.PermissionInfoOut;

import java.util.List;

/**
 * 权限 service
 */
public interface PermissionInfoService {

    /**
     * 权限 列表
     *
     * @param in
     * @return
     */
    List<PermissionInfoOut> list(PermissionInfoIn in);

    /**
     *  获取对应后台 权限 列表
     *
     * @param permissionInfoIn
     * @return
     */
    List<PermissionInfoOut> getByRoleId(PermissionInfoIn permissionInfoIn);

    /**
     * @Description: 获取角色权限名称
     * @Author: Linzq
     * @CreateDate:  2019/6/4 0004 11:20
     */
    List<RolePermissionEx> permissionSname(Integer roleId);
}
