package com.newlife.charge.service;


import com.newlife.charge.core.domain.ClientAlarmData;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.ClientAlarmDataPageIn;
import com.newlife.charge.core.dto.in.PileAlarmDataIn;
import com.newlife.charge.core.dto.out.ClientAlarmDataPageOut;

/**
 * 充电桩告警记录 service 类
 * <p>
 */
public interface ClientAlarmDataService {


    /**
     * 总后台 分页查询桩站充电桩信息
     *
     * @param pageIn
     * @return
     */
    PageInfo<ClientAlarmDataPageOut> page(ClientAlarmDataPageIn pageIn);


    /**
     * 根据条件查询
     *
     * @param stationClientCode 充电桩编号
     * @param recordNo          记录流水号
     * @param recordStorageNo   记录存储序号
     * @return
     */
    ClientAlarmData getByParams(String stationClientCode, Integer recordNo, Integer recordStorageNo);


    /**
     * 处理充电桩硬件上报的告警记录数据
     *
     * @param pileAlarmDataIn
     * @return
     */
    int dealPileAlarmData(PileAlarmDataIn pileAlarmDataIn);


}
