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
 * 总后台首页待审核公司信息
 */
@Setter
@Getter
@ApiModel
public class GeneralIndexNoAuditCompanyOut {

    //用户ID
    @ApiModelProperty(value = "账号Id")
    private Integer id;

    //账号（手机号）
    @ApiModelProperty(value = "账号（手机号）")
    private String mobile;

    //公司名称
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    //认证提交时间--桩站公司信息创建时间
    @ApiModelProperty(value = "认证提交时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy年MM月dd日HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}