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
 * 微信支付统一下单参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "微信支付统一下单参数")
@XStreamAlias("xml")
public class UnifiedOrderParams {

    @ApiModelProperty(value = "小程序ID,必传")
    @XStreamAlias("appid")
    @NotEmpty(message = "小程序ID，不能为空")
    private String appId;

    @ApiModelProperty(value = "商户号,必传")
    @XStreamAlias("mch_id")
    @NotEmpty(message = "商户号，不能为空")
    private String mchId;

    @ApiModelProperty(value = "设备号")
    @XStreamAlias("device_info")
    private String deviceInfo;

    @ApiModelProperty(value = "随机字符串，长度要求在32位以内,必传")
    @XStreamAlias("nonce_str")
    private String nonceStr;

    @ApiModelProperty(value = "签名,必传")
    @XStreamAlias("sign")
    private String sign;

    @ApiModelProperty(value = "签名类型，默认为MD5，支持HMAC-SHA256和MD5")
    @XStreamAlias("sign_type")
    private String signType;

    @ApiModelProperty(value = "商品描述,必传")
    @XStreamAlias("body")
    @NotEmpty(message = "商品描述，不能为空")
    private String body;

    @ApiModelProperty(value = "附加数据")
    @XStreamAlias("attach")
    private String attach;

    @ApiModelProperty(value = "商户订单号,必传")
    @XStreamAlias("out_trade_no")
    @NotEmpty(message = "商户订单号，不能为空")
    private String outTradeNo;

    @ApiModelProperty(value = "标价币种")
    @XStreamAlias("fee_type")
    private String feeType;

    @ApiModelProperty(value = "标价金额，单位 分,必传")
    @XStreamAlias("total_fee")
    @NotEmpty(message = "标价金额，不能为空")
    private int totalFee;

    @ApiModelProperty(value = "终端IP,必传")
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    @ApiModelProperty(value = "交易起始时间")
    @XStreamAlias("time_start")
    private String timeStart;

    @ApiModelProperty(value = "交易结束时间")
    @XStreamAlias("time_expire")
    private String timeExpire;

    @ApiModelProperty(value = "异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数,必传")
    @XStreamAlias("notify_url")
    private String notifyUrl;

    @ApiModelProperty(value = "交易类型,必传")
    @XStreamAlias("trade_type")
    @NotEmpty(message = "交易类型，不能为空")
    private String tradeType;

    @ApiModelProperty(value = "商品ID")
    @XStreamAlias("product_id")
    private String productId;

    @ApiModelProperty(value = "指定支付方式")
    @XStreamAlias("limit_pay")
    private String limitPay;

    @ApiModelProperty(value = "用户标识")
    @XStreamAlias("openid")
    private String openid;

    @ApiModelProperty(value = "receipt")
    @XStreamAlias("receipt")
    private String receipt;

    @ApiModelProperty(value = "场景信息")
    @XStreamAlias("scene_info")
    private String sceneInfo;

    @Override
    public String toString() {
        return "UnifiedOrderParams{" +
                "appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", signType='" + signType + '\'' +
                ", body='" + body + '\'' +
                ", attach='" + attach + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", feeType='" + feeType + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", spbillCreateIp='" + spbillCreateIp + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeExpire='" + timeExpire + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", productId='" + productId + '\'' +
                ", limitPay='" + limitPay + '\'' +
                ", openid='" + openid + '\'' +
                ", receipt='" + receipt + '\'' +
                '}';
    }
}