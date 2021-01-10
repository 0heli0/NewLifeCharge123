/**
 * Author: zhengyou
 * Date:   2019/4/11 17:11
 * Descripition:充电桩实时数据
 */
package com.newlife.charge.core.dto.in;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PileRealTimeDataIn {

    /**
     * 充电桩编号
     */
    private String stationClientCode;

    /**
     * 枪编号
     */
    private String gunCode;


    /**
     * 设备状态
     *
     * @see com.newlife.charge.core.enums.PileStatusEnum
     */
    private String pileStatus;

    /**
     * 设备状态中文显示
     *
     * @see com.newlife.charge.core.enums.PileStatusEnum
     */
    private String pileStatusStr;

    /**
     * 充电卡号(外部用户号/手机号)
     */
    private String chargeCardNo;

    /**
     * 车辆VIN码
     */
    private String carVIN;

    /**
     * 充电电压
     * 一位小数（桩端电表读数）
     */
    private String chargeVoltage;

    /**
     * 充电电流
     * 两位小数（桩端电表读数）
     */
    private String chargeElectricity;
    /**
     * 充电时间/秒
     */
    private String chargeTime;
    /**
     * 充电金额
     * 两位小数
     */
    private String chargeAmount;
    /**
     * 充电电能
     * 两位小数
     */
    private String chargeEnergy;
    /**
     * 剩余时间
     * 只限直流
     */
    private String leftTime;
    /**
     * 当前SOC
     * 只限直流
     */
    private String currentSoc;
    /**
     * 告警信息
     * 8字节
     */
    private String alarmInfo;
    /**
     * 充电卡余额
     * 0x0B0C 4字节
     */
    private String chargeCardBalance;
    /**
     * 充电卡类型
     */
    private String chargeCardType;
    /**
     * 充电方式
     * <p>
     * 0:立即充电 1：预约充电
     */
    private String chargeWay;
    /**
     * 充电模式
     */
    private String chargeMode;
    /**
     * 电压需求
     * 只限直流
     */
    private String voltageDemandDC;
    /**
     * 电流需求
     * 只限直流
     */
    private String electricityDemandDC;
    /**
     * 车位锁状态（即地锁）
     */
    private String lockStatus;
    /**
     * 当前电能表读数单 0.01KWH
     */
    private String currentReadList;
    /**
     * 充电电压
     * 一位小数（直流桩充电电压）
     */
    private String chargeVoltageDC;
    /**
     * 充电电流
     * 两位小数（直流桩充电电流）
     */
    private String chargeElectricityDC;


    @Override
    public String toString() {
        return "PileRealTimeDataOut{" +
                "pileStatus='" + pileStatus + '\'' +
                ", pileStatusStr='" + pileStatusStr + '\'' +
                ", chargeCardNo='" + chargeCardNo + '\'' +
                ", carVIN='" + carVIN + '\'' +
                ", chargeVoltage='" + chargeVoltage + '\'' +
                ", chargeElectricity='" + chargeElectricity + '\'' +
                ", chargeTime='" + chargeTime + '\'' +
                ", chargeAmount='" + chargeAmount + '\'' +
                ", chargeEnergy='" + chargeEnergy + '\'' +
                ", leftTime='" + leftTime + '\'' +
                ", currentSoc='" + currentSoc + '\'' +
                ", alarmInfo='" + alarmInfo + '\'' +
                ", chargeCardBalance='" + chargeCardBalance + '\'' +
                ", chargeCardType='" + chargeCardType + '\'' +
                ", chargeWay='" + chargeWay + '\'' +
                ", chargeMode='" + chargeMode + '\'' +
                ", voltageDemandDC='" + voltageDemandDC + '\'' +
                ", electricityDemandDC='" + electricityDemandDC + '\'' +
                ", lockStatus='" + lockStatus + '\'' +
                ", currentReadList='" + currentReadList + '\'' +
                ", chargeVoltageDC='" + chargeVoltageDC + '\'' +
                ", chargeElectricityDC='" + chargeElectricityDC + '\'' +
                '}';
    }
}
