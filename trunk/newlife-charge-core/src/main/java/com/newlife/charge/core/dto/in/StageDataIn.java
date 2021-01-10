/**
 * Author: zhengyou
 * Date:   2019/5/23 16:58
 * Descripition:时段内充电电量和电费
 */
package com.newlife.charge.core.dto.in;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class StageDataIn  {

    /**
     * 时段序号
     */
    private Integer time;
    /**
     * 充电金额
     * 4字节 两位小数
     */
    private String balance;
    /**
     * 充电电能
     * 4字节 两位小数
     */
    private String power;

    @Override
    public String toString() {
        return "{" +
                "time='" + time + '\'' +
                ", balance='" + balance + '\'' +
                ", power='" + power + '\'' +
                '}';
    }
}
