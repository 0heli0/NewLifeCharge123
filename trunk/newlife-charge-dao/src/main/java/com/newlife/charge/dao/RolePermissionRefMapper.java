package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.RolePermissionRefMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.RolePermissionRef;
import com.newlife.charge.core.domain.exModel.RolePermissionEx;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionRefMapper extends CrudRepository<RolePermissionRef> {


    /**
     * 通过角色ID查询
     *
     * @param roleId 角色Id,null则查询全部
     * @return
     */
    List<RolePermissionEx> getByRoleId(@Param("roleId") Integer roleId);


    /**
     * 通过角色ID集合查询
     *
     * @param roleIdList 角色Id,null则查询全部
     * @return
     */
    List<RolePermissionEx> getByRoleIdList(@Param("roleIdList") List<Integer> roleIdList);


    /**
     * 通过 角色id 删除角色用户关联表记录
     *
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);



    /**
     * 通过 角色id数组 删除角色用户关联表记录
     * @param roleIds
     * @return
     */
    int deleteByRoleIds(@Param("roleIds") int[] roleIds);

}