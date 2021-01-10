/* -------------------------------------------
 * UnifiedOrderParams.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 微信支付统查询支付成功参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "微信支付统查询支付成功参数")
@XStreamAlias("xml")
public class CheckOrderParams {

    // "小程序ID
    @XStreamAlias("appid")
    private String appId;

    // "商户号)
    @XStreamAlias("mch_id")
    private String mchId;

    // "微信订单号)
    @XStreamAlias("transaction_id")
    private String transactionId;

    // "商户订单号)
    @XStreamAlias("out_trade_no")
    @NotEmpty(message = "商户订单号，不能为空")
    private String outTradeNo;

    // "随机字符串，长度要求在32位以内)
    @XStreamAlias("nonce_str")
    private String nonceStr;

    // "签名)
    @XStreamAlias("sign")
    private String sign;

    // "签名类型，默认为MD5，支持HMAC-SHA256和MD5")
    @XStreamAlias("sign_type")
    private String signType;

}