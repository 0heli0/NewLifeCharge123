package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel("用户充电状态查询")
public class ChargeStatusOut {

    // 充电记录id
    @ApiModelProperty(value = "充电记录id，必填")
    @NotNull(message = "充电记录id,不能为空")
    private Integer id;

    //订单id
    @ApiModelProperty(value = "订单id，必填")
    @NotNull(message = "订单id,不能为空")
    private Integer orderId;

    //订单编号
    @ApiModelProperty(value = "订单编号，必填")
    @NotEmpty(message = "订单编号,不能为空")
    private String orderSn;

    //桩站id
    @ApiModelProperty(value = "桩站id，必填")
    @NotNull(message = "桩站id,不能为空")
    private Integer stationId;

    //充电桩id
    @ApiModelProperty(value = "充电桩id，必填")
    @NotNull(message = "充电桩id,不能为空")
    private Integer clientId;

    //车位id
    @ApiModelProperty(value = "车位id，必填")
    @NotNull(message = "车位id,不能为空")
    private Integer parkingLotId;

    //充电枪id
    @ApiModelProperty(value = "充电枪id，必填")
    @NotNull(message = ",不能为空")
    private Integer clientGunId;

    //充电状态(1:充电中,2:充电成功,3:充电失败)
    @ApiModelProperty(value = "充电状态(1:充电中,2：用户手动停止，3：意外停止，4：余额不足停止，5：充电充满后自动停止)，必填")
    @NotNull(message = "充电状态,不能为空")
    private Integer status;


}
