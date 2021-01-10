/* -------------------------------------------
 * ChargeLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 车主用户充电记录表 v1.0
 */


public class ChargeLogOut {

    //主键-用户充电记录id
    @ApiModelProperty(value = "用户充电记录id")
    private Integer id;

    //订单id
    @ApiModelProperty(value = "订单id")
    private Integer orderId;

    //车主用户ID
    @ApiModelProperty(value = "车主用户ID")
    private Integer userId;

    //订单编号
    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    //桩站id
    @ApiModelProperty(value = "桩站id")
    private Integer stationId;

    //桩站名称
    @ApiModelProperty(value = "桩站名称")
    private String stationName;

    //充电桩id
    @ApiModelProperty(value = "充电桩id")
    private Integer clientId;

    //充电桩编号
    @ApiModelProperty(value = "充电桩编号")
    private String clientCode;

    //充电桩类型(1:直流快充,2:交流快充,3:交流慢充)
    @ApiModelProperty(value = "充电桩类型(1:直流快充,2:交流快充,3:交流慢充)")
    private Integer clientChargeType;

    //充电桩类型名称(1:直流快充,2:交流快充,3:交流慢充)
    @ApiModelProperty(value = "充电桩类型名称(1:直流快充,2:交流快充,3:交流慢充)")
    private String clientChargeTypeName;

    //车位id
    @ApiModelProperty(value = "车位id")
    private Integer parkingLotId;

    //车位编号
    @ApiModelProperty(value = "车位编号")
    private String parkingLotCode;

    //充电枪id
    @ApiModelProperty(value = "充电枪id")
    private Integer clientGunId;

    //充电枪编号
    @ApiModelProperty(value = "充电枪编号")
    private String clientGunCode;

    //充电开始时间
    @ApiModelProperty(value = "充电开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;

    //充电结束时间
    @ApiModelProperty(value = "充电结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    //预估充电度数
    @ApiModelProperty(value = "预估充电度数")
    private BigDecimal degreePredict;

    //实际充电度数
    @ApiModelProperty(value = "实际充电度数")
    private BigDecimal degreeReal;

    //用户使用优惠券id
    @ApiModelProperty(value = "用户使用优惠券id")
    private Integer userCouponId;

    //优惠券减免的金额
    @ApiModelProperty(value = "优惠券减免的金额")
    private BigDecimal priceCoupon;

    //充电单价-基础电价
    @ApiModelProperty(value = "基础电价")
    private BigDecimal chargePrice;

    //电价-充电过程中涉及到的电价
    @ApiModelProperty(value = "充电过程中涉及到的电价")
    private String chargePrices;

    //需要支付的金额
    @ApiModelProperty(value = "需要支付的金额")
    private BigDecimal priceReal;

    //车型
    @ApiModelProperty(value = "车型")
    private String vehicleType;

    //车牌号
    @ApiModelProperty(value = "车牌号")
    private String plateNumber;

    //充电状态(1:充电中,2:充电成功,3:充电失败)
    @ApiModelProperty(value = "充电状态(1:充电中,2:充电成功,3:充电失败)")
    private Integer status;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    //充电时长
    @ApiModelProperty(value = "充电时长")
    private long chargeTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn == null ? null : orderSn.trim();
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getClientChargeType() {
        return clientChargeType;
    }

    public void setClientChargeType(Integer clientChargeType) {
        this.clientChargeType = clientChargeType;
    }

    public Integer getParkingLotId() {
        return parkingLotId;
    }

    public void setParkingLotId(Integer parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public String getParkingLotCode() {
        return parkingLotCode;
    }

    public void setParkingLotCode(String parkingLotCode) {
        this.parkingLotCode = parkingLotCode == null ? null : parkingLotCode.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDegreePredict() {
        return degreePredict;
    }

    public void setDegreePredict(BigDecimal degreePredict) {
        this.degreePredict = degreePredict;
    }

    public BigDecimal getDegreeReal() {
        return degreeReal;
    }

    public void setDegreeReal(BigDecimal degreeReal) {
        this.degreeReal = degreeReal;
    }

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }

    public BigDecimal getPriceCoupon() {
        return priceCoupon;
    }

    public void setPriceCoupon(BigDecimal priceCoupon) {
        this.priceCoupon = priceCoupon;
    }

    public BigDecimal getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(BigDecimal chargePrice) {
        this.chargePrice = chargePrice;
    }

    public BigDecimal getPriceReal() {
        return priceReal;
    }

    public void setPriceReal(BigDecimal priceReal) {
        this.priceReal = priceReal;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType == null ? null : vehicleType.trim();
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber == null ? null : plateNumber.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Integer getClientGunId() {
        return clientGunId;
    }

    public void setClientGunId(Integer clientGunId) {
        this.clientGunId = clientGunId;
    }

    public String getClientGunCode() {
        return clientGunCode;
    }

    public void setClientGunCode(String clientGunCode) {
        this.clientGunCode = clientGunCode;
    }

    public Integer getUserId() {
        return userId;
    }


    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getChargePrices() {
        return chargePrices;
    }

    public void setChargePrices(String chargePrices) {
        this.chargePrices = chargePrices;
    }

    public long getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(long chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getClientChargeTypeName() {
        return clientChargeTypeName;
    }

    public void setClientChargeTypeName(String clientChargeTypeName) {
        this.clientChargeTypeName = clientChargeTypeName;
    }
}