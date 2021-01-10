package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电接口
 *
 */
public enum ClientChargeInterfaceTypeEnum {

    STANDARD_2011(1, "国标2011"),
    STANDARD_2015(2, "国标2015"),
    TESLA(3, "特斯拉");

    private Integer value;
    private String description;

    ClientChargeInterfaceTypeEnum(Integer value, String description) {
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
        for (ClientChargeInterfaceTypeEnum auditStatusEnum : ClientChargeInterfaceTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (ClientChargeInterfaceTypeEnum auditStatusEnum : ClientChargeInterfaceTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        ClientChargeInterfaceTypeEnum[] values = ClientChargeInterfaceTypeEnum.values();
        for (ClientChargeInterfaceTypeEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
