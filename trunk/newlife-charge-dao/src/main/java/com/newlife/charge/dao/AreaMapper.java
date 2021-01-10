package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.AreaMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.Area;
import com.newlife.charge.core.dto.out.AreaOut;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper extends CrudRepository<Area> {


    /**
     * 城市列表数据
     *
     * @param parentId 父级ID为空则返回最顶级数据
     * @return
     */
    List<AreaOut> page(@Param("parentId") Integer parentId);
}