package com.newlife.charge.common.ms;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

/**
 * 封装请求参数
 *
 * @Author lincz on 2019/1/15 0015.
 */
public class MsRequestBody {

    /**
     * post 提交参数
     *
     * @param paramsMap
     * @return
     */
    public static RequestBody requestBody(Map<String, String> paramsMap) {
        FormBody.Builder builder = new FormBody.Builder();
        if (paramsMap != null && !paramsMap.isEmpty()) {
            Iterator<String> iterator = paramsMap.keySet().iterator();
            while (iterator.hasNext()) {
                String param = iterator.next();
                String value = paramsMap.get(param);

                builder.add(param, value);
            }
        }

        return builder.build();
    }

    /**
     * post 提交参数和文件
     *
     * @param paramsMap
     * @return
     */
    public static RequestBody requestBody(Map<String, String> paramsMap, File[] files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //表单参数
        if (paramsMap != null && !paramsMap.isEmpty()) {
            Iterator<String> iterator = paramsMap.keySet().iterator();
            while (iterator.hasNext()) {
                String param = iterator.next();
                String value = paramsMap.get(param);

                builder.addFormDataPart(param, value);
            }
        }

        //文件参数
        if (files != null && files.length > 0) {
            //单个文件
            String param = "file";

            for (File file : files) {
                RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                builder.addFormDataPart(param, file.getName(), body);
            }
        }

        return builder.build();
    }
}
