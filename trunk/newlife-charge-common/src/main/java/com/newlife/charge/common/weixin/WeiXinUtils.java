package com.newlife.charge.common.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.newlife.charge.common.*;
import com.newlife.charge.common.config.Global;
import com.newlife.charge.common.sms.RedisRestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeiXinUtils {

    public static final String APPId = Global.getConfig("weixin.appid");

    public static final String APPSECRET = Global.getConfig("weixin.appsecret");

    // 微信小程序appid
    public static final String APP_Id = "wx875dcc460cfe82ae";

    // 微信小程序密钥key
    public static final String APP_SECRET = "88d8aa84e75f5976daf009191eece704";

    // 商户号
    public static final String MCH_Id = "1501084871";

    // API密钥
    public static final String APP_KEY = "fwsb3b62cd8546b792acb74b69b23c40";
    public static final String APP_SECRET_NEW = "9e15b275e3aef97a1ee8565cd4edffad";

    public static final String GRANT_TYPE = "client_credential";

    public static final String NODITY_URL = "http://192.168.101.52:8990/api/appletWeiChatPay/car/weiChatPayCallBack";


    public static final String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    public static final String TEMPLATE_ID_HOMEWORK = "GLY_3fc0nJKVTwXzwCmD7aHmRr5QWZ_Q6EIDmXph8gY";

    public static final String TEMPLATE_ID_NOTICE = "SoITjmavBvzvqHErvSEycA62kh148uMUH22iqOFmrrA";

    public static final BigDecimal switchToCent = new BigDecimal(100);

    /**
     * 获取微信access_token
     * @return
     */
    public static String getAccessToken() {
        String token = RedisRestUtils.getWeiXinToken();
        if(StringUtil.isNotEmpty(token)) {
            System.out.println("获取redis微信token"+token);
            return token;
        } else {
            //这个url链接地址和参数皆不能变
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="+GRANT_TYPE+"&appid="+APPId+"&secret="+APPSECRET;
            Map<String, Object> map = OkHttpUtil.doGetNoToken(url);
            Object responData = map.get("data");
            JSONObject jsonObject = JSON.parseObject(responData.toString());
            String accessToken = jsonObject.getString("access_token");
            String expires_in = jsonObject.getString("expires_in");
            if(StringUtil.isNotEmpty(expires_in) && StringUtil.isNotEmpty(accessToken)) {
                long expire = Long.valueOf(expires_in).longValue();
                RedisRestUtils.createWeiXinToken(accessToken,expire);
                return accessToken;
            } else {
                return "";
            }
        }
    }

    /**
     * 获取ticket
     * @param accessToken
     * @return
     */
    public static String getTicket(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";//这个url链接和参数不能变
        Map<String, Object> map = OkHttpUtil.doGetNoToken(url);
        Object responData = map.get("data");
        JSONObject jsonObject = JSON.parseObject(responData.toString());
        String ticket = jsonObject.getString("ticket");
        return ticket;
    }

    public static String sha1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static NewImage createNewFile(String ext, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dir = sdf.format(new Date());
        return createNewFile(dir, ext, request);
    }

    public static NewImage createNewFile(String dir, String ext, HttpServletRequest request) {
        //文件名称
        String imgName = System.currentTimeMillis() + String.valueOf((int) ((Math.random() * 9 + 1) * 100000)) + ext;
        String imgPath = "";
        String url = "";
        imgPath = request.getSession().getServletContext()
                .getRealPath(getFileDirPathByWeiXin(dir))
                + File.separator + imgName;
        //实际路径
        //访问路径
        url = request.getContextPath() + "/" + getFileDirPathByWeiXin(dir)
                + "/" + imgName;
        File file = new File(imgPath);
        if (!file.getParentFile().isDirectory()) {
            file.getParentFile().delete();
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        NewImage newImage = new NewImage(imgPath, url, imgName, ext);
        return newImage;
    }

    public static String getFileDirPathByWeiXin(String dir) {
        return Global.getConfig("down.load.path") + dir;
    }

    public static String createSign(Class<?> clazz, Object object){
        String xml =CommonUtil.toXml(clazz,object);
        try {
            StringBuffer  stringBuffer = new StringBuffer();
            List<String> list = Lists.newArrayList();
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();
            Iterator<Element> rootItor = root.elementIterator();
            while (rootItor.hasNext()){
                Element tmpElement = rootItor.next();
                String name = tmpElement.getName();
                String data = tmpElement.getData().toString();
                list.add(name+"="+data+"&");
//                stringBuffer.append();

            }

            list.sort(null);
            list.stream().forEach(str ->{
                stringBuffer.append(str);
            });
            return stringBuffer.toString().substring(0,stringBuffer.length()-1);
        }catch (DocumentException e){
            return "";
        }



    }


}
