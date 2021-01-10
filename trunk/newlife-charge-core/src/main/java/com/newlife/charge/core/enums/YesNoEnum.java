package com.newlife.charge.core.enums;

/**
 * 是否状态
 */
public enum YesNoEnum {
    NO(0, "否"),
    YES(1, "是");
    private Integer value;
    private String description;

    YesNoEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
