/* -------------------------------------------
 * UserRechargeLogOut.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户账户余额记录 v1.0
 */
@Getter
@Setter
@ApiModel(value = "车主端-用户账户余额记录")
public class UserRechargeLogOut {

    @ApiModelProperty(value = "资金流水记录id")
    private Integer id;

    @ApiModelProperty(value = "消费类型(1.账户充值（收入），2.充电消费（支出），3.余额退款（支出）)")
    private Integer type;

    @ApiModelProperty(value = "消费类型字符串")
    private String typeStr;

    @ApiModelProperty(value = "用户消费后剩余金额")
    private BigDecimal userNowBalance;

    @ApiModelProperty(value = "用户消费前剩余金额")
    private BigDecimal userGainAmount;

    @ApiModelProperty(value = "消费金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "交易完成时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "交易完成时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    public String getTypeStr() {

        if(NewLifeSpendLogTypeEnum.USER_RECHARGE.getValue() == type){
            typeStr = NewLifeSpendLogTypeEnum.USER_RECHARGE.getDescription();

        }else if(NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getValue() == type){
            typeStr = NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getDescription();

        }else if(NewLifeSpendLogTypeEnum.BALANCE_REFUND.getValue() == type){
            typeStr = NewLifeSpendLogTypeEnum.BALANCE_REFUND.getDescription();

        }else if(NewLifeSpendLogTypeEnum.SELL_ELECTRIC_BILL.getValue() == type){
            typeStr = NewLifeSpendLogTypeEnum.SELL_ELECTRIC_BILL.getDescription();

        }

        return typeStr;
    }

    public Date getCreateTimestamp() {
        if(this.createTime != null){
            this.createTimestamp = this.createTime;
        }
        return this.createTimestamp;
    }
}