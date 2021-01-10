package com.newlife.charge.core.enums;

/**
 * 用户领取的优惠券状态
 * <p>
 */
public enum UserCouponStatusEnum {
    UNUSED(0, "未使用"),
    USED(1, "已使用"),
    LOCKED(2, "冻结"),
    OVERDUE(3, "过期");

    private Integer value;
    private String description;

    UserCouponStatusEnum(Integer value, String description) {
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
        for (UserCouponStatusEnum userStatusEnum : UserCouponStatusEnum.values()) {
            if (userStatusEnum.getValue().equals(value)) {
                return userStatusEnum.description;
            }
        }
        return null;
    }

    public static Integer getValueByDescription(String description) {
        for (UserCouponStatusEnum userStatusEnum : UserCouponStatusEnum.values()) {
            if (userStatusEnum.getDescription().equals(description)) {
                return userStatusEnum.value;
            }
        }
        return null;
    }

}
