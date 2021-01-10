package com.newlife.charge.core.dto.out;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


/**
 * 用户使用时的优惠券 v1.0
 */


@Getter
@Setter
@ApiModel("用户使用时的优惠券")
public class UserUseCoupon {

    // 用户优惠券id
    private Integer id;

    // 优惠券id
    private Integer couponId;

    // 优惠券类型
    private Integer type;

    // 面额
    private BigDecimal price;

    // 门槛金额
    private BigDecimal thresholdPrice;

    @Override
    public String toString() {
        return "UserUseCoupon{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", type=" + type +
                ", price=" + price +
                ", thresholdPrice=" + thresholdPrice +
                '}';
    }
}