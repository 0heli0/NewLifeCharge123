package com.newlife.charge.service.impl;


import com.google.common.collect.Lists;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.core.domain.NewLifeSpendLog;
import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.NewLifeSpendLogMapper;
import com.newlife.charge.dao.UserMapper;
import com.newlife.charge.service.UserRechargeAccountService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 车主端 用户充值记录-账户记录管理service类
 *
 */
@Service
@Transactional
public class UserRechargeAccountServiceImpl implements UserRechargeAccountService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NewLifeSpendLogMapper newLifeSpendLogMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public UserRechargeAccountOut rechargeAccountList(Integer userId) {

        User user = this.userMapper.get(userId);

        if(user == null){
            throw new BizException(ERROR.NOT_FOUND_USER);
        }

        List<NewLifeSpendLog> spendLog = this.newLifeSpendLogMapper.getListByUser(userId);

        List<UserRechargeLogOut> logOut = Lists.newArrayList();

        if(spendLog != null){
            logOut = BeanMapper.mapList(spendLog, UserRechargeLogOut.class);
        }

        return new UserRechargeAccountOut(user.getBalance(),logOut);
    }

    @Override
    public UserNewLifeSpendLogRechargeOut rechargeDetail(Integer id) {

        NewLifeSpendLogRechargeOut result = this.newLifeSpendLogMapper.getRechargeInfo(id);

        UserNewLifeSpendLogRechargeOut out = null;

        if(result != null){
            out = dozer.map(result, UserNewLifeSpendLogRechargeOut.class);
        }
        return out;
    }

    @Override
    public UserNewLifeSpendLogChargeOut chargeDetail(Integer id) {

        NewLifeSpendLogChargeOut result = this.newLifeSpendLogMapper.getChargeInfo(id);

        UserNewLifeSpendLogChargeOut out = null;

        if(result != null){
            out = dozer.map(result, UserNewLifeSpendLogChargeOut.class);
        }
        return out;
    }

    @Override
    public UserNewLifeSpendLogRefundOut refundDetail(Integer id) {

        NewLifeSpendLogRefundOut result = this.newLifeSpendLogMapper.getRefundInfo(id);

        UserNewLifeSpendLogRefundOut out = null;

        if(result != null){
            out = dozer.map(result, UserNewLifeSpendLogRefundOut.class);
            out.setTotalPrice(out.getAmount());
            // 计算退款后金额和当前金额是否相等
            int flag = -1;
            if(result.getType() == NewLifeSpendLogTypeEnum.USER_RECHARGE.getValue()){
                flag = out.getUserNowBalance().compareTo(out.getAmount().add(out.getUserOldBalance()));
            }else {
                flag = out.getUserNowBalance().compareTo(out.getUserOldBalance().subtract(out.getAmount()));
            }
            if(flag != 0){
                throw new BizException(ERROR.USER_BALANCE_ERROR);
            }

            if(result.getCouponPrice() == null){
                out.setCouponPrice(new BigDecimal(0));
            }

        }
        return out;
    }
}
