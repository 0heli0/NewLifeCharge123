/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.enums.NewLifeUserSpendLogTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 总后台 用户资金统计-详情
 */
@ApiModel
public class GeneralUserSpendLogInfoIn extends InfoIn {


    /**
     * 类型(1:账户充值,2:充电消费,3.余额退款)
     *
     * @see NewLifeUserSpendLogTypeEnum
     */
    @ApiModelProperty(value = "类型(1:账户充值,2:充电消费,3.余额退款)")
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}