package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 新活资金流水记录类型备注
 */
public enum NewLifeSpendLogTypeRemarkEnum {

    RECHARGE(1, "充值"),
    CONSUMPTION(2, "消费"),
    REFUND(3, "退款"),
    SELL_ELECTRIC_INCOME(4, "卖电收入"),
    SERVICE_FEE_INCOME(5, "服务费收入");

    private Integer value;
    private String description;

    NewLifeSpendLogTypeRemarkEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByValue(int value) {
        for (NewLifeSpendLogTypeRemarkEnum auditStatusEnum : NewLifeSpendLogTypeRemarkEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (NewLifeSpendLogTypeRemarkEnum auditStatusEnum : NewLifeSpendLogTypeRemarkEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        NewLifeSpendLogTypeRemarkEnum[] values = NewLifeSpendLogTypeRemarkEnum.values();
        for (NewLifeSpendLogTypeRemarkEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
