package com.newlife.charge.core.enums;

/**
 * 用户状态
 * <p>
 */
public enum UserStatusEnum {
    ENABLED(1, "启用"),
    DISABLE(2, "禁用"),
    LOCKED(3, "锁定"),
    EXPIRED(4, "过期");

    private Integer value;
    private String description;

    UserStatusEnum(Integer value, String description) {
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
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getValue().equals(value)) {
                return userStatusEnum.description;
            }
        }
        return null;
    }

    public static Integer getValueByDescription(String description) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getDescription().equals(description)) {
                return userStatusEnum.value;
            }
        }
        return null;
    }

}
