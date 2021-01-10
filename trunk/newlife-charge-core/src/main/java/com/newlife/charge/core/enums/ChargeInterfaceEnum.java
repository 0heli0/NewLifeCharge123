package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电桩充电方式
 *
 */
public enum ChargeInterfaceEnum {

    GD_2011(1, "国标2011"),
    GD_2015(2, "国标2015"),
    TSL(3, "特斯拉");

    private Integer value;
    private String description;

    ChargeInterfaceEnum(Integer value, String description) {
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
        for (ChargeInterfaceEnum auditStatusEnum : ChargeInterfaceEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (ChargeInterfaceEnum auditStatusEnum : ChargeInterfaceEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        ChargeInterfaceEnum[] values = ChargeInterfaceEnum.values();
        for (ChargeInterfaceEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
