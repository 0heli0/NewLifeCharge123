package com.newlife.charge.service;


import com.newlife.charge.core.domain.ClientHistoryData;
import com.newlife.charge.core.dto.in.PileHistoryDataIn;

/**
 * 充电桩充电历史数据（即硬件充电账单） service 类
 * <p>
 */
public interface ClientHistoryDataService {


    /**
     * 根据条件查询
     *
     * @param stationClientCode 充电桩编号
     * @param recordNo          记录流水号
     * @param recordStorageNo   记录存储序号
     * @return
     */
    ClientHistoryData getByParams(String stationClientCode, Integer recordNo, Integer recordStorageNo);


    /**
     * 处理充电桩硬件上报的历史数据
     *
     * @param pileHistoryDataIn
     * @return
     */
    int dealPileHistoryData(PileHistoryDataIn pileHistoryDataIn);

}
