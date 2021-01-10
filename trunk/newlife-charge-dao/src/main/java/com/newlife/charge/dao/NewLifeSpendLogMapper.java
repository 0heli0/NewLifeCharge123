package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.NewLifeSpendLogMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.NewLifeSpendLog;
import com.newlife.charge.core.domain.exModel.NewLifeSpendLogEx;
import com.newlife.charge.core.dto.in.StationNewLifeSpendLogPageIn;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface NewLifeSpendLogMapper extends CrudRepository<NewLifeSpendLog> {


    /**
     * 统计数量
     *
     * @param type      类型
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    Integer count(@Param("type") Integer type, @Param("startTime") Date startTime, @Param("endTime") Date endTime);


    /**
     * 分页或列表查询
     *
     * @param userMobile  用户手机号
     * @param userType    用户类型
     * @param type        类型
     * @param stationName 桩站名称
     * @param companyName 公司名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @param stationId   桩站id
     * @return
     */
    Page<NewLifeSpendLogEx> page(@Param("userMobile") String userMobile, @Param("userType") Integer userType, @Param("type") Integer type,
                                 @Param("stationName") String stationName, @Param("companyName") String companyName,
                                 @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("stationId") Integer stationId);


    /**
     * 获取账户充值详情
     *
     * @param id 资金流水记录id
     * @return
     */
    NewLifeSpendLogRechargeOut getRechargeInfo(@Param("id") Integer id);


    /**
     * 获取充电消费详情
     *
     * @param id 资金流水记录id
     * @return
     */
    NewLifeSpendLogChargeOut getChargeInfo(@Param("id") Integer id);

    /**
     * 获取余额退款详情
     *
     * @param id 资金流水记录id
     * @return
     */
    NewLifeSpendLogRefundOut getRefundInfo(@Param("id") Integer id);

    /**
     * 获取卖电账单详情
     *
     * @param id 资金流水记录id
     * @return
     */
    NewLifeSpendLogBillOut getStationIncomeInfo(@Param("id") Integer id);

    /**
     * 获取服务费佣金详情
     *
     * @param id 资金流水记录id
     * @return
     */
    NewLifeSpendLogCommissionOut getCommissionInfo(@Param("id") Integer id);

    /**
     * 通过用户id查询资金流水列表
     *
     * @param userId
     * @return
     */
    List<NewLifeSpendLog> getListByUser(@Param("userId") Integer userId);

    /**
     * @Description: 首页查询当日 卖电流水订单
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 17:19
     */
    List<NewLifeSpendLogEx> getDaySpendLog(StationNewLifeSpendLogPageIn pageIn);

    /**
     * 根据用户和类型查找用户充值资金流水（只查了充值）
     * @param userId
     * @param type 充值类型（1.全额充值，2.使用了优惠券的充值）
     * @param chargeType 表中类型
     * @param chargeTypeRemark 表中类型备注
     * @return
     */
    List<NewLifeSpendLog> getChargeListByUser(@Param("userId")Integer userId,
                                              @Param("type")Integer type,
                                              @Param("chargeType") Integer chargeType,
                                              @Param("chargeTypeRemark") Integer chargeTypeRemark);


    /**
     * @Description: 查询桩站所有卖电账单
     * @Author: Linzq
     * @CreateDate:  2019/6/6 0006 16:06
     */
    List<NewLifeSpendLog> stationGainAmount(@Param("type")Integer type,@Param("stationId")Integer stationId);
}