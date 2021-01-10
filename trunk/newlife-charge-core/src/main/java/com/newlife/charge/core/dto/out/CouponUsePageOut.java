/* -------------------------------------------
 * CouponUsePageOut.java
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

import java.util.Date;


/**
 * 优惠券详情中,优惠券使用情况集合 v1.0
 */


@Getter
@Setter
@ApiModel("优惠券详情中,优惠券使用情况集合")
public class CouponUsePageOut {

    //主键-优惠券ID
    @ApiModelProperty(value = "优惠券ID")
    private Integer id;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "对应使用订单")
    private String orderSn;

    @ApiModelProperty(value = "状态(0：未使用 1：已使用 2：冻结  3:已过期)")
    private Integer status;

    //领取开始时间
    @ApiModelProperty(value = "领取时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "领取时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    public Date getCreateTimestamp() {
        if(createTime != null){
            this.createTimestamp = this.createTime;
        }
        return createTimestamp;
    }

}