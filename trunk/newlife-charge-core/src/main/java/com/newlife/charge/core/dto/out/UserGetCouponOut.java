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
 * 用户领取的优惠券查询结果 v1.0
 */


@Getter
@Setter
@ApiModel("用户领取的优惠券查询结果")
public class UserGetCouponOut {

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


    //领取张数
    @ApiModelProperty(value = "领取张数")
    private Integer couponNumber;

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

    //创建时间
    @ApiModelProperty(value = "领取时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "绑定桩站名称,只有特定的用电优惠券有,没有的展示为空")
    private String stationName;

    //领取开始时间
    @ApiModelProperty(value = "领取开始时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date collectStartTimestamp;

    //领取结束时间
    @ApiModelProperty(value = "领取结束时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date collectEndTimestamp;

    //使用开始时间
    @ApiModelProperty(value = "使用开始时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useStartTimestamp;

    //使用结束时间
    @ApiModelProperty(value = "使用结束时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useEndTimestamp;

    //创建时间
    @ApiModelProperty(value = "领取时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    public Date getCollectStartTimestamp() {
        if(collectStartTime != null){
            collectStartTimestamp = collectStartTime;
        }
        return collectStartTimestamp;
    }

    public Date getCollectEndTimestamp() {
        if(collectEndTime != null){
            collectEndTimestamp = collectEndTime;
        }
        return collectEndTimestamp;
    }

    public Date getUseStartTimestamp() {
        if(useStartTime != null){
            useStartTimestamp = useStartTime;
        }
        return useStartTimestamp;
    }

    public Date getUseEndTimestamp() {
        if(useEndTime != null){
            useEndTimestamp = useEndTime;
        }
        return useEndTimestamp;
    }

    public Date getCreateTimestamp() {
        if(createTime != null){
            createTimestamp = createTime;
        }
        return createTimestamp;
    }
}