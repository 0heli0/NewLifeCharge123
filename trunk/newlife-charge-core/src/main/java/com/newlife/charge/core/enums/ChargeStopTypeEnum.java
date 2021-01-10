package com.newlife.charge.core.enums;

/**
 * 审核状态
 *
 */
public enum ChargeStopTypeEnum {

    CHARGE_ING(1, "充电中（默认）"),
    MANUAL_STOP(2, "用户手动停止"),
    ACCIDENT_STOP(3, "意外停止"),
    NO_MONEY_STOP(4, "余额不足停止"),
    COMPLETE_STOP(5, "电量满后停止");

    private Integer value;
    private String description;

    ChargeStopTypeEnum(Integer value, String description) {
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
        for (ChargeStopTypeEnum auditStatusEnum : ChargeStopTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (ChargeStopTypeEnum auditStatusEnum : ChargeStopTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
