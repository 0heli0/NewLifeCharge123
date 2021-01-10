

package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 绑定枪得桩站地址详情
 */
@Setter
@Getter
@ApiModel(value = "绑定枪得桩站地址详情")
public class StationGunAddressDetail {

    @ApiModelProperty(value = "充电站id")
    private Integer stationId;

    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    @ApiModelProperty(value = "充电枪id")
    private Integer gunId;

    @ApiModelProperty(value = "充电枪编号")
    private String  gunCode;

    @ApiModelProperty(value = "充电桩Id")
    private Integer clientId;

    @ApiModelProperty(value = "充电桩编号")
    private String  clientCode;

    @ApiModelProperty(value = "电站详细地址")
    private String address;

    @ApiModelProperty(value = "车位编号")
    private String lotCode;

    @ApiModelProperty(value = "车位id")
    private Integer lotId;

    @ApiModelProperty(value = "纬度")
    private double lat;

    @ApiModelProperty(value = "经度")
    private double lng;

    @Override
    public String toString() {
        return "StationGunAddressDetail{" +
                "stationId=" + stationId +
                ", stationName='" + stationName + '\'' +
                ", gunId=" + gunId +
                ", gunCode='" + gunCode + '\'' +
                ", clientId=" + clientId +
                ", clientCode='" + clientCode + '\'' +
                ", address='" + address + '\'' +
                ", lotCode='" + lotCode + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}