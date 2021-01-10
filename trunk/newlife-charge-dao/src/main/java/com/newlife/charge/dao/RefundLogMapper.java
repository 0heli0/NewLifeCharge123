package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.RefundLogMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.RefundLog;
import com.newlife.charge.core.domain.WeixinRefundLog;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface RefundLogMapper  extends CrudRepository<RefundLog> {
    /**
     * 通过订单号查找记录
     * @param orderSn
     * @return
     */
    RefundLog getBySn(@Param("orderSn") String orderSn);

    /**
     * 添加微信退款记录
     * @param weiXinRefundLog
     * @return
     */
    int insertWeiXinLog(WeixinRefundLog weiXinRefundLog);
}