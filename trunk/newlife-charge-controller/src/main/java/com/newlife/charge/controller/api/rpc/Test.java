/**
 * Author: zhengyou
 * Date:   2019/5/6 13:47
 * Descripition:
 */
package com.newlife.charge.controller.api.rpc;

import com.newlife.charge.common.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

public class Test {

    public static void main(String[] args) {

//        Date date = new Date();
//        Time time = new Time(date.getTime());
//        System.out.println(time);
//        System.out.println(date.getTime());
//        System.out.println(time.getTime());
//

        long current=System.currentTimeMillis();//当前时间毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)- TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
        long yesterday=System.currentTimeMillis()-24*60*60*1000;//昨天的这一时间的毫秒数
//        System.out.println(new Timestamp(current));//当前时间
//        System.out.println(new Timestamp(yesterday));//昨天这一时间点
//        System.out.println(new Timestamp(zero));//今天零点零分零秒
//        System.out.println(new Timestamp(twelve));//今天23点59分59秒
        System.out.println(current);//当前时间毫秒数
        System.out.println(zero);//今天零点零分零秒的毫秒数
        System.out.println(current-zero);//今天已过的毫秒数

        long timeMillisInOneDay = DateUtils.getTimeMillisInOneDay(new Date());
        System.out.println(timeMillisInOneDay);

Integer stationId = 1;
        String s = StringUtils.leftPad(String.valueOf(stationId), 4, "0");
        System.out.println(s);
        String maxCode = "00011001";
        System.out.println(maxCode);
        String numCode = "1";
        numCode = maxCode.substring(5);
        System.out.println(numCode);
        numCode = String.valueOf(Integer.valueOf(numCode)+1);//自增1
        numCode =  StringUtils.leftPad(numCode,3,"0");

        System.out.println(numCode);


    }
}
