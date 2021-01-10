package com.newlife.charge.core.enums;

/**
 * 桩站类型
 *
 */
public enum StationTypeEnum {

    COMMON(0, "公用"),
    SPECIAL(1, "专用"),
    PERSONAL(2, "私人");

    private Integer value;
    private String description;

    StationTypeEnum(Integer value, String description) {
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
        for (StationTypeEnum auditStatusEnum : StationTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (StationTypeEnum auditStatusEnum : StationTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
