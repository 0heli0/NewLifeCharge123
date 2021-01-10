/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeEnum;
import com.newlife.charge.core.enums.PayTypeEnum;
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
@ApiModel(value = "车主端 用户账户充值详情")
public class UserNewLifeSpendLogRechargeOut {


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

    //总金额/预充值套餐最终金额
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

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

    @ApiModelProperty(value = "时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    public String getTypeName() {
        if(this.type == null ){
            return "";

        }else if(NewLifeSpendLogTypeEnum.USER_RECHARGE.getValue() == type){
            typeName = NewLifeSpendLogTypeEnum.USER_RECHARGE.getDescription();

        }else if(NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getValue() == type){
            typeName = NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getDescription();

        }else if(NewLifeSpendLogTypeEnum.BALANCE_REFUND.getValue() == type){
            typeName = NewLifeSpendLogTypeEnum.BALANCE_REFUND.getDescription();

        }
        return typeName;
    }

    public Date getCreateTimestamp() {
        if(this.createTime != null ){
            this.createTimestamp = this.createTime;
        }
        return createTimestamp;
    }

    public String getPayTypeName() {
        if(this.payType == null) {
            return "";

        }else if(PayTypeEnum.WEIXIN.getValue() == this.payType){
            this.payTypeName = PayTypeEnum.WEIXIN.getDescription();

        }else if(PayTypeEnum.ACCOUNT_BALANCE.getValue() == this.payType){
            this.payTypeName = PayTypeEnum.ACCOUNT_BALANCE.getDescription();

        }else if(PayTypeEnum.ALIPAY.getValue() == this.payType){
            this.payTypeName = PayTypeEnum.ALIPAY.getDescription();

        }
        return payTypeName;
    }
}