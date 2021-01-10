package com.newlife.charge.common.jpush;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.newlife.charge.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 极光推送消息
 */
public class JPushRestUtil {

    private static Logger logger = LoggerFactory.getLogger(JPushRestUtil.class);

    public  static PushPayload buildIosPushObject(NoticePush noticePush) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setOptions(Options.newBuilder().setApnsProduction(Boolean.parseBoolean(Global.getConfig("jush.options")))
                        .build())
                .setAudience(Audience.alias(noticePush.getUserIds()))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(noticePush.getContent())
                                .setSound("default")
                                .setBadge(+1)
                                .addExtras(noticePush.getExtraMap())
                                .build())
                        .build())
                .build();
    }

    public static PushPayload buildAndroidPushObject(NoticePush noticePush) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(noticePush.getUserIds()))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(noticePush.getContent())
                                    .addExtras(noticePush.getExtraMap())
                                    .build())
                        .build())
                .build();
    }
    public static void sendWithSMS(NoticePush noticePush){
        sendWithSMS1(noticePush);
        sendWithSMS2(noticePush);
    }

    public static void sendWithSMS1(NoticePush noticePush) {
        JPushClient jpushClient = new JPushClient(Global.getConfig("jpush.master.secret"), Global.getConfig("jpush.app.key"));
        try {
            PushResult result1 = jpushClient.sendPush(buildAndroidPushObject(noticePush));
            System.out.println("推送结果"+result1);
        } catch (APIConnectionException e) {
//            logger.error("Connection error. Should retry later. ", e);
//            logger.error("Error response from JPush server. Should review and fix it. ", e);
        }catch (APIRequestException e) {
//            logger.error("Error response from JPush server. Should review and fix it. ", e);
//            logger.info("HTTP Status: " + e.getStatus());
//            logger.info("Error Code: " + e.getErrorCode());
//            logger.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static void sendWithSMS2(NoticePush noticePush) {
        JPushClient jpushClient = new JPushClient(Global.getConfig("jpush.master.secret"), Global.getConfig("jpush.app.key"));
        try {
            PushResult result = jpushClient.sendPush(buildIosPushObject(noticePush));
            System.out.println("推送结果"+result);
        } catch (APIConnectionException e) {
//            logger.error("Connection error. Should retry later. ", e);
//            logger.error("Error response from JPush server. Should review and fix it. ", e);
        }catch (APIRequestException e) {
//            logger.error("Error response from JPush server. Should review and fix it. ", e);
//            logger.info("HTTP Status: " + e.getStatus());
//            logger.info("Error Code: " + e.getErrorCode());
//            logger.info("Error Message: " + e.getErrorMessage());
        }
    }
}
