/* -------------------------------------------
 * UnifiedOrderParams.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

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
public class UnifiedOrderResult {



    @ApiModelProperty(value = "小程序ID")
    private String appId;

    @ApiModelProperty(value = "随机字符串，长度要求在32位以内")
    private String nonceStr;

    @ApiModelProperty(value = "签名")
    private String sign;

    @ApiModelProperty(value = "预支付交易会话标识，用于后续接口调用中使用，该值有效期为2小时,java不能直接返回package字段，故以此字段拼接后返回")
    private String prepayId;

    @ApiModelProperty(value = "订单，用于传回后台，后台进行手动查询使用")
    private String orderSn;

    @ApiModelProperty(value = "时间戳")
    private String timestamp;

    @ApiModelProperty(value = "签名")
    private String signType;

    @Override
    public String toString() {
        return "UnifiedOrderResult{" +
                "appId='" + appId + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                ", prepayId='" + prepayId + '\'' +
                ", orderSn='" + orderSn + '\'' +
                ", timestamp=" + timestamp +
                ", signType='" + signType + '\'' +
                '}';
    }
}