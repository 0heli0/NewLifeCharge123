package com.newlife.charge.service;


import com.newlife.charge.core.domain.StationClient;
import com.newlife.charge.core.domain.exModel.StationClientEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.StationClientPageIn;
import com.newlife.charge.core.dto.in.StationClientSaveIn;
import com.newlife.charge.core.dto.in.StationClientUpdateIn;
import com.newlife.charge.core.dto.out.SelectListOut;
import com.newlife.charge.core.dto.out.StationClientGunNumOut;
import com.newlife.charge.core.dto.out.StationClientInfoOut;
import com.newlife.charge.core.dto.out.StationClientPageOut;

import java.util.List;

/**
 * 桩站充电桩 service 类
 * <p>
 */
public interface StationClientService {

    /**
     * 获取平台所有充电桩编号
     *
     * @return
     */
    List<String> getAllStationClientCode();


    /**
     * 总后台 所有充电桩列表查询
     *
     * @param stationId 桩站ID
     * @return
     */
    List<StationClient> getByStationId(Integer stationId);


    /**
     * 统计充电桩绑定的充电枪数量
     *
     * @param stationId 桩站ID，不传则查所有
     * @return
     */
    List<StationClientGunNumOut> getGunNumGroupByStationClientId(Integer stationId);


    /**
     * 总后台 充电枪未达到绑定上限的充电桩列表查询
     *
     * @param stationId       桩站ID,不传则查所有
     * @param stationClientId 充电桩ID,返回结果会包含此ID对应的充电桩
     * @return
     */
    List<SelectListOut> selectList(Integer stationId, Integer stationClientId);


    /**
     * 根据公司ID统计充电桩数量
     *
     * @param companyId 公司ID
     * @return
     */
    Integer countByCompanyId(Integer companyId);


    /**
     * 根据桩站ID查询充电桩数量
     *
     * @param stationId 桩站ID
     * @return
     */
    Integer countByStationId(Integer stationId);


    /**
     * 总后台 分页查询桩站充电桩信息
     *
     * @param pageIn
     * @return
     */
    PageInfo<StationClientPageOut> page(StationClientPageIn pageIn);


    /**
     * 总后台 桩站充电桩详情展示
     *
     * @param id 桩站ID
     * @return
     */
    StationClientInfoOut getInfoById(Integer id);


    /**
     * 总后台 根据充电桩编号查询
     *
     * @param code 充电桩编号
     * @return
     */
    StationClientEx getByCode(String code);

    /**
     * 总后台 根据充电桩编号列表查询
     *
     * @param codeList 充电桩编号列表
     * @return
     */
    List<StationClientEx> getByCodeList(List<String> codeList);


    /**
     * 总后台充电桩信息新增
     *
     * @param saveIn
     */
    void save(StationClientSaveIn saveIn);

    /**
     * 总后台桩站充电桩信息更新
     *
     * @param updateIn
     */
    void update(StationClientUpdateIn updateIn);

    /**
     * 总后台桩站充电桩单条删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 总后台桩站充电桩信息批量删除
     *
     * @param ids
     */
    void deletes(int[] ids);


    /**
     * 获取某类型的某桩站下充电桩最大编号
     *
     * @param stationId   桩站ID
     * @param stationType 桩站类型
     * @return
     */
    String getMaxCode(Integer stationId, Integer stationType);


}
