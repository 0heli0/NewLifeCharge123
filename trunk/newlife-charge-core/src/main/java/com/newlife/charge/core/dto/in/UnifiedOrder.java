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

/**
 * 微信支付统一下单返回结果 v1.0
 */

@Getter
@Setter
@ApiModel(value = "微信支付统一下单返回结果")
@XStreamAlias("xml")
public class UnifiedOrder {

    @ApiModelProperty(value = "返回状态码,SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断,必传")
    @XStreamAlias("return_code")
    private String returnCode;

    @ApiModelProperty(value = "返回信息，如非空，为错误原因签名失败参数格式校验错误")
    @XStreamAlias("return_msg")
    private String returnMsg;

    @ApiModelProperty(value = "小程序ID,必传")
    @XStreamAlias("appid")
    private String appId;

    @ApiModelProperty(value = "商户号,必传")
    @XStreamAlias("mch_id")
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

    @ApiModelProperty(value = "用户标识")
    @XStreamAlias("openid")
    private String openid;

    @ApiModelProperty(value = "业务结果，SUCCESS/FAIL")
    @XStreamAlias("result_code")
    private String resultCode;

    @ApiModelProperty(value = "错误代码")
    @XStreamAlias("err_code")
    private String errCode;

    @ApiModelProperty(value = "错误代码描述")
    @XStreamAlias("err_code_des")
    private String errCodeDes;

    @ApiModelProperty(value = "交易类型取值为：JSAPI，NATIVE，APP等")
    @XStreamAlias("trade_type")
    private String tradeType;

    @ApiModelProperty(value = "预支付交易会话标识，用于后续接口调用中使用，该值有效期为2小时")
    @XStreamAlias("prepay_id")
    private String prepayId;

    @ApiModelProperty(value = "二维码链接，trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付。")
    @XStreamAlias("code_url")
    private String codeUrl;

    @Override
    public String toString() {
        return "UnifiedOrderResult{" +
                "returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", appId='" + appId + '\'' +
                ", mchId='" + mchId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", openid='" + openid + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errCodeDes='" + errCodeDes + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", prepayId='" + prepayId + '\'' +
                ", codeUrl='" + codeUrl + '\'' +
                '}';
    }
}