package com.newlife.charge.service;


import com.newlife.charge.core.domain.NewLifeSpendLog;
import com.newlife.charge.core.domain.exModel.NewLifeSpendLogEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.ChargeLogIn;
import com.newlife.charge.core.dto.in.GeneralStationSpendLogPageIn;
import com.newlife.charge.core.dto.in.StationNewLifeSpendLogPageIn;
import com.newlife.charge.core.dto.out.*;

import java.util.Date;
import java.util.List;

/**
 * 新活资金流水记录 service 类
 * <p>
 */
public interface NewLifeSpendLogService {

    /**
     * 分页或列表查询
     *
     * @param pageNo   页码
     * @param pageSize 页容量
     * @return
     */
    PageInfo<NewLifeSpendLogPageOut> page(int pageNo, int pageSize);

    /**
     * 分页或列表查询
     *
     * @param pageNo     页码
     * @param pageSize   页容量
     * @param userMobile 用户手机号
     * @param userType   用户类型
     * @param type       类型
     * @return
     */
    PageInfo<NewLifeSpendLogPageOut> page(int pageNo, int pageSize, String userMobile, Integer userType, Integer type);


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
     * @return
     */
    PageInfo<NewLifeSpendLogPageOut> page(int pageNo, int pageSize, String userMobile, Integer userType, Integer type,
                                          String stationName, String companyName, Date startTime, Date endTime);


    /**
     * 车主用户资金统计分页查询
     *
     * @param pageNo     页码
     * @param pageSize   页容量
     * @param userMobile 用户手机号
     * @param type       类型
     * @return
     */
    PageInfo<NewLifeUserSpendLogPageOut> carUserPage(int pageNo, int pageSize, String userMobile, Integer type);


    /**
     * 桩站资金统计分页查询
     *
     * @param pageIn
     * @return
     */
    PageInfo<GeneralStationSpendLogPageOut> stationPage(GeneralStationSpendLogPageIn pageIn);


    /**
     * 总后台 资金流水记录-详情
     *
     * @param id 主键ID
     * @return
     */
    NewLifeSpendLogInfoOut getInfo(Integer id);


    /**
     * 总后台 (车主)用户账户充值详情
     *
     * @param id 主键ID
     * @return
     */
    NewLifeSpendLogRechargeOut getCarUserRechargeInfo(Integer id);

    /**
     * 总后台 (车主)用户充电消费详情
     *
     * @param id 主键ID
     * @return
     */
    NewLifeSpendLogChargeOut getCarUserChargeInfo(Integer id);

    /**
     * 列表查询 用户充电消费资金记录
     *
     * @param stationId 桩站ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    List<NewLifeSpendLogEx> listCarUserCharge(Integer stationId, Date startTime, Date endTime);


    /**
     * 查询某时间段内卖电账单数量
     * 用于判定卖电账单定时任务是否需要执行
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    Integer countSellElectricBill(Date startTime, Date endTime);


    /**
     * 总后台 (车主)用户余额退款详情
     *
     * @param id 主键ID
     * @return
     */
    NewLifeSpendLogRefundOut getCarUserRefundInfo(Integer id);


    /**
     * 总后台 桩站卖电账单详情
     *
     * @param id 主键ID
     * @return
     */
    NewLifeSpendLogBillOut getStationIncomeInfo(Integer id);

    /**
     * 总后台 服务费佣金详情
     *
     * @param id 主键ID
     * @return
     */
    NewLifeSpendLogCommissionOut getCommissionInfo(Integer id);

    /**
     * @Description: 桩站后台 分页或列表查询
     * @Author: Linzq
     * @CreateDate: 2019/5/16 0016 15:46
     */
    PageInfo<NewLifeUserSpendLogPageOut> stationPageList(StationNewLifeSpendLogPageIn pageIn);


    /**
     * 保存
     *
     * @param newLifeSpendLog
     */
    int save(NewLifeSpendLog newLifeSpendLog);


    /**
     * @Description: 首页查询当日 卖电流水订单
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 17:19
     */
    List<NewLifeUserSpendLogPageOut> getDaySpendLog(StationNewLifeSpendLogPageIn pageIn);

    /**
     * @Description: 查询卖电账单余额
     * @Author: Linzq
     * @CreateDate:  2019/6/6 0006 16:04
     */
    List<NewLifeSpendLog> stationGainAmount(Integer type, Integer stationId);

}
