package com.newlife.charge.service;


import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.ChargeLogIn;
import com.newlife.charge.core.dto.out.ChargeLogOut;
import com.newlife.charge.core.dto.out.GeneralStationSpendLogPageOut;
import com.newlife.charge.core.dto.out.StationChargeOut;

import java.util.Date;
import java.util.List;

/**
 * 充电记录 service 类
 * <p>
 */
public interface ChargeLogService {


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
    PageInfo<GeneralStationSpendLogPageOut> stationPage(int pageNo, int pageSize, String userMobile, String stationName,
                                                        String companyName, Date startTime, Date endTime);


    /**
     * 总后台 桩站卖电流水详情
     *
     * @param id 卖电记录主键ID
     * @return
     */
    StationChargeOut getStationChargeInfo(Integer id);


    /**
     * 获取某充电时段内涉及到的桩站那店家
     *
     * @param stationId 桩站ID
     * @param chargeStartTime 充电开始时间
     * @param chargeEndTime   充电结束时间
     * @return
     */
    String getStationChargePrices(Integer stationId, Date chargeStartTime, Date chargeEndTime);


    /**
     * @Description: 首页查询当日充电账单
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 17:19
     */
    List<StationChargeOut> getDayChargeLog(ChargeLogIn chargeLogIn);

    /**
     * @Description: 桩站后台 用户账单列表
     * @Author: Linzq
     * @CreateDate:  2019/5/16 0016 13:39
     */
    PageInfo<StationChargeOut> chargeLogPage(ChargeLogIn chargeLogIn);

    /**
     * @Description: 桩站后台 查询用电账单详情
     * @Author: Linzq
     * @CreateDate:  2019/5/16 0016 14:24
     */
    ChargeLogOut getInfo(Integer id);
}
