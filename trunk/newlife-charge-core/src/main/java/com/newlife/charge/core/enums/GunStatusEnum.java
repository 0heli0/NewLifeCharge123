package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电枪状态
 * (1：离线,2:空闲中,3:连接中,4:充电中,5:被预约,6:排队中)
 */
public enum GunStatusEnum {

    OFFLINE(1, "离线"),
    FREE(2, "空闲中"),
    LINKING(3, "连接中"),
    CHARGING(4, "充电中"),
    BOOKED(5, "被预约"),
    LINE(6, "排队中");

    private Integer value;
    private String description;

    GunStatusEnum(Integer value, String description) {
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
        for (GunStatusEnum auditStatusEnum : GunStatusEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (GunStatusEnum auditStatusEnum : GunStatusEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        GunStatusEnum[] values = GunStatusEnum.values();
        for (GunStatusEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
