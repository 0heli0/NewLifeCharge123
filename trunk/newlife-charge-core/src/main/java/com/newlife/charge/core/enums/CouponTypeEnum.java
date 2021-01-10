package com.newlife.charge.core.enums;

/**
 * 优惠券类型
 *
 */
public enum CouponTypeEnum {
    Charge_Type(1,"充值通用优惠券"),
    Electro_Type(2,"用电通用优惠券"),
    Station_Electro_Type(3,"指定桩站用电优惠券");

    private  Integer value;
    private  String description;

    CouponTypeEnum(Integer value, String description) {
        this.value=value;
        this.description=description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
