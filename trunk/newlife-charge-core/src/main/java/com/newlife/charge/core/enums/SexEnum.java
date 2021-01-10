package com.newlife.charge.core.enums;

/**
 * 性别
 *
 */
public enum SexEnum {

    BOY(0, "男"),
    GIRL(1, "女"),
    ALL(2, "全部");

    private Integer value;
    private String description;

    SexEnum(Integer value, String description) {
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
        for (SexEnum auditStatusEnum : SexEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (SexEnum auditStatusEnum : SexEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
