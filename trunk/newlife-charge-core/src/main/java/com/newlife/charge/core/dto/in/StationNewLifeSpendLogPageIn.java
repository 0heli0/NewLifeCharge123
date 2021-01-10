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

import java.util.Date;

/**
 * 桩站后台 新活资金统计分页查询
 */
@Getter
@Setter
@ApiModel
public class StationNewLifeSpendLogPageIn extends Pageable {


    //类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,5.服务费佣金)
    @ApiModelProperty(value = "类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,5.服务费佣金)")
    private Integer type;

    //开始时间
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    //结束时间
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    //查询时间
    @ApiModelProperty(value = "查询时间", required = true)
    private String dateTime;

    //显示条数
    @ApiModelProperty(value = "显示条数", required = true)
    private Integer limitNo;

    //桩站id
    @ApiModelProperty(value = "桩站id", required = true)
    private Integer stationId;
}