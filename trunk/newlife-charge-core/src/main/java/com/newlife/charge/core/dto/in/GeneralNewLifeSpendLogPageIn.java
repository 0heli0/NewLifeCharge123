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
import lombok.Getter;
import lombok.Setter;

/**
 * 总后台 新活资金统计分页查询
 */
@Getter
@Setter
@ApiModel
public class GeneralNewLifeSpendLogPageIn extends Pageable {


    //用户手机号
    @ApiModelProperty(value = "用户手机号")
    private String userMobile;

    //类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,5.服务费佣金)
    @ApiModelProperty(value = "类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,5.服务费佣金)")
    private Integer type;
}