/* -------------------------------------------
 * WeiRefundParams.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 提交给微信服务的退款参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "提交给微信服务的退款参数")
public class WeixinRefundLog {

    //主键-微信支付记录ID
    private Integer id;

    //开发者ID
    private String appid;

    //申请商户号的appid或商户号绑定的appid
    private String mchAppid;

    //商户号ID
    private String mchId;

    //设备号
    private String deviceInfo;

    //随机字符串
    private String nonceStr;

    //签名
    private String sign;

    //签名类型
    private String signType;

    //业务结果
    private String resultCode;

    //错误代码
    private String errCode;

    //错误代码描述
    private String errCodeDes;

    //微信用户标识openid
    private String openid;

    //企业付款成功，返回的微信付款单号
    private String paymentNo;

    //商户订单号(本系统内订单号)
    private String partnerTradeNo;

    //商家数据包
    private String attach;

    //企业付款成功时间
    private String paymentTime;

    //返回状态码
    private String returnCode;

    //返回信息
    private String returnMsg;

    //创建时间
    private Date createTime;

    @Override
    public String toString() {
        return "WeixinRefundLog{" +
                "id=" + id +
                ", appid='" + appid + '\'' +
                ", mchAppid='" + mchAppid + '\'' +
                ", mchId='" + mchId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", signType='" + signType + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errCodeDes='" + errCodeDes + '\'' +
                ", openid='" + openid + '\'' +
                ", paymentNo='" + paymentNo + '\'' +
                ", partnerTradeNo='" + partnerTradeNo + '\'' +
                ", attach='" + attach + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}