package com.newlife.charge.service;


import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.MaxUseableCouponOut;

/**
 * 车主端 微信支付统一下单 Service 类
 * <p>
 */
public interface UnifiedOrderService {

    /**
     * 车主端 微信支付统一下单
     * @param queryIn
     * @return
     */
    UnifiedOrderResult unifiedOrder(UnifiedOrderQueryIn queryIn, Integer userId);

    /**
     * 车主端 微信支付生成订单号
     * @param in
     * @param userId
     * @param type 订单类型
     * @return
     */
    String createOrder(CreateOrderIn in, Integer userId, String type);

    /**
     * 车主端 微信支付统一下单成功后，查看支付是否成功接口
     * @param orderSnIn
     * @param userId
     */
    void weiChatPayResult(StringIn orderSnIn, Integer userId);

    /**
     * 车主端 微信支付统一下单成功后,微信回调接口
     * @param orderResult
     */
    void weiChatPayCallBack(WeiChatCheckOrderResult orderResult);

    /**
     * 车主端 获取可用的最大优惠券
     * @param priceIn
     * @return
     */
    MaxUseableCouponOut getMaxUsableCoupon(PriceIn priceIn, Integer userId);

    /**
     * 取消订单
     * @param cancelOrderIn
     * @param userId
     * @return
     */
    void cancelOrder(CancelOrderIn cancelOrderIn, Integer userId);
}

