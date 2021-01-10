/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 新活资金流水记录详情
 */
@Getter
@Setter
@ApiModel
public class NewLifeSpendLogInfoOut {

    /**
     * 类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,41.卖电流水,5.服务费佣金)
     */
    @ApiModelProperty(value = "类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,41.卖电流水,5.服务费佣金)")
    private Integer type;

    @ApiModelProperty(value = "账户充值详情")
    private NewLifeSpendLogRechargeOut rechargeOut;

    @ApiModelProperty(value = "充电消费详情")
    private NewLifeSpendLogChargeOut chargeOut;

    @ApiModelProperty(value = "余额退款详情")
    private NewLifeSpendLogRefundOut refundOut;

    @ApiModelProperty(value = "卖电账单详情")
    private NewLifeSpendLogBillOut billOut;

    @ApiModelProperty(value = "卖电流水详情")
    private StationChargeOut stationChargeOut;

    @ApiModelProperty(value = "服务费佣金详情")
    private NewLifeSpendLogCommissionOut commissionOut;


}

