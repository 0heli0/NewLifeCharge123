/* -------------------------------------------
 * TruckSpaceEx.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * 用户端桩站车位信息扩展类 v1.0
 */
@Getter
@Setter
public class TruckSpaceEx {

    // 车位id
    private Integer id;

    // 停车位编号
    private String code;

    // 桩站id
    private Integer stationId;

    // 充电枪id
    private Integer gunId;

    // 充电枪编号
    private String gunCode;

    // 充电桩编号
    private String headCode;

    // 充电方式(1:直流快充,2:交流快充,3:交流慢充)
    private Integer chargeType;

    // 充电接口(1:国标2011,2:国标2015,3:特斯拉)
    private Integer chargeInterfaceType;

    // 最低功率(kW)
    private Integer powerMin;

    // 最高功率(kW)
    private Integer powerMax;

    // "最低电压(V)")
    private Integer voltageMin;

    // "最高电压(V)")
    private Integer voltageMax;

    // 电价
    private BigDecimal chargePrice;

    // 服务费
    private BigDecimal servicePrice;

    // 计算后价格
    private BigDecimal price;

    //(涨幅)%,+涨 -降
    private Integer increase;

    // 辅源类型
    private String auxiliaryType;

    // 充电枪状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    private int status;

    // 车位状态(0:空闲中, 1:被预约, 2:正在使用)
    private int lotStatus;


}