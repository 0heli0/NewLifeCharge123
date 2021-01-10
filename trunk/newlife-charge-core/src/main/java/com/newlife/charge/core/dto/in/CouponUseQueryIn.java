/* -------------------------------------------
 * PreCharge.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 添加通用充值优惠券接收类
 */

@Getter
@Setter
@ApiModel
public class CouponUseQueryIn extends Pageable {

    @ApiModelProperty(value = "手机号,手机号格式")
    private String mobile;

    @ApiModelProperty(value = "优惠券状态(空：所有,0:未使用,1:已使用,2:冻结,3:已过期)")
    @Max(value = 3, message = "优惠券状态错误")
    private Integer status;

    @ApiModelProperty(value = "优惠券id，必传")
    @NotNull(message = "优惠券id不能为空")
    private Integer couponId;

}