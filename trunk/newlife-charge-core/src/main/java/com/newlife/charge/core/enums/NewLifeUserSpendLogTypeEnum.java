package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * （车主）用户资金流水记录类型
 */
public enum NewLifeUserSpendLogTypeEnum {

    USER_RECHARGE(1, "账户充值"),
    CHARGE_CONSUMPTION(2, "充电消费"),
    BALANCE_REFUND(3, "余额退款");

    private Integer value;
    private String description;

    NewLifeUserSpendLogTypeEnum(Integer value, String description) {
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
        for (NewLifeUserSpendLogTypeEnum auditStatusEnum : NewLifeUserSpendLogTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (NewLifeUserSpendLogTypeEnum auditStatusEnum : NewLifeUserSpendLogTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        NewLifeUserSpendLogTypeEnum[] values = NewLifeUserSpendLogTypeEnum.values();
        for (NewLifeUserSpendLogTypeEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
