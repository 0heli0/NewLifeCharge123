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

import java.util.Date;


/**
 * 总后台 桩站注册用户账号信息
 */
@Setter
@Getter
@ApiModel
public class GeneralCompanyUserPageOut {

    //用户ID
    @ApiModelProperty(value = "账号Id")
    private Integer id;

    //账号（手机号）
    @ApiModelProperty(value = "账号（手机号）")
    private String mobile;


    //公司ID
    @ApiModelProperty(value = "公司ID")
    private Integer companyId;

    //公司资质审核状态
    @ApiModelProperty(value = "审核状态-编码")
    private Integer auditStatus;

    //公司资质审核状态
    @ApiModelProperty(value = "审核状态-中文")
    private String auditStatusName;

    //微信openId
    @ApiModelProperty(value = "微信Id")
    private String openId;

    //微信头像/用户头像
    @ApiModelProperty(value = "微信头像")
    private String userAvatarUrl;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    //管理员姓名
    @ApiModelProperty(value = "管理员姓名")
    private String managerName;

    //抽佣比例(例如：20%)
    @ApiModelProperty(value = "抽佣比例")
    private String commissionRation;

    //注册时间--桩站账号注册时间
    @ApiModelProperty(value = "注册时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}