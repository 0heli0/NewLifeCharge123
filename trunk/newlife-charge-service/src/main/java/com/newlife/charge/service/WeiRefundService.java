/**
 * Author: Admin
 * Date:   2019/5/17 9:54
 * Descripition:
 */
package com.newlife.charge.service;

import com.newlife.charge.core.domain.Order;
import com.newlife.charge.core.dto.in.WRefindCreateOrderIn;
import com.newlife.charge.core.dto.in.WeiRefundIn;
import com.newlife.charge.core.dto.in.WeiRefundResult;
import com.newlife.charge.core.dto.out.FormulaModeGeneralOut;
import com.newlife.charge.core.dto.out.FormulaModeOut;
import com.newlife.charge.core.dto.out.UserAccountOut;

import java.util.List;

/**
 * 车主端 微信退款管理 service类
 */
public interface WeiRefundService {

    /**
     * 创建新活退款订单
     * @param orderIn
     */
    Order createOrder(WRefindCreateOrderIn orderIn, Integer userId, String type);

    /**
     * 通知微信服务端执行退款操作
     * @param refundIn
     * @param userId
     * @return
     */
    WeiRefundResult weiChatRefund(WeiRefundIn refundIn, Integer userId);

    /**
     * 获取用户资金信息
     * @param userId
     * @return
     */
    UserAccountOut getAccount(Integer userId);

    /**
     * 车主端 退款金额计算方式
     * @param userId
     * @return
     */
    FormulaModeGeneralOut formulaMode(Integer userId);
}