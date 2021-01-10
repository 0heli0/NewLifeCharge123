/* -------------------------------------------
 * Order.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 微信支付订单详情表 v1.0
 */

@Getter
@Setter
public class WeiChatOrderDetailEx {

    //主键-订单id
    private Integer id;

    //订单编号
    private String orderSn;

    // 优惠券id
    private Integer couponId;

    //订单类型（1:用户充值，2:用户退款,3:用户充电，4:桩站结算）
    private Integer orderType;

    //用户id
    private Integer userId;

    //支付金额
    private BigDecimal totalPrice;

    // 优惠券金额
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