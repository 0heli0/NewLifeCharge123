/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 总后台 桩站卖电流水详情
 */
@Getter
@Setter
@ApiModel
public class StationChargeOut {

    //充电记录主键ID
    @ApiModelProperty(value = "充电记录ID")
    private Integer id;

    //订单编号/流水号
    @ApiModelProperty(value = "流水号")
    private String orderSn;

    //类型
    @ApiModelProperty(value = "类型")
    private Integer type;

    //类型中文名称
    @ApiModelProperty(value = "类型中文名称")
    private String typeName;

    //类型备注
    @ApiModelProperty(value = "类型备注")
    private Integer typeRemark;

    //类型备注中文名称
    @ApiModelProperty(value = "类型备注中文名称")
    private String typeRemarkName;

    //桩站ID
    @ApiModelProperty(value = "桩站ID")
    private Integer stationId;

    //桩站账号
    @ApiModelProperty(value = "桩站账号")
    private String stationUserMobile;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    //充电枪编号
    @ApiModelProperty(value = "充电枪编号")
    private String stationClientGunCode;

    //车位编号
    @ApiModelProperty(value = "车位编号")
    private String stationParkingLotCode;

    //充电桩编号
    @ApiModelProperty(value = "充电桩编号")
    private String stationClientCode;


    //充电方式代码
    @ApiModelProperty(value = "充电方式代码")
    private Integer clientChargeType;

    //充电方式名称
    @ApiModelProperty(value = "充电方式")
    private String clientChargeTypeName;

    //涉及到的阶段电价，本次充电过程涉及到的各阶段电价（比如：0.5000、0.6523、0.7600、0.4320）
    @ApiModelProperty(value = "电价")
    private String chargePrices;


    //服务费价格
    @ApiModelProperty(value = "服务费价格")
    private BigDecimal servicePrice;

    //充电开始时间
    @ApiModelProperty(value = "充电开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date beginTime;

    //充电结束时间
    @ApiModelProperty(value = "充电开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    //充电时长
    @ApiModelProperty(value = "充电时长")
    private String chargingTime;

    //实际充电度数/充入电量
    @ApiModelProperty(value = "充入电量")
    private BigDecimal degreeReal;

    //操作金额/消费金额
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    //优惠券减免金额
    @ApiModelProperty(value = "优惠券金额")
    private BigDecimal couponPrice;

    //折扣率(%)
    @ApiModelProperty(value = "折扣率")
    private String rate;

    //实付金额
    @ApiModelProperty(value = "实付金额")
    private BigDecimal priceReal;

    //用户手机号码/消费账号
    @ApiModelProperty(value = "消费账号")
    private String userMobile;

    //创建时间
    @ApiModelProperty(value = "时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //车牌号
    @ApiModelProperty(value = "车牌号")
    private String plateNumber;

}

