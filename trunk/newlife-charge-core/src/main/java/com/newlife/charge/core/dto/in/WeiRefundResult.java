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

/**
 * 提交给微信服务的退款参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "提交给微信服务的退款参数")
@XStreamAlias("xml")
public class WeiRefundResult {

    @ApiModelProperty(value = "申请商户号的appid或商户号绑定的appid，必填")
    @XStreamAlias("mch_appid")
    private String mchAppid;

    @ApiModelProperty(value = "微信支付分配的商户号，必填")
    @XStreamAlias("mchid")
    private String mchId;

    @ApiModelProperty(value = "微信支付分配的终端设备号")
    @XStreamAlias("device_info")
    private String deviceInfo;

    @ApiModelProperty(value = "随机字符串，不长于32位，必填")
    @XStreamAlias("nonce_str")
    private String nonceStr;

    @ApiModelProperty(value = "SUCCESS/FAIL，注意：当状态为FAIL时，存在业务结果未明确的情况。如果如果状态为FAIL，请务必关注错误代码（err_code字段），通过查询查询接口确认此次付款的结果。")
    @XStreamAlias("result_code")
    private String resultCode;

    @ApiModelProperty(value = "错误码信息，注意：出现未明确的错误码时（SYSTEMERROR等），请务必用原商户订单号重试，或通过查询接口确认此次付款的结果。")
    @XStreamAlias("err_code")
    private String errCode;

    @ApiModelProperty(value = "结果信息描述")
    @XStreamAlias("err_code_des")
    private String errCodeDes;

    @ApiModelProperty(value = "商户订单号，需保持唯一性(只能是字母或者数字，不能包含有其他字符)，必填")
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    @ApiModelProperty(value = "企业付款成功，返回的微信付款单号")
    @XStreamAlias("payment_no")
    private String paymentNo;

    @ApiModelProperty(value = "企业付款成功时间")
    @XStreamAlias("payment_time")
    private String paymentTime;

    @ApiModelProperty(value = "SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断")
    @XStreamAlias("return_code")
    private String returnCode;

    @ApiModelProperty(value = "返回信息，如非空，为错误原因 ")
    @XStreamAlias("return_msg")
    private String returnMsg;

    @Override
    public String toString() {
        return "WeiRefundResult{" +
                "mchAppId='" + mchAppid + '\'' +
                ", mchId='" + mchId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", errCode='" + errCode + '\'' +
                ", errCodeDes='" + errCodeDes + '\'' +
                ", partnerTradeNo='" + partnerTradeNo + '\'' +
                ", paymentNo='" + paymentNo + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", returnCode='" + returnCode + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                '}';
    }
}