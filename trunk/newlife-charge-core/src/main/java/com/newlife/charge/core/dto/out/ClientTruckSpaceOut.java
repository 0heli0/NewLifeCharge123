/* -------------------------------------------
 * ClientTruckSpaceOut.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.newlife.charge.core.enums.ClientChargeInterfaceTypeEnum;
import com.newlife.charge.core.enums.ClientChargeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;


/**
 * 桩站车位信息 v1.0
 */
@Getter
@Setter
@ApiModel(value = "车主端-桩站车位信息")
public class ClientTruckSpaceOut {

    @ApiModelProperty(value = "车位主键id")
    private Integer id;

    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    @ApiModelProperty(value = "充电枪id")
    private Integer gunId;

    @ApiModelProperty(value = "充电枪编号")
    private String gunCode;

    @ApiModelProperty(value = "停车位编号")
    private String code;

    @ApiModelProperty(value = "充电方式(1:直流快充,2:交流快充,3:交流慢充)")
    private Integer chargeType;

    @ApiModelProperty(value = "充电方式")
    private String chargeTypeStr;

    @ApiModelProperty(value = "充电接口(1:国标2011,2:国标2015,3:特斯拉)")
    private Integer chargeInterfaceType;

    @ApiModelProperty(value = "充电接口")
    private String chargeInterfaceTypeStr;

    @ApiModelProperty(value = "辅源类型")
    private String auxiliaryType;

    @ApiModelProperty(value = "最低功率(kW)")
    private Integer powerMin;

    @ApiModelProperty(value = "最高功率(kW)")
    private Integer powerMax;

    @ApiModelProperty(value = "最低电压(V)")
    private Integer voltageMin;

    @ApiModelProperty(value = "最高电压(V)")
    private Integer voltageMax;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "用户余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "充电枪状态(0：不可用,1:可用)")
    private int status;

    @ApiModelProperty(value = "车位是否被绑定(0：没有被绑定,1:已经绑定, 2:正在使用)")
    // 车位状态(0:空闲中, 1:被预约, 2:正在使用)
    private int lotStatus;

    @ApiModelProperty(value = "时段电价标识（0:全天统一电价，1:存在时段电价）")
    private int priceStatus;

    public String getChargeTypeStr() {
        if(ClientChargeTypeEnum.DC_FAST.getValue() == chargeType){

            chargeTypeStr = ClientChargeTypeEnum.DC_FAST.getDescription();

        }else if(ClientChargeTypeEnum.AC_FAST.getValue() == chargeType){

            chargeTypeStr = ClientChargeTypeEnum.AC_FAST.getDescription();

        }else if(ClientChargeTypeEnum.AC_SLOW.getValue() == chargeType){

            chargeTypeStr = ClientChargeTypeEnum.AC_SLOW.getDescription();

        }
        return chargeTypeStr;
    }

    public String getChargeInterfaceTypeStr() {

        if(ClientChargeInterfaceTypeEnum.STANDARD_2011.getValue() == chargeInterfaceType){

            chargeInterfaceTypeStr = ClientChargeInterfaceTypeEnum.STANDARD_2011.getDescription();

        }else if(ClientChargeInterfaceTypeEnum.STANDARD_2015.getValue() == chargeInterfaceType){

            chargeInterfaceTypeStr = ClientChargeInterfaceTypeEnum.STANDARD_2015.getDescription();

        }else if(ClientChargeInterfaceTypeEnum.TESLA.getValue() == chargeInterfaceType){

            chargeInterfaceTypeStr = ClientChargeInterfaceTypeEnum.TESLA.getDescription();

        }
        return chargeInterfaceTypeStr;
    }
}