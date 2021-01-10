package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.NewLifeSpendLog;
import com.newlife.charge.core.domain.exModel.NewLifeSpendLogEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.GeneralStationSpendLogPageIn;
import com.newlife.charge.core.dto.in.StationNewLifeSpendLogPageIn;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.*;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.dao.NewLifeSpendLogMapper;
import com.newlife.charge.service.ChargeLogService;
import com.newlife.charge.service.NewLifeSpendLogService;
import com.newlife.charge.service.StationInfoService;
import com.newlife.charge.service.StationTimePriceService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
public class NewLifeSpendLogServiceImpl implements NewLifeSpendLogService {

    @Autowired
    private NewLifeSpendLogMapper mapper;


    @Autowired
    private StationInfoService stationInfoService;

    @Autowired
    private StationTimePriceService stationTimePriceService;

    @Autowired
    private ChargeLogService chargeLogService;


    @Autowired
    private Mapper dozer;

    @Override
    public PageInfo<NewLifeSpendLogPageOut> page(int pageNo, int pageSize) {
        return this.page(pageNo, pageSize, null, null, null);
    }

    @Override
    public PageInfo<NewLifeSpendLogPageOut> page(int pageNo, int pageSize, String userMobile, Integer userType, Integer type) {
        return this.page(pageNo, pageSize, userMobile, userType, type, null, null, null, null);
    }


