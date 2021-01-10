/* -------------------------------------------
 * StationDetail.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain.exModel;

import com.newlife.charge.core.domain.NewLifeSpendLog;
import com.newlife.charge.core.domain.StationInfo;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 桩站信息详情
 */

public class NewLifeSpendLogEx extends NewLifeSpendLog {

    //公司名称
    private String companyName;

    private Date showTime;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }
}