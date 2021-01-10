package com.newlife.charge.dao;
/* -------------------------------------------
 * com.newlife.charge.dao.StationHomeMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * -------------------------------------------
 * */


import com.newlife.charge.core.domain.StationCouponCount;
import com.newlife.charge.core.domain.StationHomeDetail;
import com.newlife.charge.core.dto.in.StationHomeListIn;
import com.newlife.charge.core.dto.out.StationHomeListOut;
import com.newlife.charge.core.dto.out.StationHomeMapListOut;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationHomeMapper{

    /**
     * 首页地图根据用户位置信息查询附近桩站
     * @param listIn
     * @return
     */
    List<StationHomeMapListOut> mapStation(StationHomeListIn listIn);

    /**
     * 页地图根据用户位置信息查询附近桩站
     * @param listIn
     * @return
     */
    List<StationHomeListOut> stationList(StationHomeListIn listIn);


    /**
     * 查询桩站收费,优惠和距离详情
     * @param stationId
     * @return
     */
    StationHomeDetail stationDetail(@Param("stationId")Integer stationId);

    /**
     * 获取桩站可领用的优惠券数量
     * @param stationId
     * @return
     */
    Integer getStationCouponCount(@Param("stationId")Integer stationId);

    /**
     * 批量获取桩站可领用的优惠券数量
     * @param stationIds
     * @return
     */
    List<StationCouponCount> getStationCouponCountByIds(@Param("stationIds")List<Integer> stationIds);

    /**
     * 查询通用优惠券数量
     * @return
     */
    Integer getUsefulCouponCount();

}