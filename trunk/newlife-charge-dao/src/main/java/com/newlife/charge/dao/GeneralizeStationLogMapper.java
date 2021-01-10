package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.GeneralizeStationLogMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.GeneralizeStationLog;
import com.newlife.charge.core.dto.in.GeneralizeStationLogQueryIn;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GeneralizeStationLogMapper extends CrudRepository<GeneralizeStationLog> {
    Page<GeneralizeStationLog> page(@Param("query")GeneralizeStationLogQueryIn query);
}