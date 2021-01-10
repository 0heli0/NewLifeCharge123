package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 桩站资金流水记录类型备注
 */
public enum NewLifeStationSpendLogTypeRemarkEnum {
    SELL_ELECTRIC_INCOME(4, "卖电收入");

    private Integer value;
    private String description;

    NewLifeStationSpendLogTypeRemarkEnum(Integer value, String description) {
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
        for (NewLifeStationSpendLogTypeRemarkEnum auditStatusEnum : NewLifeStationSpendLogTypeRemarkEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (NewLifeStationSpendLogTypeRemarkEnum auditStatusEnum : NewLifeStationSpendLogTypeRemarkEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        NewLifeStationSpendLogTypeRemarkEnum[] values = NewLifeStationSpendLogTypeRemarkEnum.values();
        for (NewLifeStationSpendLogTypeRemarkEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }

}
