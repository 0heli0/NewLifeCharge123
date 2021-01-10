/**
 * Author: zhengyou
 * Descripition:充电桩历史数据
 */
package com.newlife.charge.core.dto.in;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
public class PileHistoryDataIn {

    /**
     * 充电桩编号
     */
    private String stationClientCode;

    /**
     * 枪编号
     */
    private String gunCode;


    /**
     * 充电方式
     * 0:立即充电 1：预约充电
     */
    private Integer chargeWay;
    /**
     * 充电模式
     * 0.自动充电 1.金额模式 2.时间模式 3.电量模式
     */
    private Integer chargeMode;

    /**
     * 充电卡类型
     * -应该只有113
     * 1.用户卡 81.员工卡 97.充值卡 112.在线卡 113.APP用户 120.VIN码
     */
    private Integer chargeCardType;


    /**
     * 充电卡号(用户手机号)
     */
    private String chargeCardNo;

    /**
     * 车辆VIN码
     * 0x0105 17字节Hex
     */
    private String carVin;

    /**
     * 充电前余额
     * 0x0106 4字节
     * 两位小数
     */
    private String babe;


    /**
     * 充电电压
     * 0x0107 4字节
     * 一位小数
     */
    private String chargeVoltage;

    /**
     * 充电电流
     * 两位小数
     */
    private String chargeElectricity;


    /**
     * 充电时间/秒
     *
     */
    private String chargeTime;

    /**
     * 充电金额
     *  两位小数
     */
    private String chargeAmount;

    /**
     * 充电电能
     * 0x010B 4字节
     * 两位小数
     */
    private String chargeEnergy;

    /**
     * 充电开始电能
     * 0x010C 4字节
     * 两位小数
     */
    private String chargeEnergyStart;

    /**
     * 充电结束电能
     * 0x010D 4字节 两位小数
     */
    private String chargeEnergyEnd;


    /**
     * 剩余时间
     * 0x010E 4字节 只限直流
     */
    private String leftTime;

    /**
     * 当前SOC
     * 0x010F 1字节 只限直流
     */
    private String currentSoc;

    /**
     * 是否上传主站
     * 0x0110 1字节
     * 0.没有上传 1.上传
     */
    private Integer uploadMaster;

    /**
     * 是否付费
     * 0x0111 1字节
     * 0.没有正常付费 1.已经付费
     */
    private Integer pay;

    /**
     * 充电终止原因
     * 0x0112 1字节
     * 0.正常接收 1.异常结束
     */
    private Integer chargeEndReason;


    /**
     * 充电开始时间
     * 0x0113 7字节 BCD码
     */
    private Date chargeTimeStart;

    /**
     * 充电结束时间
     * 0x0114 7字节 BCD码
     */
    private Date chargeTimeEnd;


    /**
     * 记录流水号--总后台启动充电时下发的交易号（transactionNo）--TODO:暂定
     * 0x0115 4字节
     */
    private Integer recordNo;

    /**
     * 记录存储序号
     * 0x0116 4字节
     */
    private Integer recordStorageNo;

    /**
     * 10个时段充电电量和充电金额
     * 0x0117 80字节
     * 0-3字节表阶段1充电电量，4-7字节表示阶段1充电金额
     */
    private List<StageDataIn> stageDataInList ;


    @Override
    public String toString() {
        return "PileHistoryDataOut{" +
                "stationClientCode='" + stationClientCode + '\'' +
                ", gunCode='" + gunCode + '\'' +
                ", chargeWay='" + chargeWay + '\'' +
                ", chargeMode='" + chargeMode + '\'' +
                ", chargeCardType='" + chargeCardType + '\'' +
                ", chargeCardNo='" + chargeCardNo + '\'' +
                ", carVin='" + carVin + '\'' +
                ", babe='" + babe + '\'' +
                ", chargeVoltage='" + chargeVoltage + '\'' +
                ", chargeElectricity='" + chargeElectricity + '\'' +
                ", chargeTime='" + chargeTime + '\'' +
                ", chargeAmount='" + chargeAmount + '\'' +
                ", chargeEnergy='" + chargeEnergy + '\'' +
                ", chargeEnergyStart='" + chargeEnergyStart + '\'' +
                ", chargeEnergyEnd='" + chargeEnergyEnd + '\'' +
                ", leftTime='" + leftTime + '\'' +
                ", currentSoc='" + currentSoc + '\'' +
                ", uploadMaster='" + uploadMaster + '\'' +
                ", pay='" + pay + '\'' +
                ", chargeEndReason='" + chargeEndReason + '\'' +
                ", chargeTimeStart='" + chargeTimeStart + '\'' +
                ", chargeTimeEnd='" + chargeTimeEnd + '\'' +
                ", recordNo='" + recordNo + '\'' +
                ", recordStorageNo='" + recordStorageNo + '\'' +
                ", stageDataInList=" + stageDataInList +
                '}';
    }




}
