/* -------------------------------------------
 * Role.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-01-23 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 新活资金统计-服务费佣金详情
 */
@Getter
@Setter
@ApiModel
public class NewLifeSpendLogCommissionOut {

    //资金流水记录id
    @ApiModelProperty(value = "资金流水记录id")
    private Integer id;

    //资金流水号
    @ApiModelProperty(value = "资金流水号")
    private String moneySn;

    //类型
    @ApiModelProperty(value = "类型")
    private Integer type;

    //类型中文名称
    @ApiModelProperty(value = "类型中文名称")
    private String typeName;

    //类型备注
    @ApiModelProperty(value = "类型备注")
    private Integer typeRemark;

    //类型备注中文名称
    @ApiModelProperty(value = "类型备注中文名称")
    private String typeRemarkName;

    //桩站ID
    @ApiModelProperty(value = "桩站ID")
    private Integer stationId;

    //桩站账号
    @ApiModelProperty(value = "桩站账号")
    private String stationUserMobile;

    //操作金额/消费金额
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    //账单周期
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "GMT+8")
    @ApiModelProperty(value = "账单周期")
    private Date billCycle;

    //桩站历史未结算金额/之前未结算金额
    @ApiModelProperty(value = "之前未结算金额")
    private BigDecimal stationOldNoCheckAmount;

    //桩站未结算金额/未结算金额
    @ApiModelProperty(value = "未结算金额")
    private BigDecimal stationNowNoCheckAmount;

    //创建时间
    @ApiModelProperty(value = "时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    //抽佣比例
    @ApiModelProperty(value = "抽佣比例")
    private BigDecimal commissionRation;

    //桩站可得金额
    @ApiModelProperty(value = "桩站可得金额")
    private BigDecimal stationGainAmount;

    //桩站历史余额
    @ApiModelProperty(value = "桩站历史余额")
    private BigDecimal stationOldBalance;

    //桩站余额
    @ApiModelProperty(value = "桩站余额")
    private BigDecimal stationNowBalance;

    //新活可得金额
    @ApiModelProperty(value = "新活可得金额")
    private BigDecimal newLifeGainAmount;

}

