package com.newlife.charge.common;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 计算方法
 * @description
 * @Author daizj Created on  2018/8/17 0017 17:12.
 */
public class NumerationUtil {

    /**
     * 计算百分比
     * @description
     * @Author daizj Created on  2018/8/17 0017 17:12.
     */
     public static String percent(double num, double total, int scale) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
         if (num==0.0){
             return 0 + "%";
         }
        double accuracy_num = num / total * 100;
        return df.format(accuracy_num) + "%";

    }
}