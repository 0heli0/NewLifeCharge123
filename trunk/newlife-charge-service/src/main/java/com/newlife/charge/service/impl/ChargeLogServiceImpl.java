/**
 * Author: zhengyou
 * Date:   2018/12/11 16:03
 * Descripition:
 */
package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.core.domain.ChargeLog;
import com.newlife.charge.core.domain.CompanyInfo;
import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.domain.exModel.ChargeLogEx;
import com.newlife.charge.core.domain.exModel.StationInfoEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.ChargeLogIn;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.ClientChargeTypeEnum;
import com.newlife.charge.dao.ChargeLogMapper;
import com.newlife.charge.service.ChargeLogService;
import com.newlife.charge.service.StationInfoService;
import com.newlife.charge.service.StationTimePriceService;
import com.newlife.charge.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class ChargeLogServiceImpl implements ChargeLogService {
    @Autowired
    private ChargeLogMapper mapper;


    @Autowired
    private UserService userService;

    @Autowired
    private StationInfoService stationInfoService;

    @Autowired
    private StationTimePriceService stationTimePriceService;

    @Autowired
    private Mapper dozer;

    @Override
    public PageInfo<GeneralStationSpendLogPageOut> stationPage(int pageNo, int pageSize, String userMobile, String stationName, String companyName, Date startTime, Date endTime) {
        Page<GeneralStationSpendLogPageOut> page = this.mapper.stationPage(userMobile,  stationName, companyName, startTime, endTime);

        return new PageInfo<>(pageNo, pageSize, page.getTotal(), page.getResult());
    }

    @Override
    public String getStationChargePrices(Integer stationId, Date chargeStartTime, Date chargeEndTime) {


            //处理涉及到阶段电价charge_prices
            Set<BigDecimal> chargePricesSet = new HashSet<>();
            StationInfoEx stationInfoEx = this.stationInfoService.getById(stationId);
            BigDecimal chargePriceBase = null;//桩站基础电价
            if (stationInfoEx != null) {
                chargePriceBase = stationInfoEx.getChargePrice();
                chargePriceBase = chargePriceBase.setScale(4, BigDecimal.ROUND_UP);
                chargePricesSet.add(chargePriceBase);
            }
            List<GeneralStationTimePriceOut> timePriceOutList = this.stationTimePriceService.getByStationId(stationId);
            if (chargeEndTime != null && chargePriceBase != null && timePriceOutList != null && timePriceOutList.size() > 0) {
                Iterator<GeneralStationTimePriceOut> it = timePriceOutList.iterator();
                GeneralStationTimePriceOut priceOut;
                while (it.hasNext()) {
                    priceOut = it.next();
                    Time timeBegin = priceOut.getTimeBegin();
                    //判断充电时段是否与某时段价格有交集
                    if (timeBegin.getTime() <= chargeEndTime.getTime()) {
                        BigDecimal currentPrice = chargePriceBase.multiply(new BigDecimal(100 + priceOut.getIncrease()));
                        currentPrice = currentPrice.setScale(4, BigDecimal.ROUND_HALF_UP);
                        chargePricesSet.add(currentPrice);
                    }
                }
            }
        return StringUtils.join(chargePricesSet, "、");
    }

    @Override
    public StationChargeOut getStationChargeInfo(Integer id) {
        StationChargeOut info = this.mapper.getStationChargeInfo(id);

        if (info != null) {

            //桩站主账号
            User stationMainUser = this.userService.getStationMainUser(info.getStationId());
            if(stationMainUser!=null){
                info.setStationUserMobile(stationMainUser.getMobile());
            }

            Integer clientChargeType = info.getClientChargeType();
            if (clientChargeType != null) {
                info.setClientChargeTypeName(ClientChargeTypeEnum.getDescriptionByValue(clientChargeType));
            }

            //处理折扣率rate=实付金额/应付金额
            String rate = "";
            BigDecimal priceReal = info.getPriceReal();
            BigDecimal amount = info.getAmount();
            if (amount != null && priceReal != null && BigDecimal.ZERO.compareTo(amount)!=0) {
                BigDecimal divide = priceReal.divide(amount, 2, BigDecimal.ROUND_DOWN);
                rate = divide.multiply(new BigDecimal(100)).toString() + "%";
            }
            info.setRate(rate);

            //处理充电时长charging_time
            Date beginTime = info.getBeginTime();
            Date endTime = info.getEndTime();
            if (beginTime != null && endTime != null) {
                String chargingTime = DateUtils.getDistanceDateTimeStrOfTwoDate(beginTime, endTime, TimeUnit.MINUTES);
                info.setChargingTime(chargingTime);
            }

        }


        return info;
    }

    @Override
    public List<StationChargeOut> getDayChargeLog(ChargeLogIn chargeLogIn) {

        return mapper.getDayChargeLog(chargeLogIn);
    }

    @Override
    public PageInfo<StationChargeOut> chargeLogPage(ChargeLogIn chargeLogIn) {

        PageHelper.startPage(chargeLogIn.getPageNo(), chargeLogIn.getPageSize());
        Page<ChargeLog> page = this.mapper.page(chargeLogIn);
        List<StationChargeOut> outList = BeanMapper.mapList(page.getResult(), StationChargeOut.class);
        if(Collections3.isNotEmpty(outList)) {
            for(StationChargeOut stationChargeOut : outList) {
                stationChargeOut.setClientChargeTypeName(ClientChargeTypeEnum.getDescriptionByValue(stationChargeOut.getClientChargeType()));
                //充电时长返回毫秒数
                long chargeTime = stationChargeOut.getEndTime().getTime() - stationChargeOut.getBeginTime().getTime();
                long minutes = chargeTime / 1000 / 60;
                stationChargeOut.setChargingTime(String.valueOf(minutes));
            }
        }
        return new PageInfo<>(chargeLogIn.getPageNo(), chargeLogIn.getPageSize(), page.getTotal(), outList);
    }

    @Override
    public ChargeLogOut getInfo(Integer id) {

        ChargeLogEx chargeLog = mapper.getInfo(id);
        if(chargeLog != null) {
            ChargeLogOut chargeLogOut = dozer.map(chargeLog, ChargeLogOut.class);
            //充电时长返回毫秒数
            long chargeTime = chargeLogOut.getEndTime().getTime() - chargeLogOut.getBeginTime().getTime();
            chargeLogOut.setChargeTime(chargeTime);
            chargeLogOut.setClientChargeTypeName(ClientChargeTypeEnum.getDescriptionByValue(chargeLogOut.getClientChargeType()));
            return chargeLogOut;
        }
        return null;
    }
}
