/* -------------------------------------------
 * Order.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 订单表 v1.0
 */

@Getter
@Setter
public class Order {

    //主键-订单id
    private Integer id;

    //订单编号
    private String orderSn;

    //订单类型（1:用户充值，2:用户退款,3:用户充电，4:桩站结算）
    private Integer orderType;

    //用户id
    private Integer userId;

    //订单总额（支付金额+优惠券减免金额）
    private BigDecimal sumPrice;

    //支付金额
    private BigDecimal totalPrice;

    //优惠券减免金额
    private BigDecimal couponPrice;

    //支付状态（0:未支付,1:支付成功,2:支付失败）
    private Integer payStatus;

    //支付方式（1:微信支付,2:账户余额）
    private Integer payType;

    //支付时间
    private Date payTime;

    //创建时间
    private Date createTime;

}