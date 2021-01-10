/* -------------------------------------------
 * Coupon.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.newlife.charge.core.enums.CouponScopeTypeEnum;
import com.newlife.charge.core.enums.CouponTypeEnum;
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
@ApiModel("用户首页可领取优惠券查询结果")
public class UserCouponPageOut {

    //主键-优惠券ID
    @ApiModelProperty(value = "优惠券ID")
    private Integer id;

    //面额
    @ApiModelProperty(value = "面额")
    private BigDecimal price;

    //门槛金额
    @ApiModelProperty(value = "门槛金额")
    private BigDecimal thresholdPrice;

    @ApiModelProperty(value = "状态(0：生效中 1：已过期 3：已使用)")
    private Integer status;

    @ApiModelProperty(value = "优惠券类型(1.充值通用优惠券,2.用电通用优惠券,3.指定桩站用电优惠券)")
    private Integer type;

    @ApiModelProperty(value = "优惠券范围(0：通用,1.指定桩站使用)")
    private Integer scopeType;

    @ApiModelProperty(value = "优惠券类型名称")
    private String typeName;

    //领取开始时间
    @ApiModelProperty(value = "领取开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date collectStartTime;

    //使用结束时间
    @ApiModelProperty(value = "使用结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date useEndTime;

    @ApiModelProperty(value = "领取开始时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date collectStartTimestamp;

    @ApiModelProperty(value = "使用结束时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useEndTimestamp;

    @ApiModelProperty(value = "使用时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date useTime;

    public Date getCollectStartTimestamp() {
        if(collectStartTime != null){
            collectStartTimestamp = collectStartTime;
        }
        return collectStartTimestamp;
    }

    public Date getUseEndTimestamp() {
        if(useEndTime != null){
            useEndTimestamp = useEndTime;
        }
        return useEndTimestamp;
    }

    public String getTypeName() {
        if(this.type == null){
            return typeName;
        }else if(CouponTypeEnum.Charge_Type.getValue().equals(this.type)) {
            typeName = "通用";

        }else if(CouponTypeEnum.Electro_Type.getValue().equals(this.type)) {
            if(CouponScopeTypeEnum.All_Scope.getValue().equals(this.scopeType)){
                typeName = "通用";
            }else {
                typeName = "部分桩站可用";
            }
        }else if(CouponTypeEnum.Station_Electro_Type.getValue().equals(this.type)) {
            typeName = "部分桩站可用";

        }
        return typeName;
    }
}