package com.newlife.charge.service;


import com.newlife.charge.core.dto.out.GeneralStationTimePriceOut;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 时段电价 service 类
 * <p>
 */
public interface StationTimePriceService {


    /**
     * 根据桩站ID查询桩站时段价格
     *
     * @param stationId
     * @return
     */
    List<GeneralStationTimePriceOut> getByStationId(@Param("stationId") Integer stationId);

}
