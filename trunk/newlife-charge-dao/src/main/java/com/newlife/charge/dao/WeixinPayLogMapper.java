package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.WeixinPayLogMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.WeixinPayLog;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface WeixinPayLogMapper  extends CrudRepository<WeixinPayLog> {

    /**
     * 根据微信订单号获取微信充值记录
     * @param transactionId
     * @return
     */
    WeixinPayLog getBySn(@Param("transactionId") String transactionId);

    /**
     * 新增，忽略空数据
     * @param WeixinPayLog
     * @return
     */
    int insertByObj(WeixinPayLog WeixinPayLog);

}