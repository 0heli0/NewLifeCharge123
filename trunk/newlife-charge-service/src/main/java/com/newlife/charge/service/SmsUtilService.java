/**
 * Author: Fuq
 * Date:   2018/5/15 9:17
 * Descripition:短信
 */
package com.newlife.charge.service;


import com.newlife.charge.common.ms.vo.SmsModel;
import com.newlife.charge.core.domain.SmsLog;

/**
 * 发送短信 service
 *
 */
public interface SmsUtilService {

    /**
     * 根据 手机号，短信模版，短信内容 发短信
     *
     * @param mobiles    手机号
     * @param templateId 短信模版
     * @param params     内容参数
     * @return
     */
    SmsModel sendSms(String mobiles, String templateId, String[] params);



    /**
     * 查询某手机号最后一次发送的短信记录
     * @param mobile
     * @param templateId
     * @return
     */
    SmsLog getLast(String mobile, String templateId);


    /**
     * 查询当日该手机号码一个短信模板ID发送成功的次数
     *
     * @param mobile
     * @param templateId
     * @param createTime
     * @return
     */
    int countSendNumber( String mobile,  String templateId, String createTime);


    /**
     * 发送前校验
     * 1.查询手机号某模板的发送次数，超限则不发送
     * 2.两次发送不能超过1分钟
     *
     * @param mobile
     * @param templateId
     * @return
     */
    SmsModel validBeforeSend(String mobile, String templateId);

}
