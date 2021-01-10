/**
 * Author: zhengyou
 * Descripition:充电桩告警记录数据
 */
package com.newlife.charge.core.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class PileAlarmDataIn implements Serializable {

    /**
     * 充电桩编号
     */
    private String stationClientCode;

    /**
     * 枪编号
     */
    private String gunCode;


    /**
     * 告警点
     * TODO:告警点标号，如何解析
     * 0x0201 4字节
     */
    private String alarmPoint;

    /**
     * 告警点中文解释
     */
    private String alarmPointName;


    /**
     * 告警原因
     * 0x0202 4字节
     */
    private String alarmReason;

    /**
     * 告警原因中文解释
     */
    private String alarmReasonName;


    /**
     * 告警开始时间
     * 0x0203 7字节 BCD码
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date alarmTimeStart;

    /**
     * 告警结束时间
     * 0x0204 7字节 BCD码
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date alarmTimeEnd;

    /**
     * 告警持续时间/秒
     * 0x0205 4字节
     */
    private String alarmTime;

    /**
     * 是否影响充电
     * 0x0206 1字节
     * 0.无影响 1.有影响
     */
    private Integer affectCharge;


    /**
     * 是否上传主站
     * 0x0207 1字节
     * 0.无 1.有
     */
    private Integer uploadMaster;

    /**
     * 记录流水号--总后台启动充电时下发的交易号（transactionNo）
     * 0x0208 4字节
     */
    private Integer recordNo;

    /**
     * 记录存储序号
     * 0x0209 4字节
     */
    private Integer recordStorageNo;


    public PileAlarmDataIn() {
    }


    @Override
    public String toString() {
        return "PileAlarmDataIn{" +
                "stationClientCode='" + stationClientCode + '\'' +
                ", gunCode='" + gunCode + '\'' +
                ", alarmPoint='" + alarmPoint + '\'' +
                ", alarmPointName='" + alarmPointName + '\'' +
                ", alarmReason='" + alarmReason + '\'' +
                ", alarmReasonName='" + alarmReasonName + '\'' +
                ", alarmTimeStart=" + alarmTimeStart +
                ", alarmTimeEnd=" + alarmTimeEnd +
                ", alarmTime='" + alarmTime + '\'' +
                ", affectCharge=" + affectCharge +
                ", uploadMaster=" + uploadMaster +
                ", recordNo=" + recordNo +
                ", recordStorageNo=" + recordStorageNo +
                '}';
    }
}
