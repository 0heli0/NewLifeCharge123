/**
 * Author: zhengyou
 * Date:   2018/12/11 16:03
 * Descripition:
 */
package com.newlife.charge.service.impl;

import com.google.common.collect.Lists;
import com.newlife.charge.common.CommonUtil;
import com.newlife.charge.core.domain.StationCouponCount;
import com.newlife.charge.core.domain.StationGunStatistics;
import com.newlife.charge.core.domain.StationHomeDetail;
import com.newlife.charge.core.domain.StationTimePrice;
import com.newlife.charge.core.dto.in.StationDetailIn;
import com.newlife.charge.core.dto.in.StationHomeListIn;
import com.newlife.charge.core.dto.out.StationHomeDetailOut;
import com.newlife.charge.core.dto.out.StationHomeListOut;
import com.newlife.charge.core.dto.out.StationHomeMapListOut;
import com.newlife.charge.core.enums.ClientChargeTypeEnum;
import com.newlife.charge.core.enums.GunStatusEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.StationClientGunMapper;
import com.newlife.charge.dao.StationHomeMapper;
import com.newlife.charge.dao.StationTimePriceMapper;
import com.newlife.charge.service.ClientHomeService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientHomeServiceImpl implements ClientHomeService {

    private Logger logger = LoggerFactory.getLogger(ClientHomeServiceImpl.class);

    @Autowired
    private StationHomeMapper stationHomeMapper;

    @Autowired
    private StationClientGunMapper stationClientGunMapper;

    @Autowired
    private StationTimePriceMapper stationTimePriceMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public List<StationHomeMapListOut> mapStation(StationHomeListIn listIn) {
        if(listIn.getDistance()<(10*1000)){
            listIn.setDistance(10*1000);
        }
        List<StationHomeMapListOut> result = this.stationHomeMapper.mapStation(listIn);
        result.forEach(item ->{
            Double distence = CommonUtil.distance(item.getLng(),item.getLat(),listIn.getLng(),listIn.getLat())*1000;
            item.setDistance(distence.intValue());
        });

        return result;
    }

    @Override
    public List<StationHomeListOut> stationList(StationHomeListIn listIn) {

        if(listIn.getDistance()<(10*1000)){
            listIn.setDistance(10*1000);
        }
        List<StationHomeListOut>  list = this.stationHomeMapper.stationList(listIn);
        List<Integer> stationIds = Lists.newArrayList();
        list.forEach(item ->
                {
                    Double distance = CommonUtil.distance(item.getLng(),item.getLat(),listIn.getLng(),listIn.getLat())*1000;
                    item.setDistance(distance.intValue());
                    stationIds.add(item.getStationId());
                    // 查询当前时段电价
                    StationTimePrice timePrice = stationTimePriceMapper.getNowTimePrice(item.getStationId());
                    Integer increase = 0;
                    // 计算电价
                    if(timePrice != null){
                        increase = timePrice.getIncrease();
                    }
                    BigDecimal price = CommonUtil.calculatePrice(item.getChargePrice(),item.getServicePrice(),increase);
                    item.setPrice(price);


                }

        );
        // 查询桩站是否有未过期的优惠券
        if(stationIds.size()>0){
            List<StationCouponCount> counts = this.stationHomeMapper.getStationCouponCountByIds(stationIds);
            counts.forEach(item1 ->{
                list.forEach(item2 ->{
                    if(item1.getStationId().equals(item2.getStationId()) && item1.getTotal() > 0){
                        item2.setIsCoupon(1);
                    }
                });
            });
        }

        // 查询是否有未过期的通用充电优惠券
        Integer usefulCouponCount = this.stationHomeMapper.getUsefulCouponCount();
        if (usefulCouponCount != null && usefulCouponCount > 0){
            list.forEach(item -> item.setIsCoupon(1));
        }

        // list排序 有闲置状态的电站》距离>价格
        List<StationHomeListOut>  outList = list.stream().sorted(
                Comparator.comparing(StationHomeListOut::getFreeGun).reversed().
                thenComparing(StationHomeListOut::getDistance).
                thenComparing(StationHomeListOut::getPrice))
                .collect(Collectors.toList());
        return outList;
    }

    @Override
    public StationHomeDetailOut stationDetail(StationDetailIn detailIn) {

        // 查看桩站详情
        logger.info("===> 桩站详情查询");
        StationHomeDetail detail = this.stationHomeMapper.stationDetail(detailIn.getStationId());

        if(detail == null){
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }

        StationHomeDetailOut result = dozer.map(detail, StationHomeDetailOut.class);
        result.setStationId(detailIn.getStationId());

        // 设置实时用电价格
        result.setPrice(CommonUtil.calculatePrice(detail.getChargePrice(),detail.getServicePrice(),detail.getIncrease()));
        logger.info(String.format("====> 原始电价: %s, 服务费: %s, 涨幅度: %s, 现价: %s",
                detail.getChargePrice(),detail.getServicePrice(),detail.getIncrease(),result.getPrice()));


        if(result != null ){
            result.setIsReduce(0);
            // 桩站可领优惠券数量
            logger.info("===> 桩站可领优惠券数量查询");
            Integer count = this.stationHomeMapper.getStationCouponCount(detailIn.getStationId());
            if(count > 0){
                result.setIsCoupon(1);
            }
            // 充电枪总数
            logger.info("===> 充电枪总数查询");
            List<StationGunStatistics> gunCount = this.stationClientGunMapper.getCountGroupByType(detailIn.getStationId());
            // 充电枪空闲数量
            logger.info("===> 充电枪空闲数量查询");
            List<StationGunStatistics>  statisticsGun = this.stationClientGunMapper.getFreeGunByStationId(
                                                    detailIn.getStationId(),
                                                    GunStatusEnum.FREE.getValue());
            // "快充桩总数"
            int fastChargeTotal = 0;
            // "快充桩剩余数量"
            int fastChargeLeft = 0;
            // "慢充桩总数"
            int trickleChargeTotal = 0;
            // "慢充桩剩余数量"
            int trickleChargeLeft = 0;

            for (StationGunStatistics item : statisticsGun) {
                if(item.getChargeType() == ClientChargeTypeEnum.AC_SLOW.getValue()){
                    trickleChargeLeft++;
                }else if(item.getChargeType() == ClientChargeTypeEnum.DC_FAST.getValue()){
                    fastChargeLeft++;
                }else if(item.getChargeType() == ClientChargeTypeEnum.AC_FAST.getValue()){
                    fastChargeLeft++;
                }
            }

            for (StationGunStatistics item : gunCount) {
                if(item.getChargeType() == ClientChargeTypeEnum.AC_SLOW.getValue()){
                    trickleChargeTotal += item.getTotal();
                }else if(item.getChargeType() == ClientChargeTypeEnum.DC_FAST.getValue()){
                    fastChargeTotal += item.getTotal();
                }else if(item.getChargeType() == ClientChargeTypeEnum.AC_FAST.getValue()){
                    fastChargeTotal += item.getTotal();
                }
            }

            result.setTrickleChargeTotal(trickleChargeTotal);
            result.setFastChargeTotal(fastChargeTotal);
            result.setTrickleChargeLeft(trickleChargeLeft);
            result.setFastChargeLeft(fastChargeLeft);

        }

        // 计算与电站距离
        double distence = CommonUtil.distance(detail.getLng(),detail.getLat(),detailIn.getLng(),detailIn.getLat())*1000;
        result.setDistance((int)distence);

        return result;
    }


}
