package com.newlife.charge.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.newlife.charge.common.config.Global;
import com.newlife.charge.common.rest.RestApi;
import com.newlife.charge.common.sms.RedisRestUtils;
import com.newlife.charge.common.weixin.WeiXinUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 发送请求指定url
 * <p>
 * Created by linzq on 2017/10/200013 9:27.
 */
public class OkHttpUtil {

    protected static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    /**
     * Post JSON数据提交
     * @param url
     * @param map
     * @return
     * @throws IOException
     */
    public static Map<String, Object> sendPost(String url, Map<String, Object> map) {
        //JSON提交
        OkHttpClient client = new OkHttpClient();
        String json = OkHttpUtil.toJson(map);
        RequestBody body = RequestBody.create(RestApi.JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        //发送请求获取响应
        Map<String, Object> responMap = OkHttpUtil.sendRequest(client,request);
        return responMap;
    }

    /**
     * JSON数据提交带头部token
     * @param url
     * @param map
     * @return
     * @throws IOException
     */
    public static Map<String, Object> doPost(String url, Map<String, Object> map) {
        //JSON提交
        String token = OkHttpUtil.getRestToken();
        OkHttpClient client = new OkHttpClient();
        String json = OkHttpUtil.toJson(map);
        RequestBody body = RequestBody.create(RestApi.JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .post(body)
                .build();
        //发送请求获取响应
        Map<String, Object> responMap = OkHttpUtil.sendRequest(client,request);
        return responMap;
    }

    /**
     * JSON数据提交带头部token
     * @param url
     * @param list
     * @return
     * @throws IOException
     */
    public static Map<String, Object> doPost(String url, List<Map<String, Object>> list) {
        //JSON提交
        String token = OkHttpUtil.getRestToken();
        OkHttpClient client = new OkHttpClient();
        String json = OkHttpUtil.listToJson(list);
        RequestBody body = RequestBody.create(RestApi.JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .post(body)
                .build();
        //发送请求获取响应
        Map<String, Object> responMap = OkHttpUtil.sendRequest(client,request);
        return responMap;
    }
    /**身份宝
     * JSON数据提交带头部token
     * @param url
     * @param
     * @return
     * @throws IOException
     */
    public static Map<String, Object> sendHPost(String url, Map<String, Object> map,String requestTime,String signature,String orgCode) {
        //JSON提交
        String token = OkHttpUtil.getRestToken();
        OkHttpClient client = new OkHttpClient();
        String json = OkHttpUtil.toJson(map);
        RequestBody body = RequestBody.create(RestApi.JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type","application/json")
                .addHeader("appKey", Global.getConfig("sfb.appKey"))
                .addHeader("orgCode", orgCode)
                .addHeader("requestTime", requestTime)
                .addHeader("signature", signature)
                .post(body)
                .build();
        //发送请求获取响应
        Map<String, Object> responMap = OkHttpUtil.sendRequest(client,request);
        return responMap;
    }

    /**
     * GET数据提交
     * @param url
     * @return
     * @throws IOException
     */
    public static Map<String, Object> doGet(String url) {
        String token = OkHttpUtil.getRestToken();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .build();
        //发送请求获取响应
        Map<String, Object> responMap = OkHttpUtil.sendRequest(client,request);
        return responMap;
    }

    /**
     * GET数据提交 不带token 下载数据
     * @param url
     * @return
     * @throws IOException
     */
    public static String doGetNoToken(String url,HttpServletRequest httpServletRequest) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        //发送请求获取响应
        String respon = OkHttpUtil.sendRequestDownLoad(client,request,httpServletRequest);
        return respon;
    }

    /**
     * GET数据提交 不带token 下载数据
     * @param url
     * @return
     * @throws IOException
     */
    public static Map<String,Object> doGetNoToken(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        //发送请求获取响应
        Map<String,Object> responMap = OkHttpUtil.sendRequest(client,request);
        return responMap;
    }

    /**
     * DETELE数据提交
     * @param url
     * @return
     * @throws IOException
     */
    public static Map<String, Object> doDelete(String url) {
        String token = OkHttpUtil.getRestToken();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .delete()
                .build();
        //发送请求获取响应
        Map<String, Object> responMap = OkHttpUtil.sendRequest(client,request);
        return responMap;
    }

    /**
     * PUT数据提交
     * @param url
     * @return
     * @throws IOException
     */
    public static Map<String, Object> doPut(String url,Map<String, Object> map) {
        String token = OkHttpUtil.getRestToken();
        OkHttpClient client = new OkHttpClient();
        String json = OkHttpUtil.toJson(map);
        RequestBody body = RequestBody.create(RestApi.JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .put(body)
                .build();
        //发送请求获取响应
        Map<String, Object> responMap = OkHttpUtil.sendRequest(client,request);
        return responMap;
    }

    public static String toJson(Map<String, Object> map) {
        if(map == null && map.size()<0) {
            return null;
        }
        Object object = JSON.toJSON(map);
        String jsonData = object.toString();
        System.out.println(jsonData);
        return jsonData;
    }

    public static String listToJson(List<Map<String, Object>> list) {
        if(!Collections3.isNotEmpty(list)) {
            return null;
        }
        Object object = JSON.toJSON(list);
        String jsonData = object.toString();
        System.out.println(jsonData);
        return jsonData;
    }

    public static Map<String, Object> sendRequest(OkHttpClient client, Request request){
        //发送请求获取响应
        //JSONObject data = null;
        Map<String, Object> map = new HashMap<>();
        try {
            Response response = client.newCall(request).execute();
            String jsonData = "";
            jsonData = response.body().string();
            //data = JSON.parseObject(jsonData);
            //判断请求是否成功
            if(response.isSuccessful()) {
                System.out.println("访问成功！");
                map.put("data",jsonData);
                map.put("status","1");
                System.out.println(jsonData);
            } else {
                System.out.println("访问失败！");
                map.put("status","0");
                System.out.println(jsonData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("错误信息:"+e.getMessage());
        }
        return map;
    }

    public static String sendRequestDownLoad(OkHttpClient client, Request request, HttpServletRequest httpServletRequest){
        //发送请求获取响应
        Map<String, Object> map = new HashMap<>();
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len = 0;
        FileOutputStream fos = null;

        //图片后缀
        String ext = ".png";
        NewImage newImage = WeiXinUtils.createNewFile(ext,httpServletRequest);
        try {
            Response response = client.newCall(request).execute();
            is = response.body().byteStream();
            long total = response.body().contentLength();
            File file = new File(newImage.getPath());
            if (!file.getParentFile().isDirectory()) {
                file.getParentFile().delete();
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                int progress = (int) (sum * 1.0f / total * 100);
            }
            fos.flush();
            logger.error("文件下载成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("错误信息:"+e.getMessage());
        }
        return newImage.getUrl();
    }

    //获取环信token
    public static String getRestToken() {
        String token = RedisRestUtils.getRestToken();
        if(StringUtil.isNotEmpty(token)) {
            System.out.println(token);
            return token;
        } else {
            try {
                Map<String,Object> map = new HashMap<>();
                map.put("grant_type",RestApi.GRANT_TYPE);
                map.put("client_id",RestApi.CLIENT_ID);
                map.put("client_secret",RestApi.CLIENT_SECRET);
                Map<String,Object> responMap = OkHttpUtil.sendPost(RestApi.TOKEN_URL,map);
                String jsonData = responMap.get("data").toString();
                JSONObject json = JSON.parseObject(jsonData);
                token = (String) json.get("access_token");
                String application = (String)json.get("application");
                Object o = json.get("expires_in");
                long expire = Long.valueOf(String.valueOf(o)).longValue();
                RedisRestUtils.createRestToken(application,token,expire);
                System.out.println(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return token;
        }
    }

    /**
     *
     * 根据文件id下载文件
     * @throws Exception
     */
    public static InputStream getInputStream(String url) {
        InputStream is = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;

    }

    /**
     *
     * 获取下载文件信息
     * @throws Exception
     */
    public static String saveImageToDisk(String url, HttpServletRequest httpServletRequest)
            throws Exception {
        InputStream inputStream = getInputStream(url);
        byte[] data = new byte[10240];
        int len = 0;
        FileOutputStream fileOutputStream = null;
        String ext = ".amr";
        //下载微信语音路径
        NewImage oldImage = WeiXinUtils.createNewFile(ext, httpServletRequest);
        String newImage = oldImage.getPath().substring(0, oldImage.getPath().length()-4)+".mp3";
        try {
            fileOutputStream = new FileOutputStream(oldImage.getPath());
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
            //将上传的录音转为mp3格式

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        amrChangeToMp3(oldImage.getPath(), newImage);
        return oldImage.getUrl().substring(0, oldImage.getUrl().length()-4)+".mp3";
    }


   /**
    * @Description: 将上传的录音转为mp3格式
    * @Author: Linzq
    * @CreateDate:  2018/9/5 0005 15:42
    */
    private static void amrChangeToMp3(String sourcePath, String targetPath) {
        String webroot = "ffmpeg";
//        String webroot = "D:\\ffmpeg\\ffmpeg-4.0.2-win64-shared\\bin\\ffmpeg";
//        String sourcePath = "D:\\msproject\\trunk\\school-admin-web\\target\\school-admin\\upload\\file\\weixin\\20180830\\1535617874731796075";
//        String targetPath = "D:\\msproject\\trunk\\school-admin-web\\target\\school-admin\\upload\\file\\weixin\\20180830\\153562588629916666.mp3";
        //File file = new File(sourcePath);
//        String targetPath = sourcePath + ".mp3";//转换后文件的存储地址，直接将原来的文件名后加mp3后缀名
        Runtime run = null;
        try {
            run = Runtime.getRuntime();
            long start = System.currentTimeMillis();
            String str = webroot + " -i " + sourcePath + " -acodec libmp3lame " + targetPath;
            System.out.println(str);
            Process p = run.exec(str);//执行ffmpeg.exe,前面是ffmpeg.exe的地址，中间是需要转换的文件地址，后面是转换后的文件地址。-i是转换方式，意思是可编码解码，mp3编码方式采用的是libmp3lame
            String line = null;
            BufferedReader stdout = null;
            stdout = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            while ((line = stdout.readLine()) != null) {
                System.out.println(line);
            }
            stdout.close();
            //释放进程
            p.getOutputStream().close();
            p.getInputStream().close();
            p.getErrorStream().close();
            p.waitFor();
            long end = System.currentTimeMillis();
            System.out.println(sourcePath + " convert success, costs:" + (end - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //run调用lame解码器最后释放内存
            run.freeMemory();
        }
    }
}
