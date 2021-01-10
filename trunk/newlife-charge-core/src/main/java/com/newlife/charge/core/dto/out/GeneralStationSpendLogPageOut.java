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
 * 桩站资金流水记录分页信息
 */
@Setter
@Getter
@ApiModel
public class GeneralStationSpendLogPageOut {

    //资金流水记录id
    @ApiModelProperty(value = "资金流水记录id")
    private Integer id;

    //资金流水号
    @ApiModelProperty(value = "资金流水号")
    private String moneySn;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    //账号手机号码
    @ApiModelProperty(value = "账号(手机号)")
    private String userMobile;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    //类型(4.卖电账单,41.卖电流水)
    @ApiModelProperty(value = "类型(4.卖电账单,41.卖电流水)")
    private Integer type;

    //类型中文名称
    @ApiModelProperty(value = "类型中文名称")
    private String typeName;

    //类型备注(4:卖电收入)
    @ApiModelProperty(value = "类型备注(4:卖电收入,41.桩站订单)")
    private Integer typeRemark;

    //类型备注中文名称
    @ApiModelProperty(value = "类型备注中文名称")
    private String typeRemarkName;

    //操作金额/实际金额
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    //创建时间
    @ApiModelProperty(value = "时间")
    //时间格式化，时区使用东八区
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}