    @Override
    public PageInfo<NewLifeSpendLogPageOut> page(int pageNo, int pageSize, String userMobile, Integer userType, Integer type, String stationName, String companyName, Date startTime, Date endTime) {
        PageHelper.startPage(pageNo, pageSize);

        Page<NewLifeSpendLogEx> page = this.mapper.page(userMobile, userType, type, stationName, companyName, startTime, endTime, null);
        List<NewLifeSpendLogEx> list = page.getResult();
        List<NewLifeSpendLogPageOut> outList = BeanMapper.mapList(list, NewLifeSpendLogPageOut.class);
        if (outList != null && outList.size() > 0) {
            for (NewLifeSpendLogPageOut out : outList) {
                out.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(out.getType()));
                out.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(out.getTypeRemark()));
            }
        }
        return new PageInfo<>(pageNo, pageSize, page.getTotal(), outList);
    }

    @Override
    public PageInfo<NewLifeUserSpendLogPageOut> carUserPage(int pageNo, int pageSize, String userMobile, Integer type) {
        PageHelper.startPage(pageNo, pageSize);

        Page<NewLifeSpendLogEx> page = this.mapper.page(userMobile, UserTypeEnum.CAR.getValue(), type, null, null, null, null, null);
        List<NewLifeSpendLogEx> list = page.getResult();
        List<NewLifeUserSpendLogPageOut> outList = BeanMapper.mapList(list, NewLifeUserSpendLogPageOut.class);
        if (outList != null && outList.size() > 0) {
            for (NewLifeUserSpendLogPageOut out : outList) {
                out.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(out.getType()));
                out.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(out.getTypeRemark()));
            }
        }
        return new PageInfo<>(pageNo, pageSize, page.getTotal(), outList);
    }


    @Override
    public PageInfo<GeneralStationSpendLogPageOut> stationPage(GeneralStationSpendLogPageIn pageIn) {
        List<GeneralStationSpendLogPageOut> outList = null;
        long total = 0;
        //卖电账单查询和卖电流水查询调用不同的接口
        if (NewLifeSpendLogTypeEnum.SELL_ELECTRIC_BILL.getValue() == pageIn.getType()) {
            //卖电账单查询，从资金流水记录来
            PageHelper.startPage(pageIn.getPageNo(), pageIn.getPageSize());
            Page<NewLifeSpendLogEx> page = this.mapper.page(pageIn.getUserMobile(), UserTypeEnum.STATION_MAIN.getValue(),
                    pageIn.getType(), pageIn.getStationName(), pageIn.getCompanyName(), pageIn.getStartTime(), pageIn.getEndTime(), null);
            List<NewLifeSpendLogEx> list = page.getResult();
            outList = BeanMapper.mapList(list, GeneralStationSpendLogPageOut.class);
            if (outList != null && outList.size() > 0) {
                for (GeneralStationSpendLogPageOut out : outList) {
                    out.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(out.getType()));
                    out.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(out.getTypeRemark()));
                }
            }
            total = page.getTotal();
        } else if(41 == pageIn.getType()) {
            //卖电流水查询，从充电记录来
            PageInfo<GeneralStationSpendLogPageOut> chargeLogPageInfo = this.chargeLogService.stationPage(pageIn.getPageNo(), pageIn.getPageSize(),
                    pageIn.getUserMobile(), pageIn.getStationName(), pageIn.getCompanyName(), pageIn.getStartTime(), pageIn.getEndTime());
            outList = chargeLogPageInfo.getData();
            total = chargeLogPageInfo.getTotalCount();
        }else{
            throw new BizException("类型错误");
        }
        return new PageInfo<>(pageIn.getPageNo(), pageIn.getPageSize(), total, outList);
    }

    @Override
    public NewLifeSpendLogInfoOut getInfo(Integer id) {
        NewLifeSpendLogInfoOut newLifeSpendLogInfoOut = null;
        NewLifeSpendLog newLifeSpendLog = this.mapper.get(id);
        if (newLifeSpendLog != null) {
            newLifeSpendLogInfoOut = new NewLifeSpendLogInfoOut();
            Integer type = newLifeSpendLog.getType();
            newLifeSpendLogInfoOut.setType(type);

            if (type == NewLifeSpendLogTypeEnum.USER_RECHARGE.getValue()) {
                //1.账户充值
                NewLifeSpendLogRechargeOut rechargeInfo = this.getCarUserRechargeInfo(id);
                newLifeSpendLogInfoOut.setRechargeOut(rechargeInfo);
            } else if (type == NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getValue()) {
                //2.充电消费
                NewLifeSpendLogChargeOut chargeInfo = this.getCarUserChargeInfo(id);
                newLifeSpendLogInfoOut.setChargeOut(chargeInfo);
            } else if (type == NewLifeSpendLogTypeEnum.BALANCE_REFUND.getValue()) {
                //3.余额退款
                NewLifeSpendLogRefundOut refundInfo = this.getCarUserRefundInfo(id);
                newLifeSpendLogInfoOut.setRefundOut(refundInfo);
            } else if (type == NewLifeSpendLogTypeEnum.SELL_ELECTRIC_BILL.getValue()) {
                //4.卖电账单
                NewLifeSpendLogBillOut billOut = this.getStationIncomeInfo(id);
                newLifeSpendLogInfoOut.setBillOut(billOut);
            } else if (type == NewLifeSpendLogTypeEnum.SERVICE_FEE_COMMISSION.getValue()) {
                //5.服务费佣金
                NewLifeSpendLogCommissionOut commissionOut = this.getCommissionInfo(id);
                newLifeSpendLogInfoOut.setCommissionOut(commissionOut);
            } else {
                //其他未知
            }

        }
        return newLifeSpendLogInfoOut;
    }

    @Override
    public NewLifeSpendLogRechargeOut getCarUserRechargeInfo(Integer id) {
        NewLifeSpendLogRechargeOut info = this.mapper.getRechargeInfo(id);
        if (info != null) {
            Integer type = info.getType();
            if (type != null) {
                info.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(type));
            }
            Integer typeRemark = info.getTypeRemark();
            if (typeRemark != null) {
                info.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(typeRemark));
            }
            Integer payType = info.getPayType();
            if (payType != null) {
                info.setPayTypeName(PayTypeEnum.getDescriptionByValue(payType));
            }

            //处理折扣率rate=实付金额/应付金额
            String rate = "";
            BigDecimal priceReal = info.getTotalPrice();
            BigDecimal amount = info.getAmount();
            if (amount != null && priceReal != null && BigDecimal.ZERO.compareTo(amount)!=0) {
                BigDecimal divide = priceReal.divide(amount, 2, BigDecimal.ROUND_DOWN);
                rate = divide.multiply(new BigDecimal(100)).toString() + "%";
            }
            info.setRate(rate);

        }
        return info;
    }

    @Override
    public Integer countSellElectricBill(Date startTime, Date endTime) {
        return this.mapper.count(NewLifeSpendLogTypeEnum.SELL_ELECTRIC_BILL.getValue(),startTime,endTime);
    }

    @Override
    public List<NewLifeSpendLogEx> listCarUserCharge(Integer stationId, Date startTime, Date endTime) {
        Page<NewLifeSpendLogEx> page = this.mapper.page(null, null, NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getValue(),
                null, null, startTime, endTime, stationId);
        return page.getResult();
    }

    @Override
    public NewLifeSpendLogChargeOut getCarUserChargeInfo(Integer id) {
        NewLifeSpendLogChargeOut info = this.mapper.getChargeInfo(id);

        if (info != null) {
            Integer type = info.getType();
            if (type != null) {
                info.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(type));
            }
            Integer typeRemark = info.getTypeRemark();
            if (typeRemark != null) {
                info.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(typeRemark));
            }
            Integer payType = info.getPayType();
            if (payType != null) {
                info.setPayTypeName(PayTypeEnum.getDescriptionByValue(payType));
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
    public NewLifeSpendLogRefundOut getCarUserRefundInfo(Integer id) {
        NewLifeSpendLogRefundOut info = this.mapper.getRefundInfo(id);
        if (info != null) {
            //退款时优惠券减免金额为0
            info.setCouponPrice(BigDecimal.ZERO);
            Integer type = info.getType();
            if (type != null) {
                info.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(type));
            }
            Integer typeRemark = info.getTypeRemark();
            if (typeRemark != null) {
                info.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(typeRemark));
            }
            Integer payType = info.getPayType();
            if (payType != null) {
                info.setPayTypeName(PayTypeEnum.getDescriptionByValue(payType));
            }

        }
        return info;
    }

    @Override
    public NewLifeSpendLogBillOut getStationIncomeInfo(Integer id) {
        NewLifeSpendLogBillOut info = this.mapper.getStationIncomeInfo(id);
        if (info != null) {
            Integer type = info.getType();
            if (type != null) {
                info.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(type));
            }
            Integer typeRemark = info.getTypeRemark();
            if (typeRemark != null) {
                info.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(typeRemark));
            }
        }
        return info;
    }

    @Override
    public NewLifeSpendLogCommissionOut getCommissionInfo(Integer id) {
        NewLifeSpendLogCommissionOut info = this.mapper.getCommissionInfo(id);
        if (info != null) {
            Integer type = info.getType();
            if (type != null) {
                info.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(type));
            }
            Integer typeRemark = info.getTypeRemark();
            if (typeRemark != null) {
                info.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(typeRemark));
            }
        }
        return info;
    }

    @Override
    public PageInfo<NewLifeUserSpendLogPageOut> stationPageList(StationNewLifeSpendLogPageIn pageIn) {

        PageHelper.startPage(pageIn.getPageNo(), pageIn.getPageSize());

        Page<NewLifeSpendLogEx> page = this.mapper.page(null, null,
                pageIn.getType(), null, null, pageIn.getStartTime(), pageIn.getEndTime(), pageIn.getStationId());
        List<NewLifeSpendLogEx> list = page.getResult();
        List<NewLifeUserSpendLogPageOut> outList = BeanMapper.mapList(list, NewLifeUserSpendLogPageOut.class);
        if (outList != null && outList.size() > 0) {
            for (NewLifeUserSpendLogPageOut out : outList) {
                out.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(out.getType()));
                out.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(out.getTypeRemark()));
            }
        }
        return new PageInfo<>(pageIn.getPageNo(), pageIn.getPageSize(), page.getTotal(), outList);
    }

    @Override
    public int save(NewLifeSpendLog newLifeSpendLog) {
        return this.mapper.insert(newLifeSpendLog);
    }

    @Override
    public List<NewLifeUserSpendLogPageOut> getDaySpendLog(StationNewLifeSpendLogPageIn pageIn) {
        List<NewLifeSpendLogEx> list = this.mapper.getDaySpendLog(pageIn);
        List<NewLifeUserSpendLogPageOut> outList = BeanMapper.mapList(list, NewLifeUserSpendLogPageOut.class);
        if (outList != null && outList.size() > 0) {
            for (NewLifeUserSpendLogPageOut out : outList) {
                out.setTypeName(NewLifeSpendLogTypeEnum.getDescriptionByValue(out.getType()));
                out.setTypeRemarkName(NewLifeSpendLogTypeRemarkEnum.getDescriptionByValue(out.getTypeRemark()));
            }
        }
        return outList;
    }

    @Override
    public List<NewLifeSpendLog> stationGainAmount(Integer type, Integer stationId) {
        return mapper.stationGainAmount(type, stationId);
    }
}
