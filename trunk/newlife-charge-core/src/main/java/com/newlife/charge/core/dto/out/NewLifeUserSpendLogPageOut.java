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

import java.math.BigDecimal;
import java.util.Date;


/**
 * (车主用户)资金流水记录分页信息
 */
@ApiModel
public class NewLifeUserSpendLogPageOut {

    //资金流水记录id
    @ApiModelProperty(value = "资金流水记录id")
    private Integer id;

    //资金流水号
    @ApiModelProperty(value = "资金流水号")
    private String moneySn;


    //类型(1:账户充值,2:充电消费,3.余额退款)
    @ApiModelProperty(value = "类型(1:账户充值,2:充电消费,3.余额退款)")
    private Integer type;

    //类型中文名称
    @ApiModelProperty(value = "类型中文名称")
    private String typeName;

    //类型备注(1:充值,2:消费,3:退款)
    @ApiModelProperty(value = "类型备注(1:充值,2:消费,3:退款)")
    private Integer typeRemark;

    //类型备注中文名称
    @ApiModelProperty(value = "类型备注中文名称")
    private String typeRemarkName;

    //操作金额/实际金额
    @ApiModelProperty(value = "实际金额")
    private BigDecimal amount;

    //用户手机号码
    @ApiModelProperty(value = "用户手机号码")
    private String userMobile;

    //创建时间
    @ApiModelProperty(value = "时间")
    //时间格式化，时区使用东八区
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //时间格式化，时区使用东八区
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd HH:mm:ss", timezone = "GMT+8")
    private Date showTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoneySn() {
        return moneySn;
    }

    public void setMoneySn(String moneySn) {
        this.moneySn = moneySn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeRemark() {
        return typeRemark;
    }

    public void setTypeRemark(Integer typeRemark) {
        this.typeRemark = typeRemark;
    }

    public String getTypeRemarkName() {
        return typeRemarkName;
    }

    public void setTypeRemarkName(String typeRemarkName) {
        this.typeRemarkName = typeRemarkName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }
}