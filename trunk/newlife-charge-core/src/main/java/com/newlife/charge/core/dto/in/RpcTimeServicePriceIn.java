/**
 * Author: zhengyou
 * Date:   2019/5/21 13:58
 * Descripition:
 */
package com.newlife.charge.core.dto.in;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 设置时段电价和服务费电价入参
 */
@Setter
@Getter
public class RpcTimeServicePriceIn {

    /**
     * 充电桩编号集合,eg:10001或者0010001
     */
    private List<String> stationClientCodeList;

    /**
     * 十个时段电价数组，按时段从小到大排列
     */
    private String[] timePriceArr;

    /**
     * 十个时段起始时间数组，按时段从小到大排列
     */
    private String[] timeArr;

    /**
     * 服务费字符串，单位分
     */
    private String servicePrice;


}
