/* -------------------------------------------
 * ChargeLog.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


package com.newlife.charge.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;



/**
 * 车主用户充电记录表 v1.0
 */

@Getter
@Setter
public class ChargeLog {

    //主键-用户充电记录id
    private Integer id;

    //订单id
    private Integer orderId;

    //车主用户ID
    private Integer userId;

    //订单编号
    private String orderSn;

    //桩站id
    private Integer stationId;

    //桩站名称
    private String stationName;

    //充电桩id
    private Integer clientId;
    //充电桩编号
    private String clientCode;

    //充电桩类型(1:直流快充,2:交流快充,3:交流慢充)
    private Integer clientChargeType;

    //车位id
    private Integer parkingLotId;

    //车位编号
    private String parkingLotCode;

    //充电枪id
    private Integer clientGunId;
    //充电枪编号
    private String clientGunCode;

    //充电开始时间
    private Date beginTime;

    //充电结束时间
    private Date endTime;

    //预估充电度数
    private BigDecimal degreePredict;

    //实际充电度数
    private BigDecimal degreeReal;

    //用户使用优惠券id
    private Integer userCouponId;

    //优惠券减免的金额
    private BigDecimal priceCoupon;

    //充电单价-基础电价
    private BigDecimal chargePrice;

    // 服务费
    private BigDecimal servicePrice;

    //电价-充电过程中涉及到的电价
    private String chargePrices;

    //需要支付的金额
    private BigDecimal priceReal;

    //车型
    private String vehicleType;

    //车牌号
    private String plateNumber;

    //充电状态(1:充电中,2:充电成功,3:充电失败)
    private Integer status;

    // 结束类型(1:充电中,2：用户手动停止，3：意外停止，4：余额不足停止，5：充电充满后自动停止)
    private Integer stopType;

    //创建时间
    private Date createTime;

}