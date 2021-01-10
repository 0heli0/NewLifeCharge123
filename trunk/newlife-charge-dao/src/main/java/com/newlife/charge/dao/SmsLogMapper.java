/* -------------------------------------------
 * SmsLogMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2018-12-10 Created
 * ------------------------------------------- */


package com.newlife.charge.dao;

import com.newlife.charge.core.domain.SmsLog;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

/**
 * 短信日志记录表 mapper
 *
 */
public interface SmsLogMapper extends CrudRepository<SmsLog> {

    /**
     * 查询当日该手机号码一个短信模板ID发送次数
     *
     * @param mobile
     * @param templateId
     * @param createTime
     * @return
     */
    int countSendNumber(@Param("mobile") String mobile, @Param("templateId") String templateId, @Param("createTime") String createTime);


    /**
     * 查询某手机号最后一次发送的短信记录
     * @param mobile
     * @param templateId
     * @return
     */
    SmsLog getLast(@Param("mobile") String mobile, @Param("templateId") String templateId);


    /**
     * 根据手机号，短信内容，模板ID 更新短信发送状态
     *
     * @param sendStatus
     * @param mobile
     * @param smsContent
     * @param templateId
     * @return
     */
    int updateSendStatusByParams(@Param("sendStatus") Integer sendStatus,@Param("mobile") String mobile, @Param("smsContent") String smsContent, @Param("templateId") String templateId);


}