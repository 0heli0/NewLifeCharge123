/* -------------------------------------------
 * DebitCard.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-24 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * RPC充电桩状态更新
 */
@Setter
@Getter
public class RpcStationClientStatusUpdateIn {

    //充电桩终端号/充电桩编号
    private List<String> stationClientCodeList;

    //充电枪状态(1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
    private Integer status;

}