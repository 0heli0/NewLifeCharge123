/* -------------------------------------------
 * WeiChatCheckOrderResult.java
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
public class WeiChatCheckOrderResult {

    // 返回状态码 SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看trade_state来判断
    @XStreamAlias("return_code")
    @ApiModelProperty(value = "返回状态码")
    private String returnCode;

    // 返回信息 返回信息，如非空，为错误原因
    @XStreamAlias("return_msg")
    @ApiModelProperty(value = "返回信息 返回信息，如非空，为错误原因")
    private String returnMsg;

    // "小程序ID
    @XStreamAlias("appid")
    @ApiModelProperty(value = "小程序ID")
    private String appid;

    // "商户号)
    @XStreamAlias("mch_id")
    @ApiModelProperty(value = "商户号")
    private String mchId;

    // 随机字符串
    @XStreamAlias("nonce_str")
    @ApiModelProperty(value = "随机字符串")
    private String nonceStr;

    // 签名
    @XStreamAlias("sign")
    @ApiModelProperty(value = "签名")
    private String sign;

    // 业务结果 SUCCESS/FAIL
    @XStreamAlias("result_code")
    @ApiModelProperty(value = "业务结果 SUCCESS/FAIL")
    private String resultCode;

    // 错误代码
    @XStreamAlias("err_code")
    @ApiModelProperty(value = "错误代码")
    private String errCode;

    // 错误代码描述
    @XStreamAlias("err_code_des")
    @ApiModelProperty(value = "错误代码描述")
    private String errCodeDes;

    // 设备号
    @XStreamAlias("device_info")
    @ApiModelProperty(value = "设备号")
    private String deviceInfo;

    // 用户标识
    @XStreamAlias("openid")
    @ApiModelProperty(value = "用户标识")
    private String openid;

    // 是否关注公众账号 用户是否关注公众账号，Y-关注，N-未关注
    @XStreamAlias("is_subscribe")
    @ApiModelProperty(value = "是否关注公众账号 用户是否关注公众账号，Y-关注，N-未关注")
    private String isSubscribe;

    // 交易类型
    @XStreamAlias("trade_type")
    @ApiModelProperty(value = "交易类型")
    private String tradeType;

    /*
        交易状态
         SUCCESS—支付成功
         REFUND—转入退款
         NOTPAY—未支付
         CLOSED—已关闭
         REVOKED—已撤销（刷卡支付）
         USERPAYING--用户支付中
         PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    @XStreamAlias("trade_state")
    @ApiModelProperty(value = "交易状态")
    private String tradeState;

    // 付款银行
    @XStreamAlias("bank_type")
    @ApiModelProperty(value = "付款银行")
    private String bankType;

    // 标价金额
    @XStreamAlias("total_fee")
    @ApiModelProperty(value = "标价金额")
    private String totalFee;

    // 应结订单金额
    @XStreamAlias("settlement_total_fee")
    @ApiModelProperty(value = "应结订单金额")
    private String settlementTotalFee;

    // 标价币种
    @XStreamAlias("fee_type")
    @ApiModelProperty(value = "标价币种")
    private String feeType;

    // 现金支付金额
    @XStreamAlias("cash_fee")
    @ApiModelProperty(value = "现金支付金额")
    private String cashFee;

    // 现金支付币种
    @XStreamAlias("cash_fee_type")
    @ApiModelProperty(value = "现金支付币种")
    private String cashFeeType;

    // 代金券金额
    @XStreamAlias("coupon_fee")
    @ApiModelProperty(value = "代金券金额")
    private String couponFee;

    // 代金券使用数量
    @XStreamAlias("coupon_count")
    @ApiModelProperty(value = "代金券使用数量")
    private String couponCount;

    // 代金券类型
    @XStreamAlias("coupon_type_$n")
    @ApiModelProperty(value = "代金券类型")
    private String couponType$n;

    // 代金券类型
    @XStreamAlias("coupon_type")
    @ApiModelProperty(value = "代金券类型")
    private String couponType;

    // 代金券ID
    @XStreamAlias("coupon_id_$n")
    @ApiModelProperty(value = "代金券ID")
    private String couponId$n;

    // 代金券ID
    @XStreamAlias("coupon_id")
    @ApiModelProperty(value = "代金券ID")
    private String couponId;

    // 单个代金券支付金额
    @XStreamAlias("coupon_fee_$n")
    @ApiModelProperty(value = "单个代金券支付金额")
    private String couponFee$n;

    // 微信支付订单号
    @XStreamAlias("transaction_id")
    @ApiModelProperty(value = "微信支付订单号")
    private String transactionId;

    // 商户订单号
    @XStreamAlias("out_trade_no")
    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

    // 附加数据
    @XStreamAlias("attach")
    @ApiModelProperty(value = "附加数据")
    private String attach;

    // 支付完成时间
    @XStreamAlias("time_end")
    @ApiModelProperty(value = "支付完成时间")
    private String timeEnd;

    // 交易状态描述
    @XStreamAlias("trade_state_desc")
    @ApiModelProperty(value = "交易状态描述")
    private String tradeStateDesc;
}