package com.newlife.charge.service.ms;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.newlife.charge.common.StringUtil;
import com.newlife.charge.common.ms.MsRequestBody;
import com.newlife.charge.common.ms.Ok3HttpUtils;
import com.newlife.charge.common.ms.vo.*;
import com.newlife.charge.core.exception.BizException;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 咪师Api 接口
 *
 */
@Component
public class MsApi {

    @Value("${commonService.path}")
    private String commonServicePath;

    /**
     * 生成 访问 access_token
     *
     * @param apiKey    api key
     * @param secretKey secret key
     * @return
     */
    public String createAccessToken(String apiKey, String secretKey) {

        OkHttpClient client = Ok3HttpUtils.getInstence();
        //生成token接口地址
        String getTokenPath = commonServicePath + "/api/access/create";
        //参数
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("apiKey", apiKey);
        paramMap.put("secretKey", secretKey);
        RequestBody requestBody = MsRequestBody.requestBody(paramMap);
        String result = Ok3HttpUtils.sendSynFormPost(client, getTokenPath, "", requestBody);
        if (StringUtil.isNotEmpty(result)) {
            //解析
            TokenModel tokenModel = JSON.parseObject(result, TokenModel.class);
            if (tokenModel.getCode() != 200) {
                throw new BizException(tokenModel.getCode(), tokenModel.getMessage());
            }
            return tokenModel.getData();
        } else {
            throw new BizException("生成 访问 access_token 出错!");
        }
    }

    /**
     * 上传单个文件
     *
     * @param file        上传文件
     * @param msApikey    图片库密钥唯一
     * @param accessToken 访问accessToken
     * @return
     */
    public FileInfo uploadFile(String url, String msApikey, File file, String accessToken) {
        OkHttpClient client = Ok3HttpUtils.getInstence();

        //参数
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("msApikey", msApikey);
        RequestBody requestBody = MsRequestBody.requestBody(paramMap, new File[]{file});

        String result = Ok3HttpUtils.sendSynFormPost(client, url, accessToken, requestBody);
        if (StringUtil.isNotEmpty(result)) {
            //解析
            FileModel fileModel = JSON.parseObject(result, FileModel.class);
            if (fileModel.getCode() != 200) {
                throw new BizException(fileModel.getCode(), fileModel.getMessage());
            }

            return fileModel.getData();
        } else {
            throw new BizException("上传单个文件出错!");
        }
    }

    /**
     * 上传多个文件
     *
     * @param files       上传文件
     * @param msApikey    图片库密钥唯一
     * @param accessToken 访问accessToken
     * @return
     */
    public List<FileInfo> uploadFiles(String url, File[] files, String accessToken, String msApikey) {
        OkHttpClient client = Ok3HttpUtils.getInstence();

        //参数
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("msApikey", msApikey);
        RequestBody requestBody = MsRequestBody.requestBody(paramMap, files);

        String result = Ok3HttpUtils.sendSynFormPost(client, url, accessToken, requestBody);
        if (StringUtil.isNotEmpty(result)) {
            //解析
            FilesModel filesModel = JSON.parseObject(result, FilesModel.class);
            if (filesModel.getCode() != 200) {
                throw new BizException(filesModel.getCode(), filesModel.getMessage());
            }

            return filesModel.getData();
        } else {
            throw new BizException("上传多个文件 出错!");
        }
    }

    /**
     * 添加 或 更新人脸
     *
     * @param image       图片   //图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断
     * @param imageType   图片类型  //BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
     *                    //URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
     *                    //FACE_TOKEN：人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
     * @param groupId     //用户组id，标识一组用户（由数字、字母、下划线组成），长度限制48B。产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param userId      //用户id（由数字、字母、下划线组成），长度限制128B
     * @param userInfo    //用户资料，长度限制256B 默认空
     * @param msApikey    //咪师人脸库密钥唯一
     * @param accessToken 访问accessToken
     * @return
     */
    public FaceInfo addFace(String image, String imageType, String groupId, String userId, String userInfo, String msApikey, String accessToken) {
        OkHttpClient client = Ok3HttpUtils.getInstence();

        //参数
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("image", image);
        paramMap.put("imageType", imageType);
        paramMap.put("groupId", groupId);
        paramMap.put("userId", userId);
        paramMap.put("userInfo", userInfo);
        paramMap.put("userInfo", userInfo);
        paramMap.put("msApikey", msApikey);
        RequestBody requestBody = MsRequestBody.requestBody(paramMap);

        String url = commonServicePath + "/api/face/add";
        String result = Ok3HttpUtils.sendSynFormPost(client, url, accessToken, requestBody);
        if (StringUtil.isNotEmpty(result)) {
            //解析
            FaceAddModel faceModel = JSON.parseObject(result, FaceAddModel.class);
            if (faceModel.getCode() != 200) {
                System.out.println(JSON.toJSON(faceModel));
                throw new BizException("添加人脸失败，请检查正脸照！");
//                throw new BizException(faceModel.getCode(), faceModel.getMessage());
            }

            return faceModel.getData();
        } else {
            throw new BizException("添加或更新人脸 出错!");
        }
    }

