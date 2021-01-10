package com.newlife.charge.core.enums;

/**
 * 硬件上充电桩设备状态
 * 只能硬件用，其他不要使用这个类
 * 1字节
 * <p>
 * 0 空闲,1 连接中,2 充电中,3 充电完成,4 被预约,5 排队中
 * <p>
 */
public enum PileStatusEnum {
    FREE("0", "空闲"),
    CONNECTING("1", "连接中"),
    CHARGING("2", "充电中"),
    COMPLETED("3", "充电完成"),
    APPOINTMENT("4", "被预约"),
    QUEUE("5", "排队中"),
    UN_KNOWN("", "未知");

    private String value;
    private String description;


    PileStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static PileStatusEnum getByValue(String value) {
        for (PileStatusEnum commandWordEnum : PileStatusEnum.values()) {
            if (commandWordEnum.getValue().equals(value)) {
                return commandWordEnum;
            }
        }
        return UN_KNOWN;
    }

    public static String getDescriptionByValue(String value) {
        for (PileStatusEnum commandWordEnum : PileStatusEnum.values()) {
            if (commandWordEnum.getValue().equals(value)) {
                return commandWordEnum.description;
            }
        }
        return null;
    }

    public static String getValueByDescription(String description) {
        for (PileStatusEnum commandWordEnum : PileStatusEnum.values()) {
            if (commandWordEnum.getDescription().equals(description)) {
                return commandWordEnum.value;
            }
        }
        return null;
    }

}
