/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 充电桩 详情
 */
@Setter
@Getter
@ApiModel
public class StationClientInfoOut {

    //充电桩主键ID
    @ApiModelProperty(value = "充电桩主键ID")
    private Integer id;

    //所属桩站ID
    @ApiModelProperty(value = "所属桩站ID")
    private Integer stationId;

    //所属桩站名称
    @ApiModelProperty(value = "所属桩站名称")
    private String stationName;

    //充电桩编号
    @ApiModelProperty(value = "充电桩编号")
    private String code;

    //品牌
    @ApiModelProperty(value = "品牌")
    private String brand;

    //单双枪类型(1:单枪，2:双枪)
    @ApiModelProperty(value = "单双枪类型(1:单枪，2:双枪)")
    private Integer gunType;

    //单双枪类型名称
    @ApiModelProperty(value = "单双枪类型名称")
    private String gunTypeName;


    //充电方式(1:直流快充,2:交流快充,3:交流慢充)
    @ApiModelProperty(value = "充电方式(1:直流快充,2:交流快充,3:交流慢充)")
    private Integer chargeType;

    //充电方式名称
    @ApiModelProperty(value = "充电方式名称")
    private String chargeTypeName;

    //充电接口(1:国标2011,2:国标2015,3:特斯拉)
    @ApiModelProperty(value = "充电接口(1:国标2011,2:国标2015,3:特斯拉)")
    private Integer chargeInterfaceType;

    //充电接口名称
    @ApiModelProperty(value = "充电接口名称")
    private String chargeInterfaceTypeName;

    //最低功率(kW)
    @ApiModelProperty(value = "最低功率(kW)")
    private Integer powerMin;

    //最高功率(kW)
    @ApiModelProperty(value = "最高功率(kW)")
    private Integer powerMax;

    //最低电压(V)
    @ApiModelProperty(value = "最低电压(V)")
    private Integer voltageMin;

    //最高电压(V)
    @ApiModelProperty(value = "最高电压(V)")
    private Integer voltageMax;

    //辅源类型
    @ApiModelProperty(value = "辅源类型")
    private String auxiliaryType;

    //绑定车位编号(多个用逗号隔开)
    @ApiModelProperty(value = "绑定车位编号(多个用逗号隔开)")
    private String stationParkingLotCodes;

    //绑定充电枪编号(多个用逗号隔开)
    @ApiModelProperty(value = "绑定充电枪编号(多个用逗号隔开)")
    private String stationClientGunCodes;

}