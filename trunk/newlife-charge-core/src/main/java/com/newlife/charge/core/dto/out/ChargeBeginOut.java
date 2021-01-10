/* -------------------------------------------
 * ChargeBeginOut.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 桩站车位信息 v1.0
 */
@Getter
@Setter
@ApiModel(value = "车主端-开始充电操作返回结果(实时数据)")
public class ChargeBeginOut implements Serializable {

    @ApiModelProperty(value = "充电枪，主键id")
    private Integer clientGunId;

    @ApiModelProperty(value = "充电订单号")
    private String orderSn;

    @ApiModelProperty(value = "充电枪code（充电枪硬件传递的code）")
    private String gunCode;

    @ApiModelProperty(value = "充电枪状态充电状态(1：离线,2:空闲中(暂定此状态包含完成状态),3:连接中,4:充电中,5:被预约,6:排队中)")
    private int status;

    @ApiModelProperty(value = "车位详细地址")
    private String address;

    @ApiModelProperty(value = "车位编号")
    private String lotCode;

    @ApiModelProperty(value = "汽车电量百分比(%),百分号左边的整数")
    private String percentage;

   /* @ApiModelProperty(value = "充电开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "充电结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;*/

    //充电时间(秒)
    @ApiModelProperty(value = "充电时间")
    private String chargeTime;

    //充电电能(KWH)
    @ApiModelProperty(value = "充电电能(KWH)")
    private String chargeEnergy;

    //充电金额（元）
    @ApiModelProperty(value = "充电金额（元）")
    private String chargeAmount;

}