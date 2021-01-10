/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 站点/桩站信息 设置电费和服务费
 */
@ApiModel
public class StationInfoSetChargeIn {

    //充电单价(元/度)
    @ApiModelProperty(value = "电费")
    @NotNull(message = "请填写电费")
    private BigDecimal chargePrice;

    //服务费(元/度)
    @ApiModelProperty(value = "服务费")
    @NotNull(message = "请填写服务费")
    private BigDecimal servicePrice;

    public BigDecimal getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(BigDecimal chargePrice) {
        this.chargePrice = chargePrice;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
    }
}