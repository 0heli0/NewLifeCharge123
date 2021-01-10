package com.newlife.charge.service;


import com.newlife.charge.core.dto.in.ChargeParamsIn;
import com.newlife.charge.core.dto.in.ChargeTimeDataIn;
import com.newlife.charge.core.dto.in.InfoIn;
import com.newlife.charge.core.dto.in.PlugInGunIn;
import com.newlife.charge.core.dto.out.*;

import java.util.List;

/**
 * 车主端 桩站信息管理 service 类
 * <p>
 */
public interface ClientStationService {

    /**
     * 用户端充电站详情
     * @param infoIn
     * @return
     */
    ClientStationDetailOut stationDetail(InfoIn infoIn);

    /**
     * 时段电价介绍
     * @param infoIn
     * @return
     */
    List<ClientStationTimePriceOut> timePriceDetail(InfoIn infoIn);

    /**
     * 查看桩站车位列表
     * @param infoIn
     * @return
     */
    List<ClientTruckSpaceOut> truckSpaceList(InfoIn infoIn);

    /**
     * 查看桩站车位详情信息
     * @param infoIn 枪id
     * @param userId 用户id
     * @return
     */
    ClientTruckSpaceOut truckSpaceDetail(InfoIn infoIn, Integer userId);

    /**
     * 选定车位操作及返回结果
     * @param infoIn
     * @param userId
     * @return
     */
    BindingParkingOut bindingParking(InfoIn infoIn, Integer userId);

    /**
     * 取消选定车位
     * @param infoIn 充电枪id
     * @param userId
     */
    int cancelBinding(InfoIn infoIn, Integer userId);

    /**
     * 插入枪后操作
     * @param infoIn
     */
    void plugInGun(PlugInGunIn infoIn);

    /**
     * 用户确认充电操作
     * @param infoIn
     */
    ChargeBeginOut chargeBegin(ChargeParamsIn infoIn, Integer userId);

    /**
     * 用户停止充电
     * @param infoIn
     * @param userId
     * @param type 结束充电类型 0：手动结束，1：余额不足结束
     */
    void stopCharge(ChargeParamsIn infoIn, Integer userId, Integer type);

    /**
     * 车主端 首页查看充电状态
     * @param userId
     * @return
     */
    ChargeStatusOut chargeStatus(Integer userId);

    /**
     * 车主端 充电时获取实时充电数据
     * @param infoIn
     * @return
     */
    ChargeBeginOut getTimeData(ChargeTimeDataIn infoIn, Integer userId);

    /**
     * 查看桩站车位详情信息
     * @param userId 用户id
     * @return
     */
    ClientTruckSpaceOut jumpToTruckSpaceDetail(Integer userId);

    /**
     *  获取用电结果
     * @param orderSn
     * @param userId
     * @return
     */
    ChargeEndOut getChargeLogData(String orderSn, Integer userId);
}
