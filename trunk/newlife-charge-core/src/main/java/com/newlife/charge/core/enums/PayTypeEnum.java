package com.newlife.charge.core.enums;

/**
 * 审核状态
 *
 */
public enum PayTypeEnum {

    WEIXIN(1, "微信"),
    ACCOUNT_BALANCE(2, "账户余额"),
    ALIPAY(3, "支付宝");

    private Integer value;
    private String description;

    PayTypeEnum(Integer value, String description) {
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
        for (PayTypeEnum auditStatusEnum : PayTypeEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (PayTypeEnum auditStatusEnum : PayTypeEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
