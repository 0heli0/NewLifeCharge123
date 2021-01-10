package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 微信支付下单接收参数 v1.0
 */

@Getter
@Setter
@ApiModel(value = "微信退款下单接收参数")
public class WRefindCreateOrderIn {

    @ApiModelProperty(value = "退款金额")
    @NotNull(message = "退款金额,不能为空")
    @Min(value = 1,message = "退款金额不能小于1块钱")
    private BigDecimal price;

    @ApiModelProperty(value = "支付方式（1：微信 2：支付宝）,必填")
    @NotNull(message = "支付方式,不能为空")
    private Integer payType;

}