package com.newlife.charge.common;

import com.google.common.collect.Maps;
import com.newlife.charge.common.config.Global;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    static Pattern pattern = Pattern.compile("insert|update|delete|select|creat|drop|truncate\\*", Pattern.CASE_INSENSITIVE);

    public static void main(String[] args) {

        String s = "select * from t from where name like '%#name#%' " +
                "and m = '#m#' " +
                "and age >= #bage# " +
                "and age <= #eage#";
        int index = s.indexOf("from");
        s = "select count(*) " + s.substring(index);
        System.out.println(s);
    }

    public static List<String> findMatchers(String reg, String str) {
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        List<String> list = new ArrayList<String>();
        if (pattern != null && str != null) {
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                String fname = matcher.group(1);
                list.add(fname);
            }
        }
        return list;
    }

    public static String cutLast(String src) {
        if (isNotEmpty(src)) {
            return src.substring(0, src.length() - 1);
        }
        return src;
    }
    private static final String reg = "[0-9]+";
    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumer(String str) {
        if (str != null) {
            Pattern pattern = Pattern.compile(reg);
            return pattern.matcher(str).matches();
        }
        return false;
    }

    /**
     * 字符串数组转字符串
     * 去除空字符串
     *
     * @return
     */
    public static String ArrayToString(String[] strs, String split) {
        String destId = "";
        for (String str : strs) {
            if (isNotEmpty(str)) {
                destId += str + split;
            }
        }
        destId = cutLast(destId);
        return destId;
    }

    /**
     * 字符串是否在数组里
     *
     * @param str
     */
    public static boolean strInArray(String[] strs, String str) {
        for (String a : strs) {
            if (a.equals(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串是否在集合里
     *
     * @param str
     * @return
     */
    public static boolean strInArray(ArrayList<String> strs, String str) {
        for (String a : strs) {
            if (a.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String number2String(int number, int length) {
        return String.format("%0" + length + "d", number);
    }

    /**
     * 将文本域内容输出
     *
     * @param text
     * @return
     */
    public static String textToHtml(String text) {
        if (!isEmpty(text)) {
            text = text.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp;");
        }
        return text;
    }

    /**
     * 左右各去掉一个字符
     */
    public static String lr_1(String text) {
        if (!isEmpty(text)) {
            text = text.substring(1, text.length() - 1);
        }
        return text;
    }

    /**
     * 过滤字符串内的SQL关键字
     *
     * @param text
     * @return
     */
    public static String filterSql(String text) {
        if (!isEmpty(text)) {
            text = pattern.matcher(text).replaceAll(" sql关键字 ");
        }
        return text;
    }

    public static String decode(String str) {
        if (isNotEmpty(str)) {
            try {
                str = URLDecoder.decode(str, "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("UnsupportedEncodingException",e);
            }
        }
        return str;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     *  返回uuid
     *
     * @return
     */
    public static String getUUId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 判断url是否是有效的url
     *
     * @param url
     * @return
     */
    public static String urlFilter(String url) {
        if (StringUtil.isEmpty(url)) {
            url = "../../../static/img/teacher.png";
        } else {
            if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("www.")) {

            } else {
                url = Global.getImageService() + url;
            }
        }
        return url;
    }

    /**
     * 判断url是否是有效的url
     *
     * 判断url是否是有效的url按参数名称a-z排序
     * @param html
     * @return
     */
    public static String htmlToText(String html) {
        if (StringUtil.isNotEmpty(html)) {
            html = html.replaceAll("\\<.*?\\>", "");
            html = StringEscapeUtils.unescapeHtml(html);
            return html;
        } else {
            return "";
        }
    }

    /**
     * map key 升序排序
     *
     * @param map
     * @return
     */
    public static TreeMap<String, String> mapKeySort(Map<String, String> map) {
        Set keySet = map.keySet();
        List<String> list = new ArrayList<String>(keySet);

        //对key键值按字典升序排序
        Collections.sort(list);

        TreeMap<String, String> sortMap = Maps.newTreeMap();
        for (int i = 0; i < list.size(); i++) {
            sortMap.put(list.get(i), map.get(list.get(i)));
        }

        return sortMap;
    }

    /**
     * 拼接参数
     *
     * @param sortMap
     * @return
     */
    public static String pingParam(TreeMap<String, String> sortMap) {
        //random
        String param = "";
        if (sortMap.size() > 0) {
            Iterator<String> iterator = sortMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();

                if (!key.equals("random")) {
                    if(key.equals("id") || key.equals("studentId") || key.equals("userId") || key.equals("classId")) {
                        if (StringUtil.isEmpty(param)) {
                            param = param + key + "=" + sortMap.get(key);
                        } else {
                            param = param + "&" + key + "=" + sortMap.get(key);
                        }
                    }
                }
            }
        }

        return param;
    }

    /**
     * 按参数名称a-z排序
     * @param map
     * @return
     */
    public static String sortASCII (Map<String, String> map){
        SortedMap<String, String> sortedMap = new TreeMap(map);

        StringBuffer sb = new StringBuffer();
        Set es = sortedMap.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            if(null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
            }
        }
        String str = StringUtil.cutLast(sb.toString());
        System.out.println(str);
        return str;
    }

    /**
     * 将list集合转换为二维string数组
     *
     * @param list 要转换的集合
     * @return String[][] 返回的sting数组
     */

    public static String[][] listToArrayWay(List list) {
        Object o=list.get(0);

        String[] filedNames = getFiledName(o);
        int filedNum=filedNames.length;
        int listSize=list.size();
        List<Method> methods=getGetField(filedNames, o);

        String[][] arrs=new String[listSize][filedNum];
        int i=0;
        for (Object object : list) {
            int j=0;
            for (Method method : methods) {
                Object value=null;
                try {
                    value = method.invoke(object, new Object[] {});
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("属性不存在"+e);
                }
                if(value !=null && value != "") {
                    arrs[i][j]=value.toString();
                } else {
                    arrs[i][j] = "";
                }
                j++;
            }
            i++;
        }

        return arrs;
    }

    /**
     * 使用反射根据属性名称获取t属性的get方法
     *
     * @param fieldNames 属性名称
     * @param o 操作对象
     * @return List<Method> get方法
     */

    private static List<Method> getGetField(String[] fieldNames, Object o) {

        List<Method> methods=new ArrayList<Method>();
        for (String fieldName : fieldNames) {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = null;
            try {
                method = o.getClass().getMethod(getter, new Class[] {});
            } catch (NoSuchMethodException e) {
                System.out.println("属性不存在");
                continue;
            }
            //Object value = method.invoke(o, new Object[] {});
            methods.add(method);
        }
        return methods;

    }

    /**
     * 获取对象属性，返回一个字符串数组
     *
     * @param o 对象
     * @return String[] 字符串数组
     */
    private static String[] getFiledName(Object o) {
        try {
            Field[] fields = o.getClass().getDeclaredFields();
            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            return fieldNames;
        } catch (SecurityException e) {
            logger.error("SecurityException",e);
//            System.out.println(e.toString());
        }
        return null;
    }

    public static String intTOWeek(int day) {
        String weekStr="";
        switch(day){
            case 1:
                weekStr =  "星期一";
                break;
            case 2:
                weekStr = "星期二";
                break;
            case 3:
                weekStr = "星期三";
                break;
            case 4:
                weekStr = "星期四";
                break;
            case 5:
                weekStr = "星期五";
                break;
            case 6:
                weekStr = "星期六";
                break;
            default : weekStr = "星期日";
                break;
        }
        return weekStr;
    }

    /**
     * 传入1,2,3,4,5 字符串  转成星期一，星期二，星期三字符串
     * @param str
     * @return
     */
    public static String weekStr(String str){
        String weekStr = "";
        if(StringUtil.isNotEmpty(str)) {
            String[] strings = str.split(",");
            for(String s:strings) {
                String weekDay = intTOWeek(Integer.parseInt(s));
                weekStr += weekDay+",";
            }
        }
        String newWeekStr = cutLast(weekStr);
        return newWeekStr;
    }

    //返回英文字符串
    public static String intTOWeekEn(int day) {
        String weekStr="";
        switch(day){
            case 1:
                weekStr =  "MON";
                break;
            case 2:
                weekStr = "TUE";
                break;
            case 3:
                weekStr = "WED";
                break;
            case 4:
                weekStr = "THU";
                break;
            case 5:
                weekStr = "FRI";
                break;
            case 6:
                weekStr = "SAT";
                break;
            default : weekStr = "SUN";
                break;
        }
        return weekStr;
    }

    /**
     * 传入1,2,3,4,5 字符串  转成MON，TUE，WED字符串
     * @param str
     * @return
     */
    public static String weekStrEn(String str){
        String weekStr = "";
        if(StringUtil.isNotEmpty(str)) {
            String[] strings = str.split(",");
            for(String s:strings) {
                String weekDay = intTOWeekEn(Integer.parseInt(s));
                weekStr += weekDay+",";
            }
        }
        String newWeekStr = cutLast(weekStr);
        return newWeekStr;
    }

    /**
     * 标准差σ=sqrt(s^2)
     *
     * @param x
     * @return
     */
    public static double standardDiviation(List<Double> x) {
        double m = x.size();
        double sum = 0;
        for (int i = 0; i < m; i++) {//求和
            sum += x.get(i);
        }
        double dAve = sum / m;//求平均值
        double dVar = 0;
        for (int i = 0; i < m; i++) {//求方差
            dVar += (x.get(i) - dAve) * (x.get(i) - dAve);
        }
        return Math.sqrt(dVar / m);
    }
}
