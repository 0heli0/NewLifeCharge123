package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.RoleUserRefMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.RoleUserRef;
import com.newlife.charge.dao.common.CrudRepository;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleUserRefMapper extends CrudRepository<RoleUserRef> {



    /**
     * 通过角色id查询角色用户关联表记录
     * @param roleId
     * @return
     */
    List<RoleUserRef> getByRoleId(@Param("roleId") Integer roleId);

    /**
     * 通过用户id查询角色用户关联表记录
     * @param userId
     * @return
     */
    List<RoleUserRef> getByUserId(@Param("userId") Integer userId);

    /**
     * 通过用户id 删除角色用户关联表记录
     * @param userId
     * @return
     */
   int deleteByUserId(@Param("userId") Integer userId);


    /**
     * 通过用户id数组 删除角色用户关联表记录
     * @param userIds
     * @return
     */
    int deleteByUserIds(@Param("userIds") int[] userIds);

    /**
     * 通过用户id数组 删除角色用户关联表记录
     * @param userIds
     * @return
     */
    int deleteByUserIdsAndRoleIds(@Param("userIds") int[] userIds, @Param("roleIds") Integer[] roleIds);
}