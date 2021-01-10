package com.newlife.charge.core.enums;

/**
 * 单双枪类型
 *
 */
public enum GunTypeEnum {

    ONE(1, "单枪"),
    TWO(2, "双枪");

    private Integer value;
    private String description;

    GunTypeEnum(Integer value, String description) {
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
        for (GunTypeEnum auditStatusEnum : GunTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (GunTypeEnum auditStatusEnum : GunTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
