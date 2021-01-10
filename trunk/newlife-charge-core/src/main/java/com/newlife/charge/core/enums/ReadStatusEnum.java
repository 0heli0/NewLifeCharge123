package com.newlife.charge.core.enums;

/**
 * 阅读状态
 *
 */
public enum ReadStatusEnum {
    NO_READ(0,"未阅读"),
    READED(1,"已阅读");
    private  Integer value;
    private  String description;

    ReadStatusEnum(Integer value, String description) {
        this.value=value;
        this.description=description;
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
