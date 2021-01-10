/* -------------------------------------------
 * StationDetail.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import com.newlife.charge.core.domain.StationClient;
import com.newlife.charge.core.domain.StationInfo;

import java.math.BigDecimal;


/**
 * 桩站充电桩信息详情
 */

public class StationClientEx extends StationClient {

    //绑定车位编号（多个用,隔开）
    private String stationParkingLotCodes;

    //桩站名称
    private String stationName;

    //单双枪类型名称
    private String gunTypeName;

    //充电方式名称
    private String chargeTypeName;

    //充电接口名称
    private String chargeInterfaceTypeName;

    //绑定充电枪编号（多个用,隔开）
    private String stationClientGunCodes;

    public String getStationParkingLotCodes() {
        return stationParkingLotCodes;
    }

    public void setStationParkingLotCodes(String stationParkingLotCodes) {
        this.stationParkingLotCodes = stationParkingLotCodes;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getGunTypeName() {
        return gunTypeName;
    }

    public void setGunTypeName(String gunTypeName) {
        this.gunTypeName = gunTypeName;
    }

    public String getChargeTypeName() {
        return chargeTypeName;
    }

    public void setChargeTypeName(String chargeTypeName) {
        this.chargeTypeName = chargeTypeName;
    }

    public String getChargeInterfaceTypeName() {
        return chargeInterfaceTypeName;
    }

    public void setChargeInterfaceTypeName(String chargeInterfaceTypeName) {
        this.chargeInterfaceTypeName = chargeInterfaceTypeName;
    }

    public String getStationClientGunCodes() {
        return stationClientGunCodes;
    }

    public void setStationClientGunCodes(String stationClientGunCodes) {
        this.stationClientGunCodes = stationClientGunCodes;
    }
}