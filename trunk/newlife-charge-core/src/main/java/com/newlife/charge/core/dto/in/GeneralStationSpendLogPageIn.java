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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 总后台 桩站资金统计分页查询
 */
@Setter
@Getter
@ApiModel
public class GeneralStationSpendLogPageIn extends Pageable {

    /**
     * 类型(4.卖电账单,41.卖电流水)
     */
    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "类型(4.卖电账单,41.卖电流水)")
    private Integer type;

    /**
     * 桩站账号（手机号）
     */
    @ApiModelProperty(value = "桩站账号（手机号）")
    private String userMobile;

    /**
     * 桩站名称
     */
    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    private String companyName;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间")
    private Date endTime;




}