/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 领取优惠券接收类
 */
@Getter
@Setter
@ApiModel(value = "领取优惠券传参")
public class TakeCouponIn {

    //主键ID
    @ApiModelProperty(value = "主键ID")
    @NotNull(message = "主键ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "限领张数")
    private Integer limitNumber;

}