package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.RechargeLogMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.RechargeLog;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface RechargeLogMapper  extends CrudRepository<RechargeLog> {
    /**
     * 通过订单获取记录
     * @param orderSn
     * @return
     */
    RechargeLog getBySn(@Param("orderSn") String orderSn);
}