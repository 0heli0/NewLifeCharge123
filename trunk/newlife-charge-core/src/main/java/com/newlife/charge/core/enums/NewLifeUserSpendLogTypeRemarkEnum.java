package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * （车主）用户资金流水记录类型备注
 */
public enum NewLifeUserSpendLogTypeRemarkEnum {

    RECHARGE(1, "充值"),
    CONSUMPTION(2, "消费"),
    REFUND(3, "退款");

    private Integer value;
    private String description;

    NewLifeUserSpendLogTypeRemarkEnum(Integer value, String description) {
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
        for (NewLifeUserSpendLogTypeRemarkEnum auditStatusEnum : NewLifeUserSpendLogTypeRemarkEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (NewLifeUserSpendLogTypeRemarkEnum auditStatusEnum : NewLifeUserSpendLogTypeRemarkEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        NewLifeUserSpendLogTypeRemarkEnum[] values = NewLifeUserSpendLogTypeRemarkEnum.values();
        for (NewLifeUserSpendLogTypeRemarkEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
