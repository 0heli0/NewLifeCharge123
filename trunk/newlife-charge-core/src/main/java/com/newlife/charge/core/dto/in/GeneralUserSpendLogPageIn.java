/* -------------------------------------------
 * User.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-20 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.in;

import com.newlife.charge.core.domain.page.Pageable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 总后台 用户资金统计分页查询
 */
@ApiModel
public class GeneralUserSpendLogPageIn extends Pageable {


    /**
     * 类型(1:账户充值,2:充电消费,3.余额退款)
     *
     * @see com.newlife.charge.core.enums.NewLifeSpendLogTypeEnum
     */
    @ApiModelProperty(value = "类型(1:账户充值,2:充电消费,3.余额退款)")
    private Integer type;


    /**
     * 用户手机号
     */
    @ApiModelProperty(value = "用户手机号")
    private String userMobile;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}