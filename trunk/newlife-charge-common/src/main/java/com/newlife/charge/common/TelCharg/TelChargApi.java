package com.newlife.charge.common.TelCharg;


import com.alibaba.fastjson.JSONObject;
import com.newlife.charge.common.OkHttpUtil;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 话费充值 接口地址
 * @Author: Linzq
 * @CreateDate: 2018/7/2 0002 17:59
 */
public class TelChargApi {


    public static final String KEY = "017e5e6850987bbc62624226cf7e0bf9";//申请的接口Appkey

    public static final String OPEN_ID = "JHdeda5bf12f753a780f5db1aa4eb149ee";//在个人中心查询

    //检测手机号码是否能充值接口
    public static final String TEL_ChECK_URL = "http://op.juhe.cn/ofpay/mobile/telcheck?cardnum=*&phoneno=!&key=" + KEY;
    //依据用户提供的请求为指定手机直接充值
    public static final String ON_LINE_URL = "http://op.juhe.cn/ofpay/mobile/onlineorder?key=" + KEY + "&phoneno=!&cardnum=*&orderid=@&sign=$";
    //订单状态查询
    public static final String ORDER_STA_URL = "http://op.juhe.cn/ofpay/mobile/ordersta?key=" + KEY + "&orderid=!";

    /**
     * @Description: 检测手机号码是否能充值接口
     * @Author: Linzq
     * @CreateDate: 2018/7/2 0002 18:18
     */
    public static Map<String, String> telCheck(String phone, int cardnum) throws Exception {
        Map<String, Object> result = get(TEL_ChECK_URL.replace("*", cardnum + "").replace("!", phone), 0);
        Map<String, String> resultData = parseJsonData(result);
        return resultData;
    }

    /**
     * @Description: 依据用户提供的请求为指定手机直接充值
     * @Author: Linzq
     * @CreateDate: 2018/7/2 0002 18:20
     */
    public static Map<String, String> onlineOrder(String phone, int cardnum, String orderid) throws Exception {
        Map<String, Object> result = null;
        //Md5Util工具类
        String sign = new Md5Hash(OPEN_ID + KEY + phone + cardnum + orderid).toString();
        result = get(ON_LINE_URL.replace("*", cardnum + "").replace("!", phone).replace("@", orderid).replace("$", sign), 0);
        Map<String, String> resultData = parseJsonData(result);
        return resultData;
    }

    /**
     * @Description: 订单状态查询
     * @Author: Linzq
     * @CreateDate: 2018/7/2 0002 18:21
     */
    public static Map<String, Object> orderSta(String orderid) throws Exception {
        return get(ORDER_STA_URL.replace("!", orderid), 0);
    }

    /**
     * @Description: 接口请求
     * @Author: Linzq
     * @CreateDate:  2018/7/3 0003 11:26
     */
    public static Map<String, Object> get(String url, int tts) throws Exception {

        return OkHttpUtil.doGetNoToken(url);
    }



    /**
     * 创建新活资金流水号
     * @param type (NewLifeSpendLogTypeStrEnum)
     * @Description: 新活资金流水记录类型(4位)+年月日时分秒+随机码(2)+随机码(3)
     * @return
     */
    public static String getNewOrderSn(String type) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = format.format(date);
        String random_3 = String.valueOf((int) ((Math.random() * 9 + 1) * 100));
        String random_2 = String.valueOf((int) ((Math.random() * 9 + 1) * 10));
        String orderSn = type+dateStr+random_2+random_3;
        return orderSn.replace(" ","");
    }

    /**
     * @Description: 解析返回数据
     * @Author: Linzq
     * @CreateDate:  2018/7/3 0003 11:24
     */

    public static Map<String, String>  parseJsonData(Map<String, Object> result){
        Object jsonData = result.get("data");
        JSONObject json = JSONObject.parseObject(jsonData.toString());
        Map<String, String> resultData = new HashMap<>();
        resultData.put("error_code",json.get("error_code").toString());
        resultData.put("reason",json.get("reason").toString());
        return resultData;
    }
}