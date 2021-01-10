package com.newlife.charge.core.enums;

/**
 * 用户类型
 * <p>
 */
public enum UserTypeEnum {
    CAR(1, "车主用户"),
    STATION_MAIN(2, "桩站系统主账号"),
    STATION_SUB(3, "桩站系统子账号"),
    ADMIN(4, "总后台系统用户");

    private Integer value;
    private String description;

    UserTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByValue(Integer value) {
        for (UserTypeEnum usedStatusEnum : UserTypeEnum.values()) {
            if (usedStatusEnum.getValue().equals(value)) {
                return usedStatusEnum.description;
            }
        }
        return null;
    }

    public static Integer getValueByDescription(String description) {
        for (UserTypeEnum usedStatusEnum : UserTypeEnum.values()) {
            if (usedStatusEnum.getDescription().equals(description)) {
                return usedStatusEnum.value;
            }
        }
        return null;
    }

}
