/* -------------------------------------------
 * PreCharge.java
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
 * 预充值套餐表 v1.0
 */

@Getter
@Setter
@ApiModel
public class PreChargeOut {

    //主键-预充值套餐id
    @ApiModelProperty(value = "主键-预充值套餐id")
    private Integer id;

    //原价
    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;

    //最终价格
    @ApiModelProperty(value = "最终价格")
    private BigDecimal finalPrice;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建时间时间戳")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createTimestamp;

    public Date getCreateTimestamp() {
        if(createTime != null){
            createTimestamp = createTime;
        }
        return createTimestamp;
    }
}