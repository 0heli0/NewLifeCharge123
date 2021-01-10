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
 * (车主)用户账户充值详情
 */
@Getter
@Setter
@ApiModel
public class NewLifeSpendLogRechargeOut {

    //用户手机号码
    @ApiModelProperty(value = "用户手机号码")
    private String userMobile;

    //资金流水记录id
    @ApiModelProperty(value = "资金流水记录id")
    private Integer id;

    //资金流水号
    @ApiModelProperty(value = "资金流水号")
    private String moneySn;

    //类型（1:账户充值,2:充电消费,3.余额退款)
    @ApiModelProperty(value = "类型(1:账户充值,2:充电消费,3.余额退款)")
    private Integer type;

    //类型中文名称
    @ApiModelProperty(value = "类型中文名称")
    private String typeName;

    //类型备注(1:充值,2:消费,3:退款)
    @ApiModelProperty(value = "类型备注(1:充值,2:消费,3:退款)")
    private Integer typeRemark;

    //类型备注中文名称
    @ApiModelProperty(value = "类型备注中文名称")
    private String typeRemarkName;

    //总金额/预充值套餐最终金额
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "折扣率")
    private String rate;

    //优惠券减免
    @ApiModelProperty(value = "优惠券减免")
    private BigDecimal couponPrice;

    //实付金额
    @ApiModelProperty(value = "实付金额")
    private BigDecimal totalPrice;

    //用户id(车主)
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    //用户历史余额/之前剩余金额
    @ApiModelProperty(value = "用户历史余额/之前剩余金额")
    private BigDecimal userOldBalance;

    //用户余额/剩余金额
    @ApiModelProperty(value = "用户余额/剩余金额")
    private BigDecimal userNowBalance;

    //支付方式
    @ApiModelProperty(value = "支付方式编码")
    private Integer payType;

    //支付方式名称
    @ApiModelProperty(value = "支付方式")
    private String payTypeName;

    //创建时间
    @ApiModelProperty(value = "时间")
    //时间格式化，时区使用东八区
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}