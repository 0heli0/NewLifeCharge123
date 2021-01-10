/* -------------------------------------------
 * UserCouponDetail.java
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
 * 用户领取的优惠券详情
 */
@Getter
@Setter
@ApiModel("用户领取的优惠券详情")
public class UserCouponDetail {

    //主键-优惠券ID
    @ApiModelProperty(value = "用户领取的优惠券ID")
    private Integer id;

    @ApiModelProperty(value = "优惠券id")
    private Integer couponId;

    @ApiModelProperty(value = "桩站id（0为平台发放，其他为桩站发放）")
    private Integer stationId;

    @ApiModelProperty(value = "使用订单号")
    private String orderSn;

    @ApiModelProperty(value = "优惠券类型（1：充值通用优惠券 2：用电通用优惠券,3:桩站用电优惠券）")
    private Integer type;

    @ApiModelProperty(value = "优惠券类型")
    private String typeName;

    //面额
    @ApiModelProperty(value = "面额")
    private BigDecimal price;

    //门槛金额
    @ApiModelProperty(value = "门槛金额")
    private BigDecimal thresholdPrice;

    @ApiModelProperty(value = "状态（0：未使用 1：已使用 2:冻结 3:已过期）")
    private Integer status;

    //使用开始时间
    @ApiModelProperty(value = "订单创建时间")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date frozenTime;

    //使用开始时间
    @ApiModelProperty(value = "使用开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useStartTime;

    //使用结束时间
    @ApiModelProperty(value = "使用结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useEndTime;

    @ApiModelProperty(value = "领取时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "绑定桩站名称,只有特定的用电优惠券有,没有的展示为空")
    private String stationName;

    @ApiModelProperty(value = "桩站纬度")
    private BigDecimal lat;

    @ApiModelProperty(value = "桩站经度")
    private BigDecimal lng;

    @ApiModelProperty(value = "桩站详细地址")
    private String address;


    //使用开始时间
    @ApiModelProperty(value = "使用开始时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useStartTimestamp;

    //使用结束时间
    @ApiModelProperty(value = "使用结束时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useEndTimestamp;

    @ApiModelProperty(value = "领取时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    @ApiModelProperty(value = "使用时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useTime;

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
// 1：充值通用优惠券 2：用电通用优惠券,3:桩站用电优惠券,4.桩站专用优惠券
    public String getTypeName() {
        if(type != null){
            switch (type){
                case 1:
                    typeName = "充值通用优惠券";
                    break;
                case 2:
                    typeName = "用电通用优惠券";
                    break;
                case 3:
                    typeName = "桩站专用优惠券";
                    break;
                case 4:
                    typeName = "桩站专用优惠券";
                    break;
            }
        }
        return typeName;
    }
}