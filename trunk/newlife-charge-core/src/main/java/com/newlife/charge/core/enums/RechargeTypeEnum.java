package com.newlife.charge.core.enums;

/**
 *  总后台和桩站管理员 角色 绑定id
 *
 */
public enum RechargeTypeEnum {

    GENERAL_ADMIN(1, "全额充值"),
    STATION_ADMIN(2, "优惠券充值");

    private Integer value;
    private String description;

    RechargeTypeEnum(Integer value, String description) {
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
        for (RechargeTypeEnum auditStatusEnum : RechargeTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (RechargeTypeEnum auditStatusEnum : RechargeTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