    /**
     * 删除人脸
     *
     * @param groupId     //用户组id，标识一组用户（由数字、字母、下划线组成），长度限制48B。产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param userId      //用户id（由数字、字母、下划线组成），长度限制128B
     * @param faceToken   //用户资料，长度限制256B 默认空
     * @param msApikey    //咪师人脸库密钥唯一
     * @param accessToken 访问accessToken
     */
    public FaceDeleteModel deleteFace(String groupId, String userId, String faceToken, String msApikey, String accessToken) {
        OkHttpClient client = Ok3HttpUtils.getInstence();

        //参数
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("groupId", groupId);
        paramMap.put("userId", userId);
        paramMap.put("faceToken", faceToken);
        paramMap.put("msApikey", msApikey);
        RequestBody requestBody = MsRequestBody.requestBody(paramMap);

        String url = commonServicePath + "/api/face/delete";
        String result = Ok3HttpUtils.sendSynFormPost(client, url, accessToken, requestBody);
        if (StringUtil.isNotEmpty(result)) {
            //解析
            FaceDeleteModel faceDeleteModel = JSON.parseObject(result, FaceDeleteModel.class);
            if (faceDeleteModel.getCode() != 200) {
                throw new BizException(faceDeleteModel.getCode(), faceDeleteModel.getMessage());
            }

            return faceDeleteModel;
        } else {
            throw new BizException("删除人脸 出错!");
        }
    }

    /**
     * 搜索 人脸
     *
     * @param image       图片   //图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断
     * @param imageType   图片类型  //BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
     *                    //URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
     *                    //FACE_TOKEN：人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
     * @param groupId     //用户组id，标识一组用户（由数字、字母、下划线组成），长度限制48B。产品建议：根据您的业务需求，可以将需要注册的用户，按照业务划分，分配到不同的group下，例如按照会员手机尾号作为groupid，用于刷脸支付、会员计费消费等，这样可以尽可能控制每个group下的用户数与人脸数，提升检索的准确率
     * @param msApikey    //咪师人脸库密钥唯一
     * @param accessToken 访问accessToken
     */
    public FaceSearchInfo searchFace(String image, String imageType, String groupId, String msApikey, String accessToken) {
        OkHttpClient client = Ok3HttpUtils.getInstence();

        //参数
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("image", image);
        paramMap.put("imageType", imageType);
        paramMap.put("groupId", groupId);
        paramMap.put("msApikey", msApikey);
        RequestBody requestBody = MsRequestBody.requestBody(paramMap);

        String url = commonServicePath + "/api/face/search";
        String result = Ok3HttpUtils.sendSynFormPost(client, url, accessToken, requestBody);
        if (StringUtil.isNotEmpty(result)) {
            //解析
            FaceSearchModel faceSearchModel = JSON.parseObject(result, FaceSearchModel.class);
            if (faceSearchModel.getCode() != 200) {
                throw new BizException(faceSearchModel.getCode(), faceSearchModel.getMessage());
            }

            return faceSearchModel.getData();
        } else {
            throw new BizException("人脸搜索 出错!");
        }
    }

    /**
     * 检测 人脸
     *
     * @param image       图片   //图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断
     * @param imageType   图片类型  //BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
     *                    //URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
     *                    //FACE_TOKEN：人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
     * @param msApikey    //咪师人脸库密钥唯一
     * @param accessToken 访问accessToken
     */
    public FaceDecetModel detectFace(String image, String imageType, String msApikey, String accessToken) {
        OkHttpClient client = Ok3HttpUtils.getInstence();

        //参数
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("image", image);
        paramMap.put("imageType", imageType);
        paramMap.put("msApikey", msApikey);
        RequestBody requestBody = MsRequestBody.requestBody(paramMap);

        String url = commonServicePath + "/api/face/detect";
        String result = Ok3HttpUtils.sendSynFormPost(client, url, accessToken, requestBody);
        if (StringUtil.isNotEmpty(result)) {
            //解析
            FaceDecetModel faceDecetModel = JSON.parseObject(result, FaceDecetModel.class);
            if (faceDecetModel.getCode() != 200) {
                throw new BizException(faceDecetModel.getCode(), faceDecetModel.getMessage());
            }

            return faceDecetModel;
        } else {
            throw new BizException("人脸检测 出错!");
        }
    }

    /**
     * 发送短信
     *
     * @param mobile      发送电话号码（多个用逗号隔开）
     * @param params      对应参数 例：咪师,3分钟（多个用逗号隔开）
     * @param msApikey    咪师短信模板密钥唯一
     * @param accessToken 访问访问accessToken
     */
    public SmsModel sendSms(String url, String mobile, String params, String msApikey, String accessToken) {
        OkHttpClient client = Ok3HttpUtils.getInstence();

        //参数
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("mobile", mobile);
        paramMap.put("params", params);
        paramMap.put("msApikey", msApikey);
        RequestBody requestBody = MsRequestBody.requestBody(paramMap);

        String result = Ok3HttpUtils.sendSynFormPost(client, url, accessToken, requestBody);
        if (StringUtil.isNotEmpty(result)) {
            //解析
            SmsModel smsModel = JSON.parseObject(result, SmsModel.class);
            if (smsModel.getCode() != 200) {
                throw new BizException(smsModel.getCode(), smsModel.getMessage());
            }

            return smsModel;
        } else {
            throw new BizException("发送短信 出错!");
        }

    }

}
