/* -------------------------------------------
 * ClientAlarmDataMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-06-05 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.ClientAlarmData;
import com.newlife.charge.core.dto.out.ClientAlarmDataPageOut;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface ClientAlarmDataMapper extends CrudRepository<ClientAlarmData> {


    /**
     * 总后台 分页查询
     *
     * @param stationId 桩站ID
     * @param startTime 告警开始时间起
     * @param endTime   告警开始时间止
     * @return
     */
    Page<ClientAlarmDataPageOut> page(@Param("stationId") Integer stationId,
                                      @Param("startTime") Date startTime,
                                      @Param("endTime") Date endTime);


    /**
     * 根据条件查询
     *
     * @param stationClientCode 充电桩编号
     * @param recordNo          记录流水号
     * @param recordStorageNo   记录存储序号
     * @return
     */
    ClientAlarmData getByParams(@Param("stationClientCode") String stationClientCode,
                                @Param("recordNo") Integer recordNo,
                                @Param("recordStorageNo") Integer recordStorageNo);
}