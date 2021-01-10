package com.newlife.charge.core.enums;

/**
 * 所属系统
 *
 */
public enum ProjectTypeEnum {
    PRO_GENERAL(0,"总后台系统"),
    PRO_STATION(1,"桩站系统");
    private  Integer value;
    private  String description;

    ProjectTypeEnum(Integer value, String description) {
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
