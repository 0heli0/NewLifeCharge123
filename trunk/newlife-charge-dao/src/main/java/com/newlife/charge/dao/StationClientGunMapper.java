/* -------------------------------------------
 * StationClientGunMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-29 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.StationClientGun;
import com.newlife.charge.core.domain.StationGunStatistics;
import com.newlife.charge.core.domain.exModel.StationClientGunEx;
import com.newlife.charge.core.dto.out.GunStatusStatisticsOut;
import com.newlife.charge.core.dto.out.StationClientGunInfoOut;
import com.newlife.charge.core.dto.out.StationClientGunInfoSimpleOut;
import com.newlife.charge.core.dto.out.StationGunAddressDetail;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationClientGunMapper extends CrudRepository<StationClientGun> {

    /**
     * 更新充电桩下的枪状态
     *
     * @param stationClientIdList 充电桩ID集合
     * @param status              枪状态
     */
    int updateStatus(@Param("stationClientIdList") List<Integer> stationClientIdList, @Param("status") Integer status);


    /**
     * 根据车位ID查询
     *
     * @param stationParkingLotId 车位ID
     * @return
     */
    List<StationClientGun> getByStationParkingLotId(@Param("stationParkingLotId") Integer stationParkingLotId);


    /**
     * 根据充电桩id查询充电枪信息
     *
     * @param stationClientId 充电桩ID
     * @return
     */
    List<StationClientGun> getInfoByStationClientId(@Param("stationClientId") Integer stationClientId);


    /**
     * 根据桩站ID查询充电枪数量
     *
     * @param stationId 桩站ID
     * @return
     */
    Integer countByStationId(@Param("stationId") Integer stationId);


    /**
     * 根据桩站ID查询充电枪数量
     *
     * @param companyId 公司ID
     * @return
     */
    Integer countByCompanyId(@Param("companyId") Integer companyId);


    /**
     * 根据条件查询 分页或列表查询
     *
     * @param code                  充电枪编号
     * @param stationName           桩站名称
     * @param stationParkingLotCode 桩站车位编号
     * @param status                充电枪状态
     * @param stationId             桩站ID
     * @return
     */
    Page<StationClientGunInfoOut> page(@Param("code") String code,
                                       @Param("stationName") String stationName,
                                       @Param("stationParkingLotCode") String stationParkingLotCode,
                                       @Param("status") Integer status,
                                       @Param("stationId") Integer stationId);


    /**
     * 查询某桩站下的充电枪状态（简单信息）
     *
     * @param stationId 桩站ID
     * @return
     */
    List<StationClientGunInfoSimpleOut> simpleInfoList(@Param("stationId") Integer stationId);

    /**
     * 根据条件查询
     *
     * @param id                    充电枪主键ID
     * @param code                  充电枪编号
     * @param stationParkingLotCode 桩站车位编号
     * @param status                充电枪状态
     * @param stationClientId       充电桩ID
     * @return
     */
    StationClientGunEx getInfoByParams(@Param("id") Integer id,
                                       @Param("code") String code,
                                       @Param("stationParkingLotCode") String stationParkingLotCode,
                                       @Param("status") Integer status,
                                       @Param("stationClientId") Integer stationClientId,
                                       @Param("gunNo") String gunNo);

    /**
     * 根据桩站id查找充电枪类型数量
     *
     * @param stationId
     * @return
     */
    List<StationGunStatistics> getCountGroupByType(@Param("stationId") Integer stationId);

    /**
     * 根据桩站id查找空闲充电枪
     *
     * @param stationId
     * @param status
     * @return
     */
    List<StationGunStatistics> getFreeGunByStationId(@Param("stationId") Integer stationId,
                                                     @Param("status") Integer status);


    /**
     * 充电枪各个状态数量统计
     *
     * @param stationId 桩站ID
     * @return
     */
    GunStatusStatisticsOut getGunStatusStatistics(@Param("stationId") Integer stationId);

    /**
     *  查询充电枪地址详情
     * @param id
     * @return
     */
    StationGunAddressDetail getStationGunAddressDetail(@Param("id") Integer id);
}