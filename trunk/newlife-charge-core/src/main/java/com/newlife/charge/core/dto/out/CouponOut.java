/* -------------------------------------------
 * Coupon.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 优惠券输出表 v1.0
 */


@Getter
@Setter
@ApiModel("优惠券查询结果")
public class CouponOut {

    //主键-优惠券ID
    @ApiModelProperty(value = "主键id")
    private Integer id;

    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID")
    private Integer stationId;


    //优惠券类型（1：充值通用优惠券 2：用电通用优惠券）
    @ApiModelProperty(value = "优惠券类型" +
            "（1：充值通用优惠券 2：用电通用优惠券 3：用电通用优惠券）" +
            "ps:只有type为1或者2的时候才会显示下架按钮，其他情况暂不显示")
    private Integer type;

    //面额
    @ApiModelProperty(value = "面额")
    private BigDecimal price;

    //门槛金额
    @ApiModelProperty(value = "门槛金额")
    private BigDecimal thresholdPrice;

    //总数
    @ApiModelProperty(value = "总数")
    private Integer totalNumber;

    //限领
    @ApiModelProperty(value = "限领")
    private Integer limitNumber;

    //剩余数量
    @ApiModelProperty(value = "剩余数量")
    private Integer leftNumber;

    //状态(0：生效中 1：已过期 2：已删除)
    @ApiModelProperty(value = "状态(0：未过期 1：已过期 2：已删除)")
    private Integer status;

    //领取开始时间
    @ApiModelProperty(value = "领取开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date collectStartTime;

    //领取结束时间
    @ApiModelProperty(value = "领取结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date collectEndTime;

    //使用开始时间
    @ApiModelProperty(value = "使用开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useStartTime;

    //使用结束时间
    @ApiModelProperty(value = "使用结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useEndTime;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "绑定桩站名称,只有特定的用电优惠券有,没有的展示为空")
    private String stationName;

    @ApiModelProperty(value = "绑定桩站详情,只有特定的用电优惠券有,没有的展示为空")
    private List<StationDetailOut> stationDetailList = Lists.newArrayList();

    //用户已领取数量
    @ApiModelProperty(value = "用户已领取数量")
    private Integer userGetNumber;

    //已使用数量
    @ApiModelProperty(value = "已使用数量")
    private Integer usedNumber;

    @ApiModelProperty(value = "优惠券是否已下架（0.未下架，1.下架）ps:只有type为1或者2的时候才会显示下架按钮，其他情况暂不显示")
    private Integer isSoldOut;
}