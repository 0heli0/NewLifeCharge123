package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.OrderMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.newlife.charge.core.domain.Order;
import com.newlife.charge.core.domain.exModel.WeiChatOrderDetailEx;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper  extends CrudRepository<Order> {

    /**
     * 获取订单详情信息
     * @param oderSn
     * @return
     */
    WeiChatOrderDetailEx getWeiChatOrderDetail(@Param("orderSn") String oderSn);

    /**
     * 根据订单号获取订单信息
     * @param oderSn
     * @return
     */
    Order getBySn(@Param("orderSn") String oderSn);

}