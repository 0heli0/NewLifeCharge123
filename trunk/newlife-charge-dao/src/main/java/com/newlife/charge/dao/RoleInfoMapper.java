package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.RoleInfoMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.RoleInfo;
import com.newlife.charge.core.dto.in.RoleInfoQuery;
import com.newlife.charge.dao.common.CrudRepository;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleInfoMapper extends CrudRepository<RoleInfo> {


    /**
     * 分页 或 列表查询
     *
     * @param project 所属系统
     * @return
     */
    Page<RoleInfo> page(@Param("project") Integer project, @Param("stationId")Integer stationId);

    /**
     * @Description: 按条件查询角色
     * @Author: Linzq
     * @CreateDate:  2019/5/14 0014 16:17
     */
    List<RoleInfo> getByQuery(RoleInfoQuery query);

    /**
     * @Description: 查找对应桩站角色
     * @Author: Linzq
     * @CreateDate:  2019/5/14 0014 16:17
     */
    RoleInfo getByUserId(RoleInfoQuery query);

}