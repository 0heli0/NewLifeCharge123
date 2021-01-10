package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 新活资金流水记录类型首字母
 */
public enum NewLifeSpendLogTypeStrEnum {

    USER_RECHARGE(1, "ZHCZ"),
    CHARGE_CONSUMPTION(2, "CDXF"),
    BALANCE_REFUND(3, "YETK"),
    SELL_ELECTRIC_BILL(4, "MDZD"),
    SERVICE_FEE_COMMISSION(5, "FWYJ");

    private Integer value;
    private String description;

    NewLifeSpendLogTypeStrEnum(Integer value, String description) {
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
        for (NewLifeSpendLogTypeStrEnum auditStatusEnum : NewLifeSpendLogTypeStrEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (NewLifeSpendLogTypeStrEnum auditStatusEnum : NewLifeSpendLogTypeStrEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }

    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        NewLifeSpendLogTypeStrEnum[] values = NewLifeSpendLogTypeStrEnum.values();
        for (NewLifeSpendLogTypeStrEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
