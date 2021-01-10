/* -------------------------------------------
 * UserRechargeAccountOut.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


/**
 * 用户余额信息 v1.0
 */
@Getter
@Setter
@ApiModel(value = "车主端-用户余额信息")
public class UserRechargeAccountOut {

    // 当前余额
    private BigDecimal price;

    // 用户账户余额记录
    private List<UserRechargeLogOut> list = Lists.newArrayList();


    public  UserRechargeAccountOut(){

    }

    public  UserRechargeAccountOut(BigDecimal price, List<UserRechargeLogOut> list){
        this.price = price;
        this.list = list;
    }

}