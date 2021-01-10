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
 * 预充值套餐表 v1.0
 */

@Getter
@Setter
@ApiModel(value = "预充值设置更新接收类")
public class PreChargeUpdateIn {

    @ApiModelProperty(value = "id,不能为空,不能小于0")
    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Integer id;

    @ApiModelProperty(value = "原价,不能为空,不能小于0,必须是整数,若有小数则取整数部分")
    @NotNull(message = "原价不能为空")
    @Min(value = 1, message = "金额不能小于1")
    @Max(value = 10000, message = "金额不能大于10000")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "最终价格,目前设置的值默认与原价相同")
    @Min(value = 1, message = "金额不能小于1")
    @Max(value = 10000, message = "金额不能大于10000")
    private BigDecimal finalprice;

    public BigDecimal getOriginalPrice() {
        if(originalPrice != null ){
            return originalPrice.setScale(0,BigDecimal.ROUND_DOWN);
        }
        return originalPrice;
    }

}