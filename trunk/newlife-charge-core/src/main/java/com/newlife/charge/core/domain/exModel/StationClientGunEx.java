/* -------------------------------------------
 * StationDetail.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import com.newlife.charge.core.domain.StationClientGun;


/**
 * 桩站充电枪信息详情
 */

public class StationClientGunEx extends StationClientGun {

    //桩站名称
    private String stationName;

    //绑定车位编号
    private String stationParkingLotCode;

    //充电桩编号
    private String stationClientCode;

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationParkingLotCode() {
        return stationParkingLotCode;
    }

    public void setStationParkingLotCode(String stationParkingLotCode) {
        this.stationParkingLotCode = stationParkingLotCode;
    }

    public String getStationClientCode() {
        return stationClientCode;
    }

    public void setStationClientCode(String stationClientCode) {
        this.stationClientCode = stationClientCode;
    }
}