package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.PermissionInfoMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.PermissionInfo;
import com.newlife.charge.core.domain.exModel.PermissionInfoEx;
import com.newlife.charge.core.dto.in.PermissionInfoIn;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionInfoMapper extends CrudRepository<PermissionInfo> {

    /**
     * 查找某系统所有权限
     *
     * @param roleId
     * @param projectType
     * @return
     */
    List<PermissionInfoEx> page(@Param("roleId") Integer roleId,@Param("projectType") Integer projectType);

    /**
     * 查找某系统所有权限
     *
     * @param permissionInfoIn
     * @return
     */
    List<PermissionInfoEx> getByRoleId(PermissionInfoIn permissionInfoIn);
}