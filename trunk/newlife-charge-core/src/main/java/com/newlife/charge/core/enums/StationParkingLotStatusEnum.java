package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 车位状态
 * (0:空闲中, 1:被预约, 2:正在使用)
 */
public enum StationParkingLotStatusEnum {

    FREE(0, "空闲中"),
    BOOKED(1, "被预约"),
    USED(2, "正在使用");

    private Integer value;
    private String description;

    StationParkingLotStatusEnum(Integer value, String description) {
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
        for (StationParkingLotStatusEnum auditStatusEnum : StationParkingLotStatusEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (StationParkingLotStatusEnum auditStatusEnum : StationParkingLotStatusEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        StationParkingLotStatusEnum[] values = StationParkingLotStatusEnum.values();
        for (StationParkingLotStatusEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
