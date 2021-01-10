package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 新活资金流水记录类型
 */
public enum NewLifeSpendLogTypeEnum {

    USER_RECHARGE(1, "账户充值"),
    CHARGE_CONSUMPTION(2, "充电消费"),
    BALANCE_REFUND(3, "余额退款"),
    SELL_ELECTRIC_BILL(4, "卖电账单"),
    SERVICE_FEE_COMMISSION(5, "服务费佣金");

    private Integer value;
    private String description;

    NewLifeSpendLogTypeEnum(Integer value, String description) {
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
        for (NewLifeSpendLogTypeEnum auditStatusEnum : NewLifeSpendLogTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (NewLifeSpendLogTypeEnum auditStatusEnum : NewLifeSpendLogTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        NewLifeSpendLogTypeEnum[] values = NewLifeSpendLogTypeEnum.values();
        for (NewLifeSpendLogTypeEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
