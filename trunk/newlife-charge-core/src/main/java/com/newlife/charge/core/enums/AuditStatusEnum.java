package com.newlife.charge.core.enums;

/**
 * 审核状态
 *
 */
public enum AuditStatusEnum {

    NOAudited(1, "审核中"),
    PASS(2, "审核通过"),
    REFUSED(3, "审核失败"),
    NO_JOIN(4, "未提交公司资质（桩站主账号登入时判断用）"),
    NO_CREATE_STATION(5, "公司未创建桩站站点");

    private Integer value;
    private String description;

    AuditStatusEnum(Integer value, String description) {
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
        for (AuditStatusEnum auditStatusEnum : AuditStatusEnum.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (AuditStatusEnum auditStatusEnum : AuditStatusEnum.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
