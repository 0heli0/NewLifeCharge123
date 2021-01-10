/* -------------------------------------------
 * StationGunStatistics.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-29 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import lombok.Getter;
import lombok.Setter;


/**
 * 充电枪统计表 v1.0
 */

@Getter
@Setter
public class StationGunStatistics {

    // 指定桩站充电枪总数
    private Integer total;

    // 充电方式(1:直流快充,2:交流快充,3:交流慢充)
    private Integer chargeType;

}