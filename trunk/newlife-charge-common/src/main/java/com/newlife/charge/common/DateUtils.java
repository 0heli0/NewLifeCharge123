/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.newlife.charge.common;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {





    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM", "HH:mm:ss"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static java.sql.Date getSqlDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    public static String getYear(Date date) {
        return String.valueOf(new DateTime(date).getYear());
    }

    public static String getMonth(Date date) {
        return String.valueOf(new DateTime(date).getMonthOfYear());
    }


    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay(Date date) {
        return formatDate(date, "dd");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 获得该月最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    public static java.sql.Date parseSqlDate(String str) {
        Date date = parseDate(str);
        return date != null ? new java.sql.Date(date.getTime()) : null;
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 1000);
    }


    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        if (d == null) {
            d = new Date();
        }
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }


    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);

    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取两个日期之间的分钟差
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoMinute(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60);
    }

    /**
     * 计算两个日期之间的时长，XX天XX小时XX分钟XX秒
     *
     * @param before
     * @param after
     * @param timeUnit 精确级别：如-TimeUnit.MINUTES,则返回 XX天XX小时XX分钟
     * @return
     */
    public static String getDistanceDateTimeStrOfTwoDate(Date before, Date after, TimeUnit timeUnit) {
        if (timeUnit == null) {
            timeUnit = TimeUnit.SECONDS;
        }

        StringBuilder builder = new StringBuilder();

        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        long distanceTime = afterTime - beforeTime;
        //两个时间相差的天数
        long days = distanceTime / MILLIS_PER_DAY;
        distanceTime = distanceTime - days * MILLIS_PER_DAY;
        long hours = distanceTime / MILLIS_PER_HOUR;
        distanceTime = distanceTime - hours * MILLIS_PER_HOUR;
        long minute = distanceTime / MILLIS_PER_MINUTE;
        distanceTime = distanceTime - minute * MILLIS_PER_MINUTE;
        long seconds = distanceTime / MILLIS_PER_SECOND;

        switch (timeUnit) {
            case DAYS:
                builder.append(days).append("天");
                break;
            case HOURS:
                if (days != 0L) {
                    builder.append(days).append("天");
                }
                builder.append(hours).append("小时");
                break;
            case MINUTES:
                if (days != 0L) {
                    builder.append(days).append("天");
                }
                if (hours != 0L) {
                    builder.append(hours).append("小时");
                }
                builder.append(minute).append("分钟");
                break;
            case SECONDS:
                if (days != 0L) {
                    builder.append(days).append("天");
                }
                if (hours != 0L) {
                    builder.append(hours).append("小时");
                }
                if (minute != 0L) {
                    builder.append(minute).append("分钟");
                }
                builder.append(seconds).append("秒");
                break;
        }

        return builder.toString();
    }


    /**
     * 获取日期的时间戳
     *
     * @param date
     * @return
     */
    public static Timestamp getTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * 在指定日期的基础上加减月数
     *
     * @param date
     * @param month
     * @return
     */
    public static Timestamp getPlusMonth(Date date, int month) {
        return getTimestamp(new DateTime(date).plusMonths(month).toDate());
    }

    public static Timestamp getTimestamp(String str) {
        Date date = parseDate(str);
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * 获取今天最小值
     *
     * @param date
     * @return
     */
    public static Timestamp getMinOffsetDay(Date date, int offset) {
        return getTimestamp(new DateTime(date)
                .plusDays(offset)
                .hourOfDay().withMinimumValue()
                .minuteOfHour().withMinimumValue()
                .secondOfMinute().withMinimumValue()
                .millisOfSecond().withMinimumValue()
                .toDate());
    }

    /**
     * 获取本月最小的一天
     *
     * @return
     */
    public static Timestamp getMinMonthDay(Date date) {
        return getTimestamp(new DateTime(date)
                .dayOfMonth().withMinimumValue()
                .hourOfDay().withMinimumValue()
                .minuteOfHour().withMinimumValue()
                .secondOfMinute().withMinimumValue()
                .millisOfSecond().withMinimumValue()
                .toDate());
    }

    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt, String[] weekDays) {
        if (weekDays == null || weekDays.length == 0) {
            weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }

        return weekDays[w];
    }

    /**
     * 获取本月最大的一天
     *
     * @return
     */
    public static Timestamp getMaxMonthDay() {
        return getMaxMonthDay(new Date());
    }

    public static Timestamp getMaxMonthDay(Date date) {
        return getTimestamp(new DateTime(date)
                .dayOfMonth().withMaximumValue()
                .hourOfDay().withMaximumValue()
                .minuteOfHour().withMaximumValue()
                .secondOfMinute().withMaximumValue()
                .millisOfSecond().withMaximumValue()
                .toDate());
    }

    /**
     * 获取今天最大值
     *
     * @param date
     * @return
     */
    public static Timestamp getMaxOffsetDay(Date date, int offset) {
        return getTimestamp(new DateTime(date)
                .plusDays(offset)
                .hourOfDay().withMaximumValue()
                .minuteOfHour().withMaximumValue()
                .secondOfMinute().withMaximumValue()
                .millisOfSecond().withMaximumValue()
                .toDate());
    }

    /**
     * 当前时间提前*分钟 获取corn表达式
     *
     * @param dateTime
     * @return
     */
    public static String beforFiveTimeToCorn(Date dateTime, int i, boolean flag) {
        Calendar time = Calendar.getInstance();
        time.setTime(dateTime);
        time.add(Calendar.MINUTE, -i);
        String minute = DateUtils.formatDate(time.getTime(), "mm");
        String hour = DateUtils.formatDate(time.getTime(), "HH");
        String corn = "";
        if (flag) {
            corn = "0 " + minute + " " + hour + " * * ?";
        } else {
            corn = "0 " + minute + " " + hour + " ? * ";
        }
        return corn;
    }

    /**
     * 当前时间退后*分钟 获取corn表达式
     *
     * @param dateTime
     * @return
     */
    public static String afterOneTimeToCorn(Date dateTime, int i, boolean flag) {
        Calendar time = Calendar.getInstance();
        time.setTime(dateTime);
        time.add(Calendar.MINUTE, i);
        String minute = DateUtils.formatDate(time.getTime(), "mm");
        String hour = DateUtils.formatDate(time.getTime(), "HH");
        String corn = "";
        if (flag) {
            corn = "0 " + minute + " " + hour + " * * ?";
        } else {
            corn = "0 " + minute + " " + hour + " ? * ";
        }
        return corn;
    }

    /**
     * 当前时间提前*分钟 获取corn表达式
     *
     * @param dateTime
     * @return
     */
    public static String timeToCorn(Date dateTime, boolean flag) {
        String minute = DateUtils.formatDate(dateTime, "mm");
        String hour = DateUtils.formatDate(dateTime, "HH");
        String corn = "";
        if (flag) {
            corn = "0 " + minute + " " + hour + " * * ?";
        } else {
            corn = "0 " + minute + " " + hour + " ? * ";
        }
        return corn;
    }

    public static DateRange getRangeOfMonth(String year, String month) {
        return getRangeOfMonth(Integer.parseInt(year), Integer.parseInt(month), "yyyy-MM-dd");
    }

    public static DateRange getRangeOfMonth(int year, int month, String pattern) {
        Date theMonth = new DateTime().withYear(year).withMonthOfYear(month).toDate();
        String start = formatDate(getMinMonthDay(theMonth), pattern);
        String end = formatDate(getMaxMonthDay(theMonth), pattern);
        return new DateRange(start, end);
    }

    /**
     * 取 距离当前日期的天数 的指定 整点时间
     *
     * @param time      整点时间[00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24]
     * @param dayAmount 距离当前日期的 天数
     * @return
     */
    public static Date getTimeWholePoint(Integer time, Integer dayAmount) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dayAmount);
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time.toString()));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 取 距离当前日期的天数 整点时间
     *
     * @param dayAmount 距离当前日期的 天数
     * @return
     */
    public static List<Date> getTimeWholePointList(Integer dayAmount) {
        String[] timeArr = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};

        List<Date> timeWholePoints = Lists.newArrayList();
        Stream.of(timeArr).forEach(item -> {
            timeWholePoints.add(getTimeWholePoint(Integer.valueOf(item), dayAmount));
        });

        return timeWholePoints;
    }

    /**
     * 获取两个时间段(yyyy-MM-dd)内的每一天日期
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> getDateOfTwoDate(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        while (date.compareTo(startDate) >= 0) {
            dateList.add(date);
            date = getBeforeOneday(date);
        }
        return dateList;
    }

    /**
     * 获取过24小时前的时间
     *
     * @param date
     * @return
     */
    public static Date getBeforeOneday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取时间前一个礼拜前的日期(yyyy-MM-dd)
     *
     * @param date
     * @return
     */
    public static Date getbeforeDay(Date date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String dateStr = format.format(calendar.getTime());
        return format.parse(dateStr);
    }

    public static class DateRange {

        private String start;
        private String end;

        public DateRange() {
        }

        public DateRange(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("start", start)
                    .add("end", end)
                    .toString();
        }
    }


    /**
     * LocalDate转Udate
     *
     * @param localDate
     * @return
     */
    public static Date LocalDateToUdate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * 将传入日期改为当天的00:00:00
     *
     * @param date
     * @return
     */
    public static Date formatDateToDayStart(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(date)+" 00:00:00";
        try {
            Date dateStart = format.parse(dateStr);
            return dateStart;
        }catch (ParseException ext){
            return null;
        }
    }

    /**
     * 将传入日期改为当天的23:59:59
     *
     * @param date
     * @return
     */
    public static Date formatDateToDayEnd(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date)+" 23:59:59";

        try {
            Date dateEnd = formatEnd.parse(dateStr);
            return dateEnd;
        }catch (ParseException ext){
            return null;
        }
    }

    /**
     * 获取某天已过的毫秒数
     *
     * @return
     */
    public static long getTimeMillisInOneDay(Date date) {
        long time = date.getTime();
        long zero = time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//当前零点零分零秒的毫秒数
        return time - zero;
    }


    /**
     * 获取昨天的日期
     * @return Date
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 返回日时分秒
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days) {
            return days + "天" + hours + "小时" + minutes + "分" + second + "秒";
        } else {
            return hours + "小时" + minutes + "分" + second + "秒";
        }
    }


    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws ParseException {
//		System.out.println(formatDate(parseDate("2010/3/6")));
//		System.out.println(getDate("yyyy年MM月dd日 E"));
//		long time = new Date().getTime()-parseDate("2012-11-19").getTime();
//		System.out.println(time/(24*60*60*1000));

//        Timestamp maxDay = getMaxMonthDay();
//
//        Timestamp first = getMinMonthDay(new Date());
//
//
//        DateTime date = new DateTime();
//        date.withYear(2017).withMonthOfYear(5);
//
//
//        System.out.println(getRangeOfMonth("2016", "2"));
//
//        System.out.println("first=" + formatDate(first, "yyyy-MM-dd"));
//
//        System.out.println("maxDay=" + formatDate(maxDay, "yyyy-MM-dd"));
//
//
//        System.out.println(getDistanceDateTimeStrOfTwoDate(new Date(first.getTime()), new Date(maxDay.getTime()), TimeUnit.MINUTES));

//        System.out.println(formatDate(getYesterday(),"yyyy-MM-dd HH:mm:ss"));

        System.out.println(DateUtils.getMonth(new Date()));
        System.out.println(DateUtils.getMonth());



    }
}
