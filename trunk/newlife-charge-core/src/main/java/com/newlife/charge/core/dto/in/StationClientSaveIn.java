/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 充电桩 新增
 */
@Setter
@Getter
@ApiModel
public class StationClientSaveIn {


    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID，必传")
    @NotNull(message = "请选择所属桩站")
    private Integer stationId;

    //品牌
    @ApiModelProperty(value = "品牌，必传,最长50位")
    @NotBlank(message = "请填写品牌")
    @Length(max = 50, message = "品牌长度最长50位")
    private String brand;

    //单双枪类型(1:单枪，2:双枪)
    @ApiModelProperty(value = "单双枪类型(1:单枪，2:双枪)，必传")
    @NotNull(message = "请选择单双枪类型")
    private Integer gunType;

    //充电方式(1:直流快充,2:交流快充,3:交流慢充)
    @ApiModelProperty(value = "充电方式(1:直流快充,2:交流快充,3:交流慢充)，必传")
    @NotNull(message = "请选择充电方式")
    private Integer chargeType;

    //充电接口(1:国标2011,2:国标2015,3:特斯拉)
    @ApiModelProperty(value = "充电接口(1:国标2011,2:国标2015,3:特斯拉)，必传")
    @NotNull(message = "请选择充电接口")
    private Integer chargeInterfaceType;

    //最低功率(kW)
    @ApiModelProperty(value = "最低功率(kW)，必传，整形数")
    @NotNull(message = "请填写最低功率(kW)")
    private Integer powerMin;

    //最高功率(kW)
    @ApiModelProperty(value = "最高功率(kW)，必传，整形数")
    @NotNull(message = "请填写最高功率(kW)")
    private Integer powerMax;

    //最低电压(V)
    @ApiModelProperty(value = "最低电压(V)，必传，整形数")
    @NotNull(message = "请填写最低电压(V)")
    private Integer voltageMin;

    //最高电压(V)
    @ApiModelProperty(value = "最高电压(V)，必传，整形数")
    @NotNull(message = "请填写最高电压(V)")
    private Integer voltageMax;

    //辅源类型
    @ApiModelProperty(value = "辅源类型，必传,最长50位")
    @NotBlank(message = "请填写辅源类型")
    @Length(max = 50, message = "辅源类型长度最长50位")
    private String auxiliaryType;

}