/* -------------------------------------------
 * CouponStationRef.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-26 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * 优惠券适用桩站输出结果 v1.0
 */

@Getter
@Setter
@ApiModel("优惠券适用桩站查询结果")
public class CouponStationRefOut {

    //主键-优惠券适用桩站ID
    @ApiModelProperty(value = "主键id")
    private Integer id;

    //优惠券ID
    @ApiModelProperty(value = "优惠券ID")
    private Integer couponId;

    //适用桩站ID
    @ApiModelProperty(value = "适用桩站ID")
    private Integer stationId;

    @ApiModelProperty(value = "桩站名")
    private String name;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    public Date getCreateTimestamp() {
        if(createTime != null){
            this.createTimestamp = this.createTime;
        }
        return createTimestamp;
    }

}