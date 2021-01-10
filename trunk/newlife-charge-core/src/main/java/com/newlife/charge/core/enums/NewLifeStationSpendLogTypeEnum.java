package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 桩站资金流水记录类型-新活资金类型中的一个
 */
public enum NewLifeStationSpendLogTypeEnum {

    SELL_ELECTRIC_BILL(4, "卖电账单");

    private Integer value;
    private String description;

    NewLifeStationSpendLogTypeEnum(Integer value, String description) {
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
        for (NewLifeStationSpendLogTypeEnum auditStatusEnum : NewLifeStationSpendLogTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (NewLifeStationSpendLogTypeEnum auditStatusEnum : NewLifeStationSpendLogTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        NewLifeStationSpendLogTypeEnum[] values = NewLifeStationSpendLogTypeEnum.values();
        for (NewLifeStationSpendLogTypeEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
