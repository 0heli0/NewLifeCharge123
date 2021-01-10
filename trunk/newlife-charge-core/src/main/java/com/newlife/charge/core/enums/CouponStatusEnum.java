package com.newlife.charge.core.enums;

/**
 * 优惠券状态
 *
 */
public enum CouponStatusEnum {
    USED(0,"使用中"),
    OVERDUE(1,"已过期"),
    DELETE(2,"已删除");
    private  Integer value;
    private  String description;

    CouponStatusEnum(Integer value, String description) {
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
