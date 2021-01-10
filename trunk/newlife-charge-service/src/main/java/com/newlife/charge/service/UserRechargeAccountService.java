package com.newlife.charge.service;


import com.newlife.charge.core.dto.out.UserNewLifeSpendLogChargeOut;
import com.newlife.charge.core.dto.out.UserNewLifeSpendLogRechargeOut;
import com.newlife.charge.core.dto.out.UserNewLifeSpendLogRefundOut;
import com.newlife.charge.core.dto.out.UserRechargeAccountOut;

/**
 * 车主端 用户充值记录-账户记录管理 service类
 * <p>
 */
public interface UserRechargeAccountService {

    /**
     * 车主端 用户账户列表展示
     * @param userId
     * @return
     */
    UserRechargeAccountOut rechargeAccountList(Integer userId);

    /**
     * 车主端 用户账户充值详情
     * @param id
     * @return
     */
    UserNewLifeSpendLogRechargeOut rechargeDetail(Integer id);

    /**
     * 车主端 用户用电详情
     * @param id
     * @return
     */
    UserNewLifeSpendLogChargeOut chargeDetail(Integer id);

    /**
     * 车主端 用户退款记录详情
     * @param id 流水统计id
     * @return
     */
    UserNewLifeSpendLogRefundOut refundDetail(Integer id);
}
