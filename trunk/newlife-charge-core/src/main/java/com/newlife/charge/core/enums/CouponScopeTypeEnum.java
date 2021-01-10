package com.newlife.charge.core.enums;

/**
 * 优惠券适用范围类型
 *
 */
public enum CouponScopeTypeEnum {
    All_Scope(0,"通用优惠券,全部适用"),
    Appoint_Type(1,"用电优惠券,指定桩站适用");

    private  Integer value;
    private  String description;

    CouponScopeTypeEnum(Integer value, String description) {
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
