package com.newlife.charge.core.enums.log;

/**
 * 操作类型
 * <p>
 * Created by lincz on 2017/9/27 0027 9:53.
 */
public enum PlatLogOperateType {

    Login(0, "登录操作"),
    Common(1, "业务操作");

    private Integer value;
    private String description;

    PlatLogOperateType(Integer value, String description) {
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
        for (PlatLogOperateType auditStatusEnum : PlatLogOperateType.values()) {
            if (auditStatusEnum.getValue() == value) {
                return auditStatusEnum.description;
            }
        }
        return null;
    }

    public static int getValueByDescription(String description) {
        for (PlatLogOperateType auditStatusEnum : PlatLogOperateType.values()) {
            if (auditStatusEnum.getDescription().equals(description)) {
                return auditStatusEnum.value;
            }
        }
        return 0;
    }


}
