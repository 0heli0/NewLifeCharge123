package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电枪编号
 *
 */
public enum StationClientGunCodeEnum {

    ONE(1, "1"),
    TWO(2, "2");

    private Integer value;
    private String description;

    StationClientGunCodeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByValue(int value) {
        for (StationClientGunCodeEnum auditStatusEnum : StationClientGunCodeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static Integer getValueByDescription(String description) {
        for (StationClientGunCodeEnum auditStatusEnum : StationClientGunCodeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        StationClientGunCodeEnum[] values = StationClientGunCodeEnum.values();
        for (StationClientGunCodeEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
