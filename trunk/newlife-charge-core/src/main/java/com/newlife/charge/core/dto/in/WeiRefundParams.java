/* -------------------------------------------
 * WeiRefundParams.java
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

import java.math.BigDecimal;

/**
 * 提交给微信服务的退款参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "提交给微信服务的退款参数")
@XStreamAlias("xml")
public class WeiRefundParams {

    @ApiModelProperty(value = "申请商户号的appid或商户号绑定的appid，必填")
    @XStreamAlias("mch_appid")
    private String mchAppId;

    @ApiModelProperty(value = "微信支付分配的商户号，必填")
    @XStreamAlias("mchid")
    private String mchId;

    @ApiModelProperty(value = "微信支付分配的终端设备号")
    @XStreamAlias("device_info")
    private String deviceInfo;

    @ApiModelProperty(value = "随机字符串，不长于32位，必填")
    @XStreamAlias("nonce_str")
    private String nonceStr;

    @ApiModelProperty(value = "签名，必填")
    @XStreamAlias("sign")
    private String sign;

    @ApiModelProperty(value = "商户订单号，需保持唯一性(只能是字母或者数字，不能包含有其他字符)，必填")
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    @ApiModelProperty(value = "商户appid下，某用户的openid，必填")
    @XStreamAlias("openid")
    private String openid;

    @ApiModelProperty(value = "（校验用户姓名选项：NO_CHECK：不校验真实姓名 ，FORCE_CHECK：强校验真实姓名），必填")
    @XStreamAlias("check_name")
    private String checkName;

    @ApiModelProperty(value = "收款用户真实姓名。 如果check_name设置为FORCE_CHECK，则必填用户真实姓名")
    @XStreamAlias("re_user_name")
    private String reUserName;

    @ApiModelProperty(value = "企业付款金额，单位为分，必填")
    @XStreamAlias("amount")
    private int amount;

    @ApiModelProperty(value = "企业付款备注，必填。注意：备注中的敏感词会被转成字符*")
    @XStreamAlias("desc")
    private String desc;

    @ApiModelProperty(value = "该IP同在商户平台设置的IP白名单中的IP没有关联，该IP可传用户端或者服务端的IP。，必填")
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;






}