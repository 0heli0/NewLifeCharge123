/* -------------------------------------------
 * PreCharge.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * 预充值设置保存接收类
 */

@Getter
@Setter
@ApiModel
public class PreChargeSaveIn {

    @ApiModelProperty(value = "面额,必传，1-9999的整数")
    @NotNull(message = "原价不能为空")
    @Min(value = 1, message = "金额不能小于1")
    @Max(value = 9999, message = "金额不能大于9999")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "最终价格,目前设置的值默认与原价相同")
    @Min(value = 1, message = "金额不能小于1")
    @Max(value = 9999, message = "金额不能大于9999")
    private BigDecimal finalprice;

    public BigDecimal getOriginalPrice() {

        // 设置金额只能是整数
        if (originalPrice != null) {
            return originalPrice.setScale(0, BigDecimal.ROUND_DOWN);
        }
        return originalPrice;
    }

}