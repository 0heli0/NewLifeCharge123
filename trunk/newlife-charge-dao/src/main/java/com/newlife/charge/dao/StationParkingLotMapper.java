package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.StationParkingLotMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.StationParkingLot;
import com.newlife.charge.core.dto.out.GeneralStationParkingLotPageOut;
import com.newlife.charge.core.dto.out.SelectListOut;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationParkingLotMapper extends CrudRepository<StationParkingLot> {


    /**
     * 总后台 车位列表下拉框
     *
     * @param stationId 桩站ID
     * @param stationParkingLotId 车位ID
     * @return
     */
    List<SelectListOut> selectList(@Param("stationId") Integer stationId,@Param("stationParkingLotId")Integer stationParkingLotId);


    /**
     * 根据充电桩id查询车位信息
     *
     * @param stationClientId 充电桩ID
     * @return
     */
    List<StationParkingLot> getInfoByStationClientId(@Param("stationClientId") Integer stationClientId);


    /**
     * 根据桩站ID查询车位数量
     *
     * @param stationId
     * @return
     */
    Integer countByStationId(@Param("stationId") Integer stationId);


    /**
     * 根据公司ID统计车位数量
     *
     * @param companyId 公司ID
     * @return
     */
    Integer countByCompanyId(@Param("companyId") Integer companyId);


//    /**
//     * 总后台 根据车位编号查询车位信息
//     *
//     * @param code 车位编号
//     * @return
//     */
//    StationParkingLot getByCode(@Param("code") String code);

    /**
     * 总后台 根据车位编号和桩站ID查询车位信息
     *
     * @param code 车位编号
     * @param stationId 桩站ID
     * @return
     */
    StationParkingLot getByCodeAndStationId(@Param("code") String code,@Param("stationId") Integer stationId);

    /**
     * 根据桩站名称分页查询车位信息
     *
     * @param stationName
     * @return
     */
    Page<GeneralStationParkingLotPageOut> page(@Param("stationName") String stationName);

    /**
     * 通过参数获取
     * @param lot
     * @return
     */
    List<StationParkingLot> getByParams(StationParkingLot lot);
}