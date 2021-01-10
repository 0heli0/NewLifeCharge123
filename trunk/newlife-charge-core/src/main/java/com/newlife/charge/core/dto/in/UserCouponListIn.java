/* -------------------------------------------
 * UserCouponQueryIn.java
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

import javax.validation.constraints.NotNull;


/**
 * 用户查看领取的优惠券接收类
 */

@Getter
@Setter
@ApiModel(value = "用户查看领取的优惠券接收类")
public class UserCouponListIn extends Pageable{

    @ApiModelProperty(value = "优惠券状态,必填(0：未使用 1：已使用 3:已过期)")
    @NotNull(message = "优惠券状态不能为空")
    private Integer status;

}