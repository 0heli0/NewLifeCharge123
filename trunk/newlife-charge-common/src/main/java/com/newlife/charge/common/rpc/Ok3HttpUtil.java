package com.newlife.charge.common.rpc;

import com.newlife.charge.common.rest.RestApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * rpc client
 *
 * Created by lincz on 2017/12/27 0027 9:35.
 */
public class Ok3HttpUtil {

    protected static final Logger logger = LoggerFactory.getLogger(Ok3HttpUtil.class);

    /**
     * Post JSON数据提交
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String sendJSONPost(String url, String jsonData) {
        //JSON提交
        OkHttpClient client = new OkHttpClient();

        client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10,TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10,TimeUnit.SECONDS);

        RequestBody body = RequestBody.create(RestApi.JSON, jsonData);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        //发送请求获取响应
        return sendRequest(client, request);
    }

    //发送请求获取响应
    private static String sendRequest(OkHttpClient client, Request request) {
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("错误信息:" + e.getMessage());
        }
        return null;
    }
}
