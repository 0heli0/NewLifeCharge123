/* -------------------------------------------
 * StationClientMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-29 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.StationClient;
import com.newlife.charge.core.domain.exModel.StationClientEx;
import com.newlife.charge.core.dto.out.StationClientGunNumOut;
import com.newlife.charge.core.dto.out.StationClientPageOut;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationClientMapper extends CrudRepository<StationClient> {



    /**
     * RPC 更新充电桩下所有充电枪状态
     *
     * @param codeList 充电桩编号列表
     * @param status   充电枪状态
     * @return
     */
    int updateGunStatusByClientCodeList(@Param("codeList") List<String> codeList, @Param("status") Integer status);


    /**
     * 获取平台所有充电桩编号
     * @return
     */
    List<String> getAllStationClientCode();


    /**
     * 获取某类型的某桩站下充电桩最大编号
     *
     * @param stationId   桩站ID
     * @param stationType 桩站类型
     * @return
     */
    String getMaxCode(@Param("stationId") Integer stationId, @Param("stationType") Integer stationType);


    /**
     * 某桩站下所有充电桩列表查询
     *
     * @param stationId 桩站ID，不传则查所有
     * @return
     */
    List<StationClient> getByStationId(@Param("stationId") Integer stationId);

    /**
     * 统计充电桩绑定的充电枪数量
     *
     * @param stationId 桩站ID，不传则查所有
     * @return
     */
    List<StationClientGunNumOut> getGunNumGroupByStationClientId(@Param("stationId") Integer stationId);


    /**
     * 根据桩站ID查询充电桩数量
     *
     * @param stationId
     * @return
     */
    Integer countByStationId(@Param("stationId") Integer stationId);


    /**
     * 根据公司ID统计充电桩数量
     *
     * @param companyId 公司ID
     * @return
     */
    Integer countByCompanyId(@Param("companyId") Integer companyId);


    /**
     * 分页或列表查询
     *
     * @param code    充电桩编号
     * @param gunType 枪类型
     * @return
     */
    Page<StationClientPageOut> page(@Param("code") String code, @Param("gunType") Integer gunType);


    /**
     * 根据充电桩编码查询
     *
     * @return
     */
    StationClientEx getByCode( @Param("code") String code);


    /**
     * 总后台 根据充电桩编号列表查询
     *
     * @param codeList 充电桩编号列表
     * @return
     */
    List<StationClientEx> getByCodeList(@Param("codeList") List<String> codeList);


    /**
     * 根据参数查询
     *
     * @return
     */
    StationClientEx getInfoByParams(@Param("id") Integer id, @Param("code") String code,
                                    @Param("terminalNo") String terminalNo);

}