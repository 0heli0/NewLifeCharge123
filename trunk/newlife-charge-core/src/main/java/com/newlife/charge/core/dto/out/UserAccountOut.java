/* -------------------------------------------
 * UserAccount.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-30 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户账户表 v1.0
 */

@Getter
@Setter
@ApiModel(value = "用户账户信息输出")
public class UserAccountOut {

    //主键-用户账户ID
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "用户余额，所有充值金额总和")
    private BigDecimal balance;

    //充值金额-未参与优惠
    @ApiModelProperty(value = "充值金额-未参与优惠（此部分为用户可退款金额）")
    private BigDecimal chargeBalance;

    //优惠充值金额-使用优惠券实付金额
    @ApiModelProperty(value = "优惠充值金额-使用优惠券实付金额（不可退款）")
    private BigDecimal couponChargeBalance;

    //赠送金额-优惠券赠送金额
    @ApiModelProperty(value = "赠送金额-优惠券赠送金额（不可退款）")
    private BigDecimal giveBalance;

}