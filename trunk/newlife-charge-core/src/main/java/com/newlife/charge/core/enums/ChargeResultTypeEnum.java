package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电返回客户端类型
 */
public enum ChargeResultTypeEnum {

    CONNECT_SUCCESS(1, "充电枪连接成功返回"),
    REAL_TIME_DATA(2, "实时数据返回"),
    END_RESULT(3, "充电结束返回");

    private Integer value;
    private String description;

    ChargeResultTypeEnum(Integer value, String description) {
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
        for (ChargeResultTypeEnum auditStatusEnum : ChargeResultTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (ChargeResultTypeEnum auditStatusEnum : ChargeResultTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        ChargeResultTypeEnum[] values = ChargeResultTypeEnum.values();
        for (ChargeResultTypeEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
