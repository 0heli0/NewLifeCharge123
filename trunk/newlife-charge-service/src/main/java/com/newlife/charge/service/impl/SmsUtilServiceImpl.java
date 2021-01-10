package com.newlife.charge.service.impl;

import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.StringUtil;
import com.newlife.charge.common.ms.vo.SmsModel;
import com.newlife.charge.core.domain.SmsLog;
import com.newlife.charge.dao.SmsLogMapper;
import com.newlife.charge.service.SmsUtilService;
import com.newlife.charge.service.ms.MsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmsUtilServiceImpl implements SmsUtilService {

    private static final Logger logger = LoggerFactory.getLogger(SmsUtilServiceImpl.class);

    public static final int FREQUENCY = 5;//每天发送次数
    public static final int TIME_INTERVAL = 1;//时间间隔1分钟

    @Autowired
    private SmsLogMapper smsLogMapper;

    @Autowired
    private MsApi msApi;

    @Value("${commonService.path}")
    private String commonServicePath;

    @Value("${commonService.msmApiKey}")
    private String msmApiKey;

    @Value("${commonService.msmSecretKey}")
    private String msmSecretKey;

    @Value("${commonService.sendSmsPath}")
    private String sendSmsPath;



    @Override
    public SmsLog getLast(String mobile, String templateId) {
        return this.smsLogMapper.getLast(mobile, templateId);
    }

    @Override
    public int countSendNumber(String mobile, String templateId, String createTime) {
        return this.smsLogMapper.countSendNumber(mobile, templateId, createTime);
    }

    @Override
    public SmsModel validBeforeSend(String mobile, String templateId) {

        boolean frequency_flag = false;
        boolean time_interval_flag = false;

        SmsModel smsModel =null;
        if (StringUtil.isNotEmpty(mobile)) {
            //时间间隔判断
            SmsLog smsLogLast = this.getLast(mobile, templateId);
            if(smsLogLast==null){
                time_interval_flag = true;
            }else{
                long lastTime = smsLogLast.getCreateTime().getTime();
                long currentTimeMillis = System.currentTimeMillis();
                //两次发送时间大于1分钟
                if(currentTimeMillis-lastTime>TIME_INTERVAL*1000*60){
                    time_interval_flag = true;
                }
            }
            String toDay = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
            //次数判断
            int countSendNumber = this.countSendNumber(mobile, templateId, toDay);
            if (countSendNumber < FREQUENCY) {
                frequency_flag = true;
            }

            if (!time_interval_flag) {
                smsModel= new SmsModel();
                smsModel.setCode(500);
                smsModel.setMessage("短信验证码发送过频繁");
            }
            if (!frequency_flag) {
                smsModel= new SmsModel();
                smsModel.setCode(500);
                smsModel.setMessage("今日短信发送已超过次数");
            }

        }
        return smsModel;
    }



    @Override
    public final SmsModel sendSms(String mobiles, String templateId, String[] params) {

        SmsModel smsModel = new SmsModel();
        if (StringUtil.isNotEmpty(mobiles)) {
            String param = "";
            if (params.length > 0) {
                for (String s : params) {
                    param += s + ",";
                }
            }
            String newParam = StringUtil.cutLast(param);

            //保存发送记录，默认失败
            saveSmsLog(mobiles, params, templateId);

            //可以发送
            //获取发送短信token
            String accessToken = msApi.createAccessToken(msmApiKey, msmSecretKey);
            //发送短信
            smsModel = msApi.sendSms(commonServicePath + sendSmsPath, mobiles, newParam, templateId, accessToken);
            boolean isSuccess = false;
            if (smsModel.getCode() == 200) {
                isSuccess = true;
            }
            //更新发送结果
            updateSmsLog(isSuccess, mobiles, params, templateId);
        }
        return smsModel;
    }


//    @Override
//    public final SmsModel sendSms(String mobiles, String templateId, String[] params) {
//        SmsModel smsModel = new SmsModel();
//        if (StringUtil.isNotEmpty(mobiles)) {
//            String param = "";
//            if (params.length > 0) {
//                for (String s : params) {
//                    param += s + ",";
//                }
//            }
//            String newParam = StringUtil.cutLast(param);
//
//            //获取发送短信token
//            String accessToken = msApi.createAccessToken(msmApiKey, msmSecretKey);
//            //发送短信
//            smsModel = msApi.sendSms(commonServicePath + sendSmsPath, mobiles, newParam, templateId, accessToken);
//            boolean isSuccess = false;
//            if (smsModel.getCode() == 200) {
//                isSuccess = true;
//            }
//            //保存发送记录
//            slowSmsLog(isSuccess, mobiles, params, templateId);
//        }
//        return smsModel;
//    }

//    /**
//     * 记录短信发送日志
//     *
//     * @return
//     */
//    private void slowSmsLog(Boolean SendSuccess, String mobiles, String[] params, String templateId) {
//        new Thread() {
//            public void run() {
//                try {
//                    Thread.sleep(0);
//                    String[] mobileList = mobiles.split(",");
//                    for (String item : mobileList) {
//                        SmsLog smsLog = new SmsLog();
//                        smsLog.setMobile(item);
//                        if (SendSuccess) {
//                            smsLog.setSendStatus(1);
//                        } else {
//                            smsLog.setSendStatus(0);
//                        }
//                        smsLog.setCreateTime(new Date());
//                        smsLog.setSmsContent(params[0]);
//                        smsLog.setTemplateId(templateId);
//                        smsLogMapper.insert(smsLog);
//                    }
//
//                } catch (InterruptedException e) {
//                    logger.error("记录短信发送日志失败",e);
//                }
//            }
//        }.start();
//    }


    /**
     * 记录短信发送日志
     *
     * @return
     */
    private void saveSmsLog(String mobiles, String[] params, String templateId) {

        try {
            String[] mobileList = mobiles.split(",");
            for (String item : mobileList) {
                SmsLog smsLog = new SmsLog();
                smsLog.setMobile(item);
                smsLog.setSendStatus(0);    //默认发送失败
                smsLog.setCreateTime(new Date());
                smsLog.setSmsContent(params[0]);
                smsLog.setTemplateId(templateId);
                smsLogMapper.insert(smsLog);
            }
        } catch (Exception e) {
            logger.error("记录短信发送日志失败", e);
        }

    }


    /**
     * 更新短信发送日志的发送状态
     *
     * @return
     */
    private void updateSmsLog(Boolean sendSuccess, String mobiles, String[] params, String templateId) {

        try {

            Integer sendStatus = 0;
            if (sendSuccess) {
                sendStatus = 1;
            }

            String[] mobileList = mobiles.split(",");
            for (String item : mobileList) {

                this.smsLogMapper.updateSendStatusByParams(sendStatus, item, params[0], templateId);
            }

        } catch (Exception e) {
            logger.error("更新短信发送日志发送状态失败", e);
        }

    }

}
