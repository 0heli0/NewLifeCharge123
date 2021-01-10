package com.newlife.charge.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 时段
 */
public enum TimeNoEnum {

    ONE(1, "第一时段"),
    TWO(2, "第二时段"),
    THREE(3, "第三时段"),
    FOUR(4, "第四时段"),
    FIVE(5, "第五时段"),
    SEX(6, "第六时段"),
    SEVEN(7, "第七时段"),
    EIGHT(8, "第八时段"),
    NINE(9, "第九时段"),
    TEN(10, "第十时段"),
    ELEVEN(11, "第十一时段"),
    TWELVE(12, "第十二时段"),
    THIRTEEN(13, "第十三时段"),
    FOURTEEN(14, "第十四时段"),
    FIFTEEN(15, "第十五时段");

    private Integer value;
    private String description;

    TimeNoEnum(Integer value, String description) {
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
        for (TimeNoEnum auditStatusEnum : TimeNoEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (TimeNoEnum auditStatusEnum : TimeNoEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        TimeNoEnum[] values = TimeNoEnum.values();
        for (TimeNoEnum typeEnum : values) {
            map.put(typeEnum.getValue(), typeEnum.getDescription());
        }
        return map;
    }


}
