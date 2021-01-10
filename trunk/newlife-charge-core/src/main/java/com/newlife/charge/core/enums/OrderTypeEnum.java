package com.newlife.charge.core.enums;

/**
 * 充值类型
 *
 */
public enum OrderTypeEnum {
    RECHARGE(1,"用户充值"),
    REFUND(2,"用户退款"),
    CHARGE(3,"用户充电"),
    STATION_ACCOUNT(4,"桩站结算");

    private  Integer value;
    private  String description;

    OrderTypeEnum(Integer value, String description) {
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
