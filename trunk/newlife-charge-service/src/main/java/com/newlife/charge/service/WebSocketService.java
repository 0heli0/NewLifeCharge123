/**
 * Author: zhengyou
 * Date:   2018/12/11 15:51
 * Descripition:地区Service类
 */
package com.newlife.charge.service;

import com.newlife.charge.core.dto.in.PileHistoryDataIn;
import com.newlife.charge.core.dto.in.PileRealTimeDataIn;

public interface WebSocketService {


    /**
     * 充电时更新实时数据
     * @param gunId     充电枪ID
     * @param status 充电枪状态 1."离线",2"空闲中",3"连接中",4"充电中",5"被预约",6"排队中"
     * @param pileRealTimeDataIn 充电枪实时数据
     * @return
     */
    void sendRealTimeMessage(Integer gunId, Integer status, PileRealTimeDataIn pileRealTimeDataIn);

    /**
     * 插入充电枪返回数据
     * @param id     充电枪ID
     * @param status 充电枪状态 1."离线",2"空闲中",3"连接中",4"充电中",5"被预约",6"排队中"
     * @param pileRealTimeDataIn 充电枪实时数据
     * @return
     */
    void plugInGun(Integer id, Integer status, PileRealTimeDataIn pileRealTimeDataIn);

    /**
     * 充电枪停止操作后操作
     * @param pileHistoryDataIn 硬件传的数据
     */
    void chargeStopSuccess(PileHistoryDataIn pileHistoryDataIn);
}
