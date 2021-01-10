package com.newlife.charge.core.enums;

/**
 * 学校类型
 * <p>
 * Created by lincz on 2017/10/13 0013.
 */
public enum SchoolTypeEnum {

    KIND("0", "幼儿园"),
    PRIMARY("1", "小学"),
    JUNIOR("2", "初中"),
    HIGH("3", "高中"),
    NINE_YEAR("4", "九年一贯制"),
    FINISH("5", "完中");


    private String value;
    private String description;

    SchoolTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByValue(String value) {
        for (SchoolTypeEnum smsTypeEnum : SchoolTypeEnum.values()) {
            if (smsTypeEnum.getValue().equals(value)) {
                return smsTypeEnum.description;
            }
        }
        return null;
    }

    public static String getValueByDescription(String description) {
        for (SchoolTypeEnum smsTypeEnum : SchoolTypeEnum.values()) {
            if (smsTypeEnum.getDescription().equals(description)) {
                return smsTypeEnum.value;
            }
        }
        return null;
    }
}
