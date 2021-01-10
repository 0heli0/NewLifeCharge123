package com.newlife.charge.dao;
/* -------------------------------------------
 * com.newlife.charge.dao.ClientStationMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * -------------------------------------------
 * */

import com.newlife.charge.core.domain.exModel.ClientStationDetailEx;
import com.newlife.charge.core.domain.exModel.TruckSpaceEx;
import com.newlife.charge.core.domain.exModel.ClientStationTimePriceEx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClientStationMapper {

    /**
     * 用户端充电站详情
     * @param stationId
     * @return
     */
    ClientStationDetailEx stationDetail(@Param("stationId") Integer stationId);

    /**
     * 时段电价介绍
     * @param stationId
     * @return
     */
    List<ClientStationTimePriceEx> timePriceDetail(@Param("stationId") Integer stationId);

    /**
     * 桩站车位列表
     * @param stationId
     * @return
     */
    List<TruckSpaceEx> truckSpaceList(@Param("stationId")Integer stationId);

    /**
     * 桩站车位详情
     * @param id
     * @return
     */
    TruckSpaceEx truckSpaceDetail(@Param("id")Integer id);

}