/**
 * Author: zhengyou
 * Date:   2019/4/15 15:41
 * Descripition:启停命令入参
 */
package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel
public class StartStopCommandIn {

    /**
     * 桩编号
     */
    @ApiModelProperty(value = "桩编号")
    private String stationClientCode;

    /**
     * 枪编号
     * 1：1号枪，2：2号枪
     */
    @ApiModelProperty(value = "枪编号,1：1号枪，2：2号枪")
    private String gunCode;

    /**
     * 启停标志
     * 1.启动，2.停止
     */
    @ApiModelProperty(value = "启停标志，1.启动，2.停止")
    private String startStopFlag;
    /**
     * 用户号（目前是手机号）
     */
    @ApiModelProperty(value = "用户号（目前是手机号）")
    private String userNo;

    /**
     * 用户余额/分，不带小数
     */
    @ApiModelProperty(value = "用户余额/分，不带小数")
    private String balance;

    /**
     * 交易号-即订单ID
     * 启动充电时后台分配的唯一流水号，上传账单时必须把该流水号上传到后台，（）
     * 停止充电命令可忽略该流水号
     */
    @ApiModelProperty(value = "交易号-即订单ID，停止充电命令可忽略该流水号")
    private String transactionNo;
    /**
     * 充电模式--默认自动充电
     * 0.自动充电，1.金额模式，2.时间模式，3.电量模式
     */
    @ApiModelProperty(value = "充电模式,0.自动充电，1.金额模式，2.时间模式，3.电量模式")
    private String chargeMode;
    /**
     * 充电参数-默认自动充电
     * 自动充电模式不传，金额模式单位分，时间模式单位秒，电量模式单位0.01KW.H
     */
    @ApiModelProperty(value = "充电参数,自动充电模式不传")
    private String chargeParam;


}
