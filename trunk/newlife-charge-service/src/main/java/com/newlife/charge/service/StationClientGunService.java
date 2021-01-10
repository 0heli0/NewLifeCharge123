package com.newlife.charge.service;


import com.newlife.charge.core.domain.StationClientGun;
import com.newlife.charge.core.domain.exModel.StationClientGunEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;

import java.util.List;

/**
 * 桩站充电枪 service 类
 * <p>
 */
public interface StationClientGunService {


    /**
     * RPC 更新充电桩下所有充电枪状态
     *
     * @param codeList 充电桩编号列表
     * @param status   充电枪状态
     * @return
     */
    int updateStatusByClientCodeList(List<String> codeList, Integer status);

    /**
     * 更新某充电桩下所有充电枪状态
     *
     * @param stationClientId 充电桩ID
     * @return
     */
    int updateStatusByStationClientId(Integer stationClientId, Integer status);


    /**
     * 更新充电枪状态
     *
     * @param id     充电枪ID
     * @param status 充电枪状态
     * @param pileRealTimeDataIn 充电枪实时数据
     * @return
     */
    int updateStatus(Integer id, Integer status,PileRealTimeDataIn pileRealTimeDataIn);


    /**
     * 总后台 某充电桩下未使用的枪编号列表
     *
     * @param stationClientId      充电桩Id
     * @param stationClientGunCode 枪编号
     * @return 结果中会包含传入的枪编号
     */
    List<SelectListOut> selectList(Integer stationClientId, String stationClientGunCode);


    /**
     * 根据充电桩id查询充电枪信息
     *
     * @param stationClientId 充电桩ID
     * @return
     */
    List<StationClientGun> getInfoByStationClientId(Integer stationClientId);


    /**
     * 根据车位ID查询
     *
     * @param stationParkingLotId 车位ID
     * @return
     */
    List<StationClientGun> getByStationParkingLotId(Integer stationParkingLotId);


    /**
     * 根据桩站ID查询充电枪数量
     *
     * @param stationId 桩站ID
     * @return
     */
    Integer countByStationId(Integer stationId);


    /**
     * 根据公司ID统计充电枪数量
     *
     * @param companyId 公司ID
     * @return
     */
    Integer countByCompanyId(Integer companyId);


    /**
     * 总后台 分页查询桩站充电枪信息
     *
     * @param pageIn
     * @return
     */
    PageInfo<StationClientGunInfoOut> page(StationClientGunPageIn pageIn);


    /**
     * 总后台首页充电桩状态分页查询
     *
     * @param pageIn
     * @return
     */
    PageInfo<GeneralIndexStationClientGunInfoOut> generalIndexGunStatusPageList(GeneralIndexStationClientGunPageIn pageIn);


    /**
     * 总后台 桩站充电枪详情展示
     *
     * @param id 充电枪主键ID
     * @return
     */
    StationClientGunInfoOut getInfoById(Integer id);


    /**
     * 总后台 查询某桩站下的所有充电枪
     *
     * @param stationId 桩站ID
     * @return
     */
    List<StationClientGunInfoOut> getListByStationId(Integer stationId);


    /**
     * 总后台 查询某桩站下的所有充电枪简单信息
     *
     * @param stationId 桩站ID
     * @return
     */
    List<StationClientGunInfoSimpleOut> getSimpleInfoListByStationId(Integer stationId);


    /**
     * 总后台 根据充电枪编号查询
     *
     * @param code            充电枪编号
     * @param stationClientId 充电桩ID
     * @return
     */
    StationClientGunEx getByCode(String code, Integer stationClientId);


    /**
     * 充电枪各个状态数量统计
     * 总后台、桩站端都可以用
     *
     * @param stationId 桩站ID,不传则查询所有
     * @return
     */
    GunStatusStatisticsOut getGunStatusStatistics(Integer stationId);


    /**
     * 总后台充电枪信息新增
     *
     * @param saveIn
     */
    void save(StationClientGunSaveIn saveIn);

    /**
     * 总后台桩站充电枪信息更新
     *
     * @param updateIn
     */
    void update(StationClientGunUpdateIn updateIn);

    /**
     * 总后台桩站充电枪单条删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 总后台桩站充电枪信息批量删除
     *
     * @param ids
     */
    void deletes(int[] ids);

    /**
     * @Description: 桩站充电枪各种状态数量查询
     * @Author: Linzq
     * @CreateDate: 2019/5/7 0007 16:55
     */
    ClientGunStatusCountOut stationClientGunStatus(StationClientGunPageIn stationClientGunPageIn);

    /**
     * 启动充电
     * @param startStopCommandIn
     */
    String startCharge(StartStopCommandIn startStopCommandIn);

    /**
     * 停止充电
     * @param startStopCommandIn
     */
    String stopCharge(StartStopCommandIn startStopCommandIn);




}
