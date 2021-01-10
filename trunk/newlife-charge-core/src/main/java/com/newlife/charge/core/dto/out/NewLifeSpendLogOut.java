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
 * 新活资金流水记录
 */
@Getter
@Setter
@ApiModel
public class NewLifeSpendLogOut {

    //新活资金流水记录id
    @ApiModelProperty(value = "新活资金流水记录id")
    private Integer id;

    //资金流水号
    @ApiModelProperty(value = "资金流水号")
    private String moneySn;

    //订单id
    @ApiModelProperty(value = "订单id")
    private String orderId;

    //订单号
    @ApiModelProperty(value = "订单号")
    private String orderSn;

    //类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,5.服务费佣金)
    @ApiModelProperty(value = "类型(1:账户充值,2:充电消费,3.余额退款,4.卖电账单,5.服务费佣金)")
    private Integer type;

    //类型中文名称
    @ApiModelProperty(value = "类型中文名称")
    private String typeName;

    //类型备注(1:充值,2:消费,3:退款,4:卖电收入,5:服务费收入)
    @ApiModelProperty(value = "类型备注(1:充值,2:消费,3:退款,4:卖电收入,5:服务费收入)")
    private Integer typeRemark;

    //类型备注中文名称
    @ApiModelProperty(value = "类型备注中文名称")
    private String typeRemarkName;

    //操作金额
    @ApiModelProperty(value = "操作金额")
    private BigDecimal amount;

    //用户id
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    //用户类型
    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    //用户姓名
    @ApiModelProperty(value = "用户姓名")
    private String userName;

    //用户手机号码
    @ApiModelProperty(value = "用户手机号码")
    private String userMobile;

    //微信头像
    @ApiModelProperty(value = "微信头像")
    private String avatarUrl;

    //用户历史余额
    @ApiModelProperty(value = "用户历史余额")
    private BigDecimal userOldBalance;

    //用户当前余额
    @ApiModelProperty(value = "用户当前余额")
    private BigDecimal userNowBalance;

    //桩站id
    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    //抽佣比
    @ApiModelProperty(value = "抽佣比")
    private BigDecimal commissionRation;

    //服务费
    @ApiModelProperty(value = "服务费")
    private BigDecimal servicePrice;

    //桩站可得金额
    @ApiModelProperty(value = "桩站可得金额")
    private BigDecimal stationGainAmount;

    //桩站历史未结算金额
    @ApiModelProperty(value = "桩站历史未结算金额")
    private BigDecimal stationOldNoCheckAmount;

    //桩站未结算金额
    @ApiModelProperty(value = "桩站未结算金额")
    private BigDecimal stationNowNoCheckAmount;

    //桩站历史余额
    @ApiModelProperty(value = "桩站历史余额")
    private BigDecimal stationOldBalance;

    //桩站余额
    @ApiModelProperty(value = "桩站余额")
    private BigDecimal stationNowBalance;

    //新活可得金额
    @ApiModelProperty(value = "新活可得金额")
    private BigDecimal newLifeGainAmount;

    //备注
    @ApiModelProperty
    private String remark;

    //创建时间
    @ApiModelProperty(value = "创建时间")

    //时间格式化，时区使用东八区
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}