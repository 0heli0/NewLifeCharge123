package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电桩充电方式
 *
 */
public enum ClientChargeTypeEnum {

    DC_FAST(1, "直流快充"),
    AC_FAST(2, "交流快充"),
    AC_SLOW(3, "交流慢充");

    private Integer value;
    private String description;

    ClientChargeTypeEnum(Integer value, String description) {
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
        for (ClientChargeTypeEnum auditStatusEnum : ClientChargeTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (ClientChargeTypeEnum auditStatusEnum : ClientChargeTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        ClientChargeTypeEnum[] values = ClientChargeTypeEnum.values();
        for (ClientChargeTypeEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
