/* -------------------------------------------
 * StationDetail.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import com.newlife.charge.core.domain.ChargeLog;
import com.newlife.charge.core.domain.NewLifeSpendLog;


/**
 * 车主用户充电记录
 */
public class ChargeLogEx extends ChargeLog {

    //公司名称
    private String companyName;

    //车位编号
    private String code;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}