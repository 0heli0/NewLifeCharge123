package com.newlife.charge.core.enums;

/**
 * 审核状态
 *
 */
public enum PayStatusEnum {

    NONE(0, "未支付"),
    SUCCESS(1, "支付成功"),
    FAIL(2, "支付失败");

    private Integer value;
    private String description;

    PayStatusEnum(Integer value, String description) {
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
        for (PayStatusEnum auditStatusEnum : PayStatusEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (PayStatusEnum auditStatusEnum : PayStatusEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
