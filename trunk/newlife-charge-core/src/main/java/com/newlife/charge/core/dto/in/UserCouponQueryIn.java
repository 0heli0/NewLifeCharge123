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
 * 用户查看可领取优惠券接收类
 */

@Getter
@Setter
@ApiModel(value = "用户查看可领取优惠券接收类")
public class UserCouponQueryIn extends Pageable{

    @ApiModelProperty(value = "是否是天降神券接口,必填,非空(0:用户手动点击按钮时传入, 1:天降神券时传入)")
    @NotNull(message = "接口参数不能为空")
    private Integer autoIndex;


}