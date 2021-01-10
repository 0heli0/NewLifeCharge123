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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 添加通用充值优惠券接收类
 */

@Getter
@Setter
@ApiModel(value = "添加优惠券接收类")
public class CouponSaveIn {

    @ApiModelProperty(value = "桩站id,总后台与桩站端区分，" +
            "1.总后台创建的优惠券在优惠券表默认为0,有指定桩站时根据关系映射表查询;" +
            "2.桩站创建的优惠券则为该桩站的id" +
            "3.故总后台接口该字段为非必填,且不做验证,该字段默认为0" +
            "4.若该参数不传则认为是通用券,充值优惠券传此参数无效")
    private List<Integer> stationIds = Lists.newArrayList();

    @ApiModelProperty(value = "优惠券类型（1：充值通用优惠券 2：用电通用优惠券 3:指定桩站用电优惠券）,必填,非空")
    @NotNull(message = "优惠券类型不能为空")
    @Min(value = 1, message = "没有此优惠券类型")
    @Max(value = 2, message = "没有此优惠券类型")
    private Integer type;

    @ApiModelProperty(value = "面额,优惠券面额,必填非空,且只能为整数,若传入小数后台取整数部分")
    @NotNull(message = "面额不能为空")
    @Max(value = 9999, message = "面额必须小于10000")
    private BigDecimal price;

    @ApiModelProperty(value = "门槛金额,使用优惠券的门槛金额,必须大于面额,必填非空,且只能为整数,若传入小数后台取整数部分")
    @NotNull(message = "门槛金额不能为空")
    @Max(value = 9999, message = "门槛金额必须小于10000")
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