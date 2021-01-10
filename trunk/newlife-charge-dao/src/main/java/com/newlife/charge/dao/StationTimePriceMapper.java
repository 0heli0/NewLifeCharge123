package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.StationTimePriceMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.StationTimePrice;
import com.newlife.charge.core.dto.in.StationTimePriceQuery;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationTimePriceMapper extends CrudRepository<StationTimePrice> {

    /**
     * 根据桩站ID查询桩站时段价格
     * @param stationId
     * @return
     */
    List<StationTimePrice> getByStationId(@Param("stationId") Integer stationId);

    /**
     * 根据桩站ID查询桩站时段 序号最大值
     * @param stationId
     * @return
     */
    StationTimePrice getOneByStationId(@Param("stationId") Integer stationId);

    /**
     * @Description: 根据条件查询时段电价
     * @Author: Linzq
     * @CreateDate:  2019/5/9 0009 15:02
     */
    List<StationTimePrice> getByQuery(StationTimePriceQuery query);

    /**
     * @Description: 删除时段电价
     * @Author: Linzq
     * @CreateDate:  2019/5/9 0009 15:09
     */
    void deleteByTimeNoAndStationId(@Param("stationId") Integer stationId, @Param("timeNo") Integer timeNo);

    /**
     * @Description: 限制电价 价格只能三种
     * @Author: Linzq
     * @CreateDate:  2019/5/9 0009 15:09
     */
    List<StationTimePrice> getIncrease(@Param("stationId") Integer stationId);

    /**
     * @Description: 根据条件查询时段电价
     * @Author: Linzq
     * @CreateDate:  2019/5/9 0009 15:02
     */
    List<StationTimePrice> selectStationTime(StationTimePriceQuery query);

    /**
     * 查询指定电站，当前时段电价
     * @param stationId
     * @return
     */
    StationTimePrice getNowTimePrice(Integer stationId);
}