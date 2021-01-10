package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电状态(1:充电中,2:充电成功,3:充电失败)
 */
public enum ChargeLogStatusEnum {

    CHARGE_ING(1, "充电中"),
    CHARGE_SUCCESS(2, "充电成功"),
    CHARGE_FAIL(3, "充电失败");

    private Integer value;
    private String description;

    ChargeLogStatusEnum(Integer value, String description) {
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
        for (ChargeLogStatusEnum auditStatusEnum : ChargeLogStatusEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (ChargeLogStatusEnum auditStatusEnum : ChargeLogStatusEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        ChargeLogStatusEnum[] values = ChargeLogStatusEnum.values();
        for (ChargeLogStatusEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
