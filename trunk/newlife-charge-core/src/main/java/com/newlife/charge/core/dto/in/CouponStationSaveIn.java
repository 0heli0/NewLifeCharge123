/* -------------------------------------------
 * PreCharge.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.google.common.collect.Lists;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 桩站添加 用电券接收类
 */

@Getter
@Setter
@ApiModel(value = "添加优惠券接收类")
public class CouponStationSaveIn {


    @ApiModelProperty(value = "面额,优惠券面额,必填非空,且只能为整数,若传入小数后台取整数部分")
    @NotNull(message = "面额不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "门槛金额,使用优惠券的门槛金额,必须大于面额,必填非空,且只能为整数,若传入小数后台取整数部分")
    @NotNull(message = "门槛金额不能为空")
    private BigDecimal thresholdPrice;

    @ApiModelProperty(value = "发放总数,必填")
    @NotNull(message = "发放总数不能为空")
    @Min(value = 1,message = "发放总数必须大于0")
    private Integer totalNumber;

    @ApiModelProperty(value = "每个用户限领数量,必填")
    @NotNull(message = "限领数量不能为空")
    @Min(value = 1,message = "限领数量必须大于0")
    private Integer limitNumber;

    @ApiModelProperty(value = "领取开始时间,必填非空")
    @NotNull(message = "领取开始时间不能为空")
    private Date collectStartTime;

    @ApiModelProperty(value = "领取结束时间,必填非空")
    @NotNull(message = "领取结束时间不能为空")
    private Date collectEndTime;


    @ApiModelProperty(value = "使用开始时间,必填非空")
    @NotNull(message = "使用开始时间不能为空")
    private Date useStartTime;

    @ApiModelProperty(value = "使用结束时间,必填非空")
    @NotNull(message = "使用结束时间不能为空")
    private Date useEndTime;



    public BigDecimal getPrice() {

        // 设置金额只能是整数
        if(price != null ){
            return price.setScale(0,BigDecimal.ROUND_DOWN);
        }
        return price;
    }

    public BigDecimal getThresholdPrice() {

        //当门槛金额小于面额时,抛出参数异常
        if(thresholdPrice != null && price != null &&  thresholdPrice.compareTo(price) == -1){
            throw new BizException(ERROR.PARAMATER_ERROR);
        }

        // 设置金额只能是整数
        if(thresholdPrice != null ){
            return thresholdPrice.setScale(0,BigDecimal.ROUND_DOWN);
        }
        return thresholdPrice;
    }

    // 时间格式化,小时及以后默认都为0
    public Date getCollectStartTime() {

        collectStartTime = DateUtils.formatDateToDayStart(collectStartTime);
        return collectStartTime;
    }

    public Date getCollectEndTime() {

        collectEndTime = DateUtils.formatDateToDayEnd(collectEndTime);
        return collectEndTime;
    }

    public Date getUseStartTime() {

        useStartTime = DateUtils.formatDateToDayStart(useStartTime);
        return useStartTime;
    }

    public Date getUseEndTime() {

        useEndTime = DateUtils.formatDateToDayEnd(useEndTime);
        return useEndTime;
    }


}