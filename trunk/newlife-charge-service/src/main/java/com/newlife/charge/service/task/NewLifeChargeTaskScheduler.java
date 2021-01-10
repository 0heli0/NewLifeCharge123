package com.newlife.charge.service.task;

import com.newlife.charge.common.DateUtils;
import com.newlife.charge.common.TelCharg.TelChargApi;
import com.newlife.charge.core.constant.Constants;
import com.newlife.charge.core.domain.NewLifeSpendLog;
import com.newlife.charge.core.domain.StationInfo;
import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.domain.exModel.NewLifeSpendLogEx;
import com.newlife.charge.core.domain.exModel.StationInfoEx;
import com.newlife.charge.core.dto.out.StationCompanyInfoOut;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeEnum;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeRemarkEnum;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeStrEnum;
import com.newlife.charge.service.CompanyInfoService;
import com.newlife.charge.service.NewLifeSpendLogService;
import com.newlife.charge.service.StationInfoService;
import com.newlife.charge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 定时任务
 */
@Service
public class NewLifeChargeTaskScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewLifeChargeTaskScheduler.class);

    @Autowired
    private NewLifeSpendLogService newLifeSpendLogService;

    @Autowired
    private StationInfoService stationInfoService;

    @Autowired
    private CompanyInfoService companyInfoService;


    @Autowired
    private UserService userService;

    /**
     * 桩站卖电账单、服务费佣金，每天早上10点处理
     * 条件判断：
     * 如果已存在昨天的服务费佣金，则任务结束
     * 业务：
     * 1.桩站账户余额增加
     * 2.统计昨天各桩站卖电可得金额--充电消费-桩站可得求和
     * 3.统计昨天各桩站平台可得服务费--充电消费-新活可得求和
     */
    @Scheduled(cron = "0 0 10 * * ?")
    @Transactional
    public void commissionSettlement() {
        LOGGER.info("===开始执行定时任务：桩站卖电账单、服务费佣金===");


        Date yesterday = DateUtils.getYesterday();
        Date yesterdayStartTime = DateUtils.formatDateToDayStart(yesterday);
        Date yesterdayEndTime = DateUtils.formatDateToDayEnd(yesterday);

        //条件判断
        // 1.如果已存在昨天的卖电账单，则任务结束
        Integer count = this.newLifeSpendLogService.countSellElectricBill(yesterdayStartTime, yesterdayEndTime);
        if (count > 0) {
            LOGGER.info("===昨日卖电账单已生成，任务结束===");
            LOGGER.info("===结束执行定时任务：桩站卖电账单、服务费佣金===");
            return;
        }

        //查询昨天所有的充电消费资金记录
        List<NewLifeSpendLogEx> list = this.newLifeSpendLogService.listCarUserCharge(null, yesterdayStartTime, yesterdayEndTime);
        Map<Integer, BigDecimal[]> stationLogMap = new HashMap<>();//某桩站和其昨天的桩站可得金额、平台可得服务费金额
        if (list != null && list.size() > 0) {
            Iterator<NewLifeSpendLogEx> it = list.iterator();
            NewLifeSpendLogEx log;
            BigDecimal[] gainAmount = null;
            while (it.hasNext()) {
                log = it.next();
                Integer stationId = log.getStationId();
                gainAmount = stationLogMap.get(stationId);
                //桩站可得金额（包含电费和服务费，如果优惠券是桩站发的，则已经扣除优惠券金额）
                BigDecimal stationGainAmount = log.getStationGainAmount() == null ? BigDecimal.ZERO : log.getStationGainAmount();
                //新活可得金额（如果优惠券是平台发的，则已经扣除优惠券金额）
                BigDecimal newLifeGainAmount = log.getNewLifeGainAmount() == null ? BigDecimal.ZERO : log.getNewLifeGainAmount();
                //桩站可得电费金额
                BigDecimal stationGainAmountCharge = log.getStationGainAmountCharge()==null?BigDecimal.ZERO:log.getStationGainAmountCharge();
                //桩站可得服务费金额
                BigDecimal stationGainAmountService = log.getStationGainAmountService()==null?BigDecimal.ZERO:log.getStationGainAmountService();

                if (gainAmount == null) {
                    gainAmount = new BigDecimal[]{stationGainAmount, newLifeGainAmount,stationGainAmountCharge,stationGainAmountService};
                } else {
                    gainAmount[0] = stationGainAmount.add(gainAmount[0]);//桩站可得金额
                    gainAmount[1] = newLifeGainAmount.add(gainAmount[1]);//新活可得金额
                    gainAmount[2] = stationGainAmountCharge.add(gainAmount[2]);//桩站可得电费金额
                    gainAmount[3] = stationGainAmountService.add(gainAmount[3]);//桩站可得服务费金额
                }
                stationLogMap.put(stationId, gainAmount);
            }
        }

        if (stationLogMap.size() > 0) {
            Set<Integer> keySet = stationLogMap.keySet();
            Iterator<Integer> it = keySet.iterator();
            while (it.hasNext()) {
                Date nowDate = new Date();
                Integer stationId = it.next();
                StationInfoEx stationInfoEx = this.stationInfoService.getById(stationId);//桩站信息
                User stationMainUser = userService.getStationMainUser(stationId);//桩站主账号
//                StationCompanyInfoOut companyInfoOut = companyInfoService.getInfoByStationId(stationId);//公司信息

                BigDecimal[] gainAmount = stationLogMap.get(stationId);
                BigDecimal stationGainAmount = gainAmount[0];
                BigDecimal newLifeGainAmount = gainAmount[1];
                BigDecimal stationGainAmountCharge = gainAmount[2];
                BigDecimal stationGainAmountService = gainAmount[3];

                //1.桩站用户余额增加
                StationInfo stationInfo = new StationInfo();
                BigDecimal oldUseBalance = stationInfoEx.getUseBalance() == null ? BigDecimal.ZERO : stationInfoEx.getUseBalance();
                BigDecimal nowUseBalance = oldUseBalance.add(stationGainAmount);
                stationInfo.setId(stationId);
                stationInfo.setUseBalance(nowUseBalance);
                this.stationInfoService.updateStationInfo(stationInfo);

                //2.保存卖电账单
                NewLifeSpendLog billLog = new NewLifeSpendLog();
                billLog.setId(null);
                billLog.setMoneySn(TelChargApi.getNewOrderSn(NewLifeSpendLogTypeStrEnum.SELL_ELECTRIC_BILL.getDescription()));
                billLog.setOrderId(Constants.INTEGER_DEFAULT);//没有意义，作0处理
                billLog.setOrderSn(Constants.STRING_BLANK);//没有意义，作空处理
                billLog.setType(NewLifeSpendLogTypeEnum.SELL_ELECTRIC_BILL.getValue());
                billLog.setTypeRemark(NewLifeSpendLogTypeRemarkEnum.SELL_ELECTRIC_INCOME.getValue());
                billLog.setAmount(stationGainAmount);//桩站可得卖电总收入
                billLog.setUserId(stationMainUser.getId());//桩站主账号用户ID
                billLog.setUserType(stationMainUser.getUserType());
                billLog.setUserName(stationMainUser.getUserName());
                billLog.setUserMobile(stationMainUser.getMobile());
                billLog.setUserAvatarUrl(stationMainUser.getAvatarUrl());
                billLog.setUserOldBalance(BigDecimal.ZERO);//没有意义，作0处理
                billLog.setUserGainAmount(BigDecimal.ZERO);//没有意义，作0处理
                billLog.setUserNowBalance(BigDecimal.ZERO);//没有意义，作0处理
                billLog.setStationId(stationId);
                billLog.setStationName(stationInfoEx.getName());
                billLog.setCommissionRation(BigDecimal.ZERO);//没有意义，作0处理
                billLog.setServicePrice(BigDecimal.ZERO);//没有意义，作0处理
                billLog.setStationGainAmount(stationGainAmount);
                billLog.setStationGainAmountCharge(stationGainAmountCharge);
                billLog.setStationGainAmountService(stationGainAmountService);
                billLog.setStationOldNoCheckAmount(stationInfoEx.getNoCheckBalance());
                billLog.setStationNowNoCheckAmount(stationInfoEx.getNoCheckBalance());
                billLog.setStationOldBalance(oldUseBalance);
                billLog.setStationNowBalance(nowUseBalance);
                billLog.setNewLifeGainAmount(BigDecimal.ZERO);//没有意义，作0处理
                billLog.setCouponAmount(BigDecimal.ZERO);//没有意义，作0处理
                billLog.setCouponType(Constants.INTEGER_DEFAULT);//没有意义，作0处理
                billLog.setRemark("桩站卖电账单");
                billLog.setCreateTime(nowDate);

                this.newLifeSpendLogService.save(billLog);

                //2.保存新活可得服务费佣金
                NewLifeSpendLog serviceLog = new NewLifeSpendLog();
                serviceLog.setId(null);
                serviceLog.setMoneySn(TelChargApi.getNewOrderSn(NewLifeSpendLogTypeStrEnum.SERVICE_FEE_COMMISSION.getDescription()));
                serviceLog.setOrderId(Constants.INTEGER_DEFAULT);//没有意义，作0处理
                serviceLog.setOrderSn(Constants.STRING_BLANK);//没有意义，作空处理
                serviceLog.setType(NewLifeSpendLogTypeEnum.SERVICE_FEE_COMMISSION.getValue());
                serviceLog.setTypeRemark(NewLifeSpendLogTypeRemarkEnum.SERVICE_FEE_INCOME.getValue());
                serviceLog.setAmount(newLifeGainAmount);//平台可得服务费佣金
                serviceLog.setUserId(stationMainUser.getId());//桩站主账号用户ID
                serviceLog.setUserType(stationMainUser.getUserType());
                serviceLog.setUserName(stationMainUser.getUserName());
                serviceLog.setUserMobile(stationMainUser.getMobile());
                serviceLog.setUserAvatarUrl(stationMainUser.getAvatarUrl());
                serviceLog.setUserOldBalance(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setUserGainAmount(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setUserNowBalance(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setStationId(stationId);
                serviceLog.setStationName(stationInfoEx.getName());
                serviceLog.setCommissionRation(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setServicePrice(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setStationGainAmount(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setStationGainAmountCharge(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setStationGainAmountService(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setStationOldNoCheckAmount(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setStationNowNoCheckAmount(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setStationOldBalance(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setStationNowBalance(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setNewLifeGainAmount(newLifeGainAmount);//新活可得服务费佣金
                serviceLog.setCouponAmount(BigDecimal.ZERO);//没有意义，作0处理
                serviceLog.setCouponType(Constants.INTEGER_DEFAULT);//没有意义，作0处理
                serviceLog.setRemark("服务费佣金");
                serviceLog.setCreateTime(nowDate);
                this.newLifeSpendLogService.save(serviceLog);

            }
        }else{
            LOGGER.info("===昨日没有充电消费，任务结束===");

        }

        LOGGER.info("===结束执行定时任务：桩站卖电账单、服务费佣金===");

    }

}
