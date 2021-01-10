package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 桩站车位信息 v1.0
 */
@Getter
@Setter
@ApiModel(value = "车主端-结束充电操作返回结果（充电账单）")
public class ChargeEndOut implements Serializable {

    @ApiModelProperty(value = "充电枪，主键id")
    private Integer clientGunId;

    @ApiModelProperty(value = "充电订单号")
    private String orderSn;

    @ApiModelProperty(value = "充电枪code（充电枪硬件传递的code）")
    private String gunCode;

    @ApiModelProperty(value = "车位code（用于展示）")
    private String lotCode;

    @ApiModelProperty(value = "返回类型,充电状态(1:充电枪连接成功返回,2:实时数据返回,3:充电结束返回)")
    private int status;

    //充电时间(秒)
    @ApiModelProperty(value = "充电时间")
    private String chargeTime;

    //充电电能(KWH)
    @ApiModelProperty(value = "充电电能(KWH)")
    private String chargeEnergy;

    @ApiModelProperty(value = "充电时段价格")
    private String chargeTimePrice;

    //充电金额（元）
    @ApiModelProperty(value = "充电总金额（元）")
    private BigDecimal chargeAmount;

    @ApiModelProperty(value = "优惠券金额（元）")
    private BigDecimal couponPrice;

    @ApiModelProperty(value = "实付金额（元）")
    private BigDecimal actualAmount;

    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    @ApiModelProperty(value = "桩站详细地址")
    private String address;

    @ApiModelProperty(value = "纬度")
    private double lat;

    @ApiModelProperty(value = "经度")
    private double lng;

    @ApiModelProperty(value = "停止充电类型（1:未停止，2：用户手动停止，3：意外停止，4：余额不足停止，5：充电充满后自动停止）")
    private int stopType;

    /*@ApiModelProperty(value = "汽车电量百分比(%),百分号左边的整数")
    private String percentage;*/

}