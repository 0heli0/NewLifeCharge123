/* -------------------------------------------
 * Coupon.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户可领取优惠券查询结果 v1.0
 */


@Getter
@Setter
@ApiModel("用户可领取优惠券查询结果")
public class UserCouponResult {

    //主键-优惠券ID
    @ApiModelProperty(value = "优惠券ID")
    private Integer id;

    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID")
    private Integer stationId;


    //优惠券类型（1：充值通用优惠券 2：用电通用优惠券）
    @ApiModelProperty(value = "优惠券类型（1：充值通用优惠券 2：用电通用优惠券）")
    private Integer type;

    //面额
    @ApiModelProperty(value = "面额")
    private BigDecimal price;

    //门槛金额
    @ApiModelProperty(value = "门槛金额")
    private BigDecimal thresholdPrice;


    //限领张数
    @ApiModelProperty(value = "限领张数")
    private Integer limitNumber;

    @ApiModelProperty(value = "状态(0：已达领取次数，不可领取 1：可以领取)")
    private Integer takeStatus;

    //状态(0：生效中 1：已过期 2：已删除)
    @ApiModelProperty(value = "状态(0：生效中 1：已过期 2：已删除)")
    private Integer status;

    //领取开始时间
    @ApiModelProperty(value = "领取开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date collectStartTime;

    //领取结束时间
    @ApiModelProperty(value = "领取结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date collectEndTime;

    //使用开始时间
    @ApiModelProperty(value = "使用开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useStartTime;

    //使用结束时间
    @ApiModelProperty(value = "使用结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useEndTime;

    @ApiModelProperty(value = "领取开始时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date collectStartTimestamp;

    @ApiModelProperty(value = "领取结束时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date collectEndTimestamp;

    @ApiModelProperty(value = "使用开始时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useStartTimestamp;

    @ApiModelProperty(value = "使用结束时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useEndTimestamp;


    public Date getCollectStartTimestamp() {

        if(collectStartTime != null){
            collectStartTimestamp = collectStartTime;
        }

        return collectStartTimestamp;
    }

    public Date getCollectEndTimestamp() {

        if(collectStartTime != null){
            collectEndTimestamp = collectEndTime;
        }
        return collectEndTimestamp;
    }

    public Date getUseStartTimestamp() {

        if(collectStartTime != null){
            useStartTimestamp = useStartTime;
        }
        return useStartTimestamp;
    }

    public Date getUseEndTimestamp() {

        if(collectStartTime != null){
            useEndTimestamp = useEndTime;
        }
        return useEndTimestamp;
    }
}