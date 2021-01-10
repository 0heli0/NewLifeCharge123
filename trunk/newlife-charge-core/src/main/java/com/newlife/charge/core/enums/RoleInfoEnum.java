package com.newlife.charge.core.enums;

/**
 *  总后台和桩站管理员 角色 绑定id
 *
 */
public enum RoleInfoEnum {

    GENERAL_ADMIN(1, "总后台管理员"),
    STATION_ADMIN(2, "桩站后台管理员");

    private Integer value;
    private String description;

    RoleInfoEnum(Integer value, String description) {
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
        for (RoleInfoEnum auditStatusEnum : RoleInfoEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (RoleInfoEnum auditStatusEnum : RoleInfoEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
