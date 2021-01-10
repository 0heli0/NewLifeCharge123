package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.StationDetailMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.StationDetail;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StationDetailMapper extends CrudRepository<StationDetail> {

    /**
     * 根据桩站ID查询桩站详细信息
     * @param stationId
     * @return
     */
    StationDetail getByStationId(@Param("stationId") Integer stationId);

    List<StationDetail> infosById(@Param("ids") int[] ids);

}