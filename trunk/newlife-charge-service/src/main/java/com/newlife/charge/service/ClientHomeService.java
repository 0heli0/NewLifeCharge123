package com.newlife.charge.service;


import com.newlife.charge.core.dto.in.StationDetailIn;
import com.newlife.charge.core.dto.in.StationHomeListIn;
import com.newlife.charge.core.dto.out.StationHomeDetailOut;
import com.newlife.charge.core.dto.out.StationHomeListOut;
import com.newlife.charge.core.dto.out.StationHomeMapListOut;

import java.util.List;

/**
 * 车主端 首页功能 service 类
 * <p>
 */
public interface ClientHomeService {

    /**
     * 首页地图根据用户位置信息查询附近桩站
     * @param listIn
     * @return
     */
    List<StationHomeMapListOut> mapStation(StationHomeListIn listIn);

    /**
     * 首页地图根据用户位置信息查询附近桩站
     * @param listIn
     * @return
     */
    List<StationHomeListOut> stationList(StationHomeListIn listIn);

    /**
     * 查询桩站收费,优惠和距离详情
     * @param detailIn
     * @return
     */
    StationHomeDetailOut stationDetail(StationDetailIn detailIn);

}
