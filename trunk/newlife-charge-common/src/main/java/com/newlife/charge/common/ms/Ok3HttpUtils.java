package com.newlife.charge.common.ms;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Okhttp3 工具类
 *
 * @Author lincz on 2019/1/15 0015 14:05.
 */
public class Ok3HttpUtils {

    protected static final Logger logger = LoggerFactory.getLogger(Ok3HttpUtils.class);

    private static OkHttpClient singleton;

    //取 OkHttpClient 单例
    public static OkHttpClient getInstence() {
        if (singleton == null) {
            //枷锁
            synchronized (Ok3HttpUtils.class) {
                if (singleton == null) {
                    singleton = new OkHttpClient();

                    //超时配制
                    singleton.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
                    singleton.newBuilder().readTimeout(10, TimeUnit.SECONDS);
                    singleton.newBuilder().writeTimeout(30, TimeUnit.SECONDS);
                }
            }
        }
        return singleton;
    }

    /**
     * 同步表单 请求
     *
     * @param client
     * @param url
     * @param requestBody
     * @return
     */
    public static String sendSynFormPost(OkHttpClient client, String url, String token, RequestBody requestBody) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
