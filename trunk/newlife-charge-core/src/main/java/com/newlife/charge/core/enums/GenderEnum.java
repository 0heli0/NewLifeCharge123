package com.newlife.charge.core.enums;

/**
 * 单双枪类型
 *
 */
public enum GenderEnum {

    MAN(0, "男"),
    WOMAN(1, "女"),
    DEFAULT(3, "保密");

    private Integer value;
    private String description;

    GenderEnum(Integer value, String description) {
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
        for (GenderEnum auditStatusEnum : GenderEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (GenderEnum auditStatusEnum : GenderEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
