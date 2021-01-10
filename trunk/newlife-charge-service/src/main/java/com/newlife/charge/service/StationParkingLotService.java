package com.newlife.charge.service;


import com.newlife.charge.core.domain.StationParkingLot;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.GeneralStationParkingLotPageIn;
import com.newlife.charge.core.dto.in.StationParkingLotSaveIn;
import com.newlife.charge.core.dto.in.StationParkingLotUpdateIn;
import com.newlife.charge.core.dto.out.GeneralStationParkingLotInfoOut;
import com.newlife.charge.core.dto.out.GeneralStationParkingLotPageOut;
import com.newlife.charge.core.dto.out.SelectListOut;

import java.util.List;

/**
 * 桩站车位 service 类
 * <p>
 */
public interface StationParkingLotService {


    /**
     * 总后台 某桩站下的所有未绑定充电枪的车位列表
     *
     * @param stationId 桩站ID
     * @param stationParkingLotId 车位ID
     * @return 结果中会包含传入的车位ID
     */
    List<SelectListOut> selectList(Integer stationId,Integer stationParkingLotId);

    /**
     * 根据桩站ID查询车位数量
     *
     * @param stationId 桩站ID
     * @return
     */
    Integer countByStationId(Integer stationId);


    /**
     * 根据公司ID统计车位数量
     *
     * @param companyId 公司ID
     * @return
     */
    Integer countByCompanyId(Integer companyId);


    /**
     * 总后台 分页查询桩站车位信息
     *
     * @param pageIn
     * @return
     */
    PageInfo<GeneralStationParkingLotPageOut> page(GeneralStationParkingLotPageIn pageIn);


    /**
     * 总后台 桩站车位详情展示
     *
     * @param id 桩站ID
     * @return
     */
    GeneralStationParkingLotInfoOut getInfoById(Integer id);


    /**
     * 根据充电桩id查询车位信息
     *
     * @param stationClientId 充电桩ID
     * @return
     */
    List<StationParkingLot> getInfoByStationClientId(Integer stationClientId);

    /**
     * 总后台 根据车位编号和桩站ID查询车位信息
     *
     * @param code 车位编号
     * @param stationId 桩站ID
     * @return
     */
    StationParkingLot getByCodeAndStationId(String code,Integer stationId);

    /**
     * 总后台车位信息新增
     *
     * @param saveIn
     */
    void save(StationParkingLotSaveIn saveIn);

    /**
     * 总后台桩站车位信息更新
     *
     * @param updateIn
     */
    void update(StationParkingLotUpdateIn updateIn);

    /**
     * 总后台桩站车位单条删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 总后台桩站车位信息批量删除
     *
     * @param ids
     */
    void deletes(int[] ids);


}
