package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.ChargeLogMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.ChargeLog;
import com.newlife.charge.core.domain.exModel.ChargeLogEx;
import com.newlife.charge.core.dto.in.ChargeLogIn;
import com.newlife.charge.core.dto.out.GeneralStationSpendLogPageOut;
import com.newlife.charge.core.dto.out.StationChargeOut;
import com.newlife.charge.dao.common.CrudRepository;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ChargeLogMapper extends CrudRepository<ChargeLog> {


    /**
     * 总后台 桩站卖电流水详情
     * @param id 卖电记录主键ID
     * @return
     */
    StationChargeOut getStationChargeInfo(@Param("id") Integer id);


    /**
     * 桩站卖电流水分页或列表查询
     *
     * @param userMobile  车主用户手机号
     * @param stationName 桩站名称
     * @param companyName 公司名称
     * @param startTime   开始时间
     * @param endTime     结束时间
     * @return
     */
    Page<GeneralStationSpendLogPageOut> stationPage(@Param("userMobile") String userMobile,
                                                    @Param("stationName") String stationName,
                                                    @Param("companyName") String companyName,
                                                    @Param("startTime") Date startTime,
                                                    @Param("endTime") Date endTime);

    /**
     * @Description: 首页查询当日充电账单
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 17:19
     */
    List<StationChargeOut> getDayChargeLog(ChargeLogIn chargeLogIn);

    /**
     * @Description: 桩站后台 用户账单列表
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 17:19
     */
    Page<ChargeLog> page(ChargeLogIn chargeLogIn);

    /**
     * @Description: 桩站后台 用户账单详情
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 17:19
     */
    ChargeLogEx getInfo(@Param("id") Integer id);

    /**
     * 根据订单编号查询
     * @param orderSn
     * @return
     */
    ChargeLog getByOrderSn(@Param("orderSn")String orderSn);

    /**
     * 根据用户id查找
     * @param userId
     * @return
     */
    ChargeLog getByUserId(@Param("userId")Integer userId);

    /**
     * 根据参数获取
     * @param userId 用户id
     * @param orderSn 订单号
     * @param orderId 订单id
     * @param status 状态
     * @return
     */
    List<ChargeLog> getByParams(@Param("userId")Integer userId,
                                @Param("orderSn")String orderSn,
                                @Param("orderId")Integer orderId,
                                @Param("status")Integer status);

    /**
     * 根据参数查找单个
     * @param parseInt
     * @param gunId
     * @param status
     * @return
     */
    ChargeLog getInfoByParams(@Param("userId")Integer parseInt,
                              @Param("gunId")Integer gunId,
                              @Param("status") Integer status);

    /**
     * 通过用户手机号关联查询记录
     * @param mobile 用户手机号
     * @return
     */
    ChargeLog getByMobile(String mobile);
}