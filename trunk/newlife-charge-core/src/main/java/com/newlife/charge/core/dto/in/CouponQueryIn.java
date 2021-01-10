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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * 分页查询优惠券接收类
 */

@Getter
@Setter
@ApiModel(value = "分页查询优惠券接收类")
public class CouponQueryIn extends Pageable{

    @ApiModelProperty(value = "添加人,必填,指定值为0-2的整数,非空: 0:所有人员,1:总后台管理员,2:桩站管理员")
    @NotNull(message = "添加人类型不能为空")
    @Min(value = 0, message = "添加人类型错误")
    @Max(value = 2, message = "添加人类型错误")
    private Integer addUser;

    @ApiModelProperty(value = "优惠券类型,必填,指定值为0-2的整数,非空（0:所有,1：充值通用优惠券 2：用电通用优惠券）")
    @NotNull(message = "优惠券类型不能为空")
    @Min(value = 0, message = "优惠券类型错误")
    @Max(value = 2, message = "优惠券类型错误")
    private Integer type;

    @ApiModelProperty(value = "优惠券适用范围类型,指定值为0-1的整数,为空时默认为全部(0: 全部-通用优惠券, 1: 指定桩站适用-用电优惠券)")
    @Min(value = 0, message = "优惠券适用范围类型错误")
    @Max(value = 1, message = "优惠券适用范围类型错误")
    private Integer scopeType;

    @ApiModelProperty(value = "优惠券状态,指定值为0-2的整数,必填(0:所有,1:未过期,2:已过期)")
    @NotNull(message = "优惠券状态不能为空")
    @Min(value = 0, message = "优惠券状态错误")
    @Max(value = 2, message = "优惠券状态错误")
    private Integer status;

    @ApiModelProperty(value = "桩站名,模糊查询")
    private String stationName;

    @ApiModelProperty(value = "面额,优惠券面额,必填非空,且只能为整数,若传入小数后台取整数部分")
    private BigDecimal price;

    @ApiModelProperty(value = "门槛金额,使用优惠券的门槛金额,必须大于面额,必填非空,且只能为整数,若传入小数后台取整数部分")
    private BigDecimal thresholdPrice;

}