package com.newlife.charge.common;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 通用工具类
 *
 * Created by lincz on 2017/12/11 0011 10:35.
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param lng1
     *            第一点经度
     * @param lat1
     *            第一点纬度
     * @param lng2
     *            第二点经度
     * @param lat2
     *            第二点纬度
     * @return 返回距离 单位：千米
     */
    public static double distance(double lng1, double lat1, double lng2, double lat2) {
        double a, b, R;
        R = 6378.137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (lng1 - lng2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /**
     * 计算实时电价(默认保留4位小数)
     * @param chargePrice 电价
     * @param servicePrice 服务费
     * @param increase 涨幅(%):+涨-减
     * @return
     */
    public static BigDecimal calculatePrice(BigDecimal chargePrice, BigDecimal servicePrice, Integer increase){

        if(chargePrice == null){
            chargePrice = new BigDecimal(0);
        }
        if(servicePrice == null){
            servicePrice = new BigDecimal(0);
        }

        if(increase != null && increase != 0){
            BigDecimal percent = new BigDecimal(100);
            BigDecimal baseIncrease = new BigDecimal(increase);
            BigDecimal scale = percent.add(baseIncrease).divide(percent);// 计算涨幅后比例
            BigDecimal increasePrice = scale.multiply(chargePrice);// 计算涨幅后价格
            BigDecimal price = increasePrice.add(servicePrice);// 实际价格
            return price.setScale(4,BigDecimal.ROUND_HALF_UP);
        }else {
            return chargePrice.add(servicePrice).setScale(4);
        }
    }


    /**
     * 将xml文件转换为对应bean对象
     * @param clazz
     * @param xml
     * @return
     */
    public static Object toBean(Class<?> clazz, String xml) {
        Object xmlObject = null;
        XStream xstream = new XStream();
        xstream.processAnnotations(clazz);
        xstream.autodetectAnnotations(true);
        xmlObject= xstream.fromXML(xml);
        return xmlObject;
    }

    /**
     * 将bean对象转换为xml文件
     * @param clazz
     * @param obj
     * @return
     */
    public static String toXml(Class<?> clazz, Object obj) {
        XStream xStream = new XStream(new Xpp3Driver(new NoNameCoder()));
        xStream.processAnnotations(clazz);
        xStream.autodetectAnnotations(true);
        String result = xStream.toXML(obj);
        return result;
    }

    public static String getPropertyValue(String key, String propertyName){
        ResourceBundle bundle = ResourceBundle.getBundle(propertyName);
        String value = bundle.getString(key);
        return value;

    }

    public static void setPropertyValue(String key, String value, String propertyName){
        String proFilePath = CommonUtil.class.getResource("/").getPath()+propertyName+".properties";

        try {
            System.out.println(proFilePath);
            Properties property = new Properties();
            property.load(new FileInputStream(proFilePath));
            property.setProperty(key, value);
            FileOutputStream os = new FileOutputStream(proFilePath);
            property.store(os, "update tmp");
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.err.println(getPropertyValue("name", "constants"));
        setPropertyValue("password", "123456", "constants");
    }

}
