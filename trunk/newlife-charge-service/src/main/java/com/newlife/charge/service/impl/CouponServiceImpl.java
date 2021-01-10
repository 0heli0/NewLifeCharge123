package com.newlife.charge.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.StringUtil;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.*;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.CouponOut;
import com.newlife.charge.core.dto.out.StationDetailOut;
import com.newlife.charge.core.enums.CouponScopeTypeEnum;
import com.newlife.charge.core.enums.CouponStatusEnum;
import com.newlife.charge.core.enums.CouponTypeEnum;
import com.newlife.charge.core.enums.UserCouponStatusEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.*;
import com.newlife.charge.service.CouponService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠券service类
 * <p>
 */
@Service
@Transactional
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponStationRefMapper couponStationRefMapper;

    @Autowired
    private StationDetailMapper stationDetailMapper;

    @Autowired
    private Mapper dozer;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private StationInfoMapper stationInfoMapper;

    @Override
    public int save(CouponSaveIn saveIn) {

        int result = 0;
        if(saveIn != null){

            // 检查参数是否满足要求
            saveIn.getThresholdPrice();

            Coupon coupon = dozer.map(saveIn, Coupon.class);
            coupon.setCreateTime(new Date());
            coupon.setLeftNumber(saveIn.getTotalNumber());
            coupon.setStationId(0);// 总后台电站id默认为0

            // 保存映射表,无论是否总后台或者桩站指定的添加都需要保存映射,便于以后查询
            if(saveIn.getType().equals(CouponTypeEnum.Electro_Type.getValue()) && saveIn.getStationIds().size()>0){
                if(saveIn.getStationIds().size() == 1 && saveIn.getStationIds().get(0) == 0){
                    // 防止集合只有一个0的元素
                    coupon.setScopeType(CouponScopeTypeEnum.All_Scope.getValue());
                }else {
                    // 添加适用范围
                    coupon.setScopeType(CouponScopeTypeEnum.Appoint_Type.getValue());
                }
            }else {
                coupon.setScopeType(CouponScopeTypeEnum.All_Scope.getValue());
            }

            // 总后台判断优惠结束券日期大于今天则为生效中,否则则为已过期
            if(coupon.getUseEndTime().getTime() > System.currentTimeMillis()){
                coupon.setStatus(CouponStatusEnum.USED.getValue());
            }else {
                coupon.setStatus(CouponStatusEnum.OVERDUE.getValue());
            }

            // 检查面额是否重复
            Coupon couponInfo = new Coupon();
//            couponInfo.setScopeType(coupon.getScopeType());
            couponInfo.setType(saveIn.getType());
            couponInfo.setStationId(0);
            couponInfo.setPrice(saveIn.getPrice());
            couponInfo.setThresholdPrice(saveIn.getThresholdPrice());

            if(saveIn.getType().equals(CouponTypeEnum.Electro_Type.getValue())){
                couponInfo.setCollectStartTime(saveIn.getCollectStartTime());
                couponInfo.setCollectEndTime(saveIn.getCollectEndTime());
                couponInfo.setUseStartTime(saveIn.getUseStartTime());
                couponInfo.setUseEndTime(saveIn.getUseEndTime());
            }

            checkPricIsRepeat(couponInfo, saveIn.getStationIds());

            //先保存优惠券表
            result = couponMapper.insert(coupon);


            // 保存映射表,无论是否总后台或者桩站指定的添加都需要保存映射,便于以后查询
            if(saveIn.getType().equals(CouponTypeEnum.Electro_Type.getValue()) && saveIn.getStationIds().size()>0){
                saveCouponStationRef(saveIn.getStationIds(), coupon.getId());
            }

        }
        return result;
    }



    @Override
    public PageInfo<CouponOut> page(CouponQueryIn queryIn) {

        // 先查看适用桩站是否存在，存在则查询全部和包含的桩站
        if(StringUtil.isNotEmpty(queryIn.getStationName())){
            List<StationInfo> stationInfos = stationInfoMapper.getByName(queryIn.getStationName());

            if(stationInfos == null){
                return new PageInfo<>(queryIn.getPageNo(),queryIn.getPageSize(),0,null);
            }

            if(stationInfos != null && stationInfos.size() == 0){
                return new PageInfo<>(queryIn.getPageNo(),queryIn.getPageSize(),0,null);
            }
        }

        PageHelper.startPage(queryIn.getPageNo(), queryIn.getPageSize());
        Page<CouponOut> pageInfo = this.couponMapper.page(queryIn);
        pageInfo.getResult().stream().forEach(item -> {
            if(StringUtil.isEmpty(item.getStationName())){
                item.setStationName("全部");
            }else {
                // 将有名称的优惠券id再查询，一次拼接名称
                String name = couponMapper.getStationNames(item.getId());
                item.setStationName(name);
            }
        });

        return new PageInfo<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getResult());
    }

    @Override
    public CouponOut info(int id) {
        Coupon info = this.couponMapper.get(id);
        CouponOut out = null;
        if(info != null){
            out = dozer.map(info, CouponOut.class);
            // 判断是否已下架
            if(out.getCollectEndTime().getTime() <= System.currentTimeMillis()){
                out.setIsSoldOut(1);
            }else {
                out.setIsSoldOut(0);
            }
        }

        // 当体验券查询结果有值,且桩站为指定用电通用券,则查询是否指定了桩站使用
        if(out != null && (out.getType()==2) || out.getType()==3){
            List<StationDetail> stationDetailList = Lists.newArrayList();
            /*
            * 分为两种情况:
            *    1.总后台创建的优惠券,可以指定多个桩站
            *    2.桩站端创建的优惠券,只能自己桩站使用
             */
            // 情况1,先查询是否指定了桩站
            if(out.getStationId() == 0){
                List<CouponStationRef> refList =  this.couponStationRefMapper.getByCouponId(out.getId());
                if(refList != null && refList.size() > 0){
                    int[] ids = new int[refList.size()];
                    for (int i = 0; i < refList.size(); i++) {
                        ids[i] = refList.get(i).getStationId();
                    }
                    stationDetailList = stationDetailMapper.infosById(ids);
                }
            }
            //情况2,直接查询桩站信息
            else {
                StationDetail staion = stationDetailMapper.get(out.getStationId());
                stationDetailList.add(staion);
            }
            List<StationDetailOut> stationDetailOut = BeanMapper.mapList(stationDetailList, StationDetailOut.class);
            out.setStationDetailList(stationDetailOut);
        }
        return out;
    }



    @Override
    public int update(CouponUpdateIn updateIn) {
        int result = 0;

        // 检查参数是否满足要求
        updateIn.getThresholdPrice();

        // 检查面额是否重复
        if(updateIn != null){
            Coupon couponInfo = new Coupon();
//            couponInfo.setScopeType(CouponScopeTypeEnum.All_Scope.getValue());
            couponInfo.setType(updateIn.getType());
            couponInfo.setStationId(0);
            couponInfo.setPrice(updateIn.getPrice());
            couponInfo.setThresholdPrice(updateIn.getThresholdPrice());

            if(updateIn.getType().equals(CouponTypeEnum.Electro_Type.getValue())){
                couponInfo.setCollectStartTime(updateIn.getCollectStartTime());
                couponInfo.setCollectEndTime(updateIn.getCollectEndTime());
                couponInfo.setUseStartTime(updateIn.getUseStartTime());
                couponInfo.setUseEndTime(updateIn.getUseEndTime());
            }

            checkPricIsRepeat(couponInfo, updateIn.getStationIds());
        }

        // 当更新操作指定桩站参数不为空先查询到映射表
        if(updateIn.getStationIds().size() > 0 ){
            // 先查看是否存在映射表，有则删除
            deleteRefByids(updateIn.getId());
        }
        if(updateIn != null){
            Coupon coupon = dozer.map(updateIn, Coupon.class);
            result = this.couponMapper.update(coupon);
        }

        //重新保存映射表
        saveCouponStationRef(updateIn.getStationIds(),updateIn.getId());
        return result;
    }

    @Override
    public int out(DelIn delIn) {
        int result = this.couponMapper.out(delIn.getId());
        return result;
    }

    @Override
    public int deleteById(DelIn delIn) {
        //优惠劵已被领取的 不能删除
        checkDelete(delIn.getId());

        // 先查看是否存在映射表，有则删除
        deleteRefByids(delIn.getId());

        int result = this.couponMapper.deleteById(delIn.getId());
        return result;
    }

    //优惠劵已被领取的 不能删除
    private void checkDelete(Integer id) {
        List<UserCoupon> couponOuts = userCouponMapper.getByCouponId(id);
        if(Collections3.isNotEmpty(couponOuts)) {
            throw new BizException("该优惠劵已有用户领取，不能删除");

        }
    }

    @Override
    public void deleteByIds(DelsIn delsIn) {
        //优惠劵已被领取的 不能删除
        int[] idsArr = delsIn.getIds();
        for(int id:idsArr) {
            checkDelete(id);
        }

        // 先查看是否存在映射表，有则删除
        List<CouponStationRef> refs = this.couponStationRefMapper.getByCouponIds(delsIn.getIds());
        // 若有则删除映射表
        if(refs != null && refs.size() > 0 ){
            int[] ids = new int[refs.size()];
            for (int i = 0; i < refs.size(); i++) {
                ids[i] = refs.get(i).getId();
            }
            this.couponStationRefMapper.deleteByIds(ids);
        }

        this.couponMapper.deleteByIds(delsIn.getIds());
    }

    /**
     * 当选择适用桩站的指定用电优惠券,将优惠券id批量保存到映射表中
     * @param StationIds
     * @param couponId
     * @return
     */
    private void saveCouponStationRef(List<Integer> StationIds, Integer couponId){

        if(StationIds == null || StationIds.size() == 0 ){
            return;
        }

        List<CouponStationRef> refList = Lists.newArrayList();

        StationIds.stream().forEach(item ->{
            CouponStationRef ref = new CouponStationRef();
            ref.setStationId(item);
            ref.setCouponId(couponId);
            ref.setCreateTime(new Date());
            refList.add(ref);
        });

        couponStationRefMapper.batchInsert(refList);
    }

    /**
     * 根据体验券id删除映射表
     * @param couponId
     * @return
     */
    private void deleteRefByids(Integer couponId){
        // 先查找映射表
        List<CouponStationRef> refs = this.couponStationRefMapper.getByCouponId(couponId);
        // 若有则删除映射表
        if(refs != null && refs.size() > 0 ){
            int[] ids = new int[refs.size()];
            for (int i = 0; i < refs.size(); i++) {
                ids[i] = refs.get(i).getId();
            }
            this.couponStationRefMapper.deleteByIds(ids);
        }
    }

    @Override
    public PageInfo<CouponOut> pageList(CouponStationQueryIn queryIn) {

        PageHelper.startPage(queryIn.getPageNo(), queryIn.getPageSize());
        Page<CouponOut> pageInfo = this.couponMapper.pageList(queryIn);

        return new PageInfo<>(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getResult());
    }

    @Override
    public void stationSave(CouponStationSaveIn saveIn) {

        // 检查参数是否满足要求
        saveIn.getThresholdPrice();

        //存在相同面额同门槛 未过期的同类型优惠劵 不能添加
        CouponQueryIn couponQueryIn = new CouponQueryIn();
        couponQueryIn.setStatus(CouponStatusEnum.USED.getValue());
        couponQueryIn.setPrice(saveIn.getPrice());
        couponQueryIn.setThresholdPrice(saveIn.getThresholdPrice());
        couponQueryIn.setScopeType(CouponScopeTypeEnum.Appoint_Type.getValue());
        couponQueryIn.setType(CouponTypeEnum.Station_Electro_Type.getValue());
        Coupon cn = couponMapper.getByQuery(couponQueryIn);
        if(cn != null) {
            throw new BizException("请勿重复添加相同的用电优惠劵");
        }

        Coupon coupon = dozer.map(saveIn, Coupon.class);
        //桩站添加指定用电优惠劵
        coupon.setScopeType(CouponScopeTypeEnum.Appoint_Type.getValue());
        coupon.setType(CouponTypeEnum.Station_Electro_Type.getValue());
        coupon.setCreateTime(new Date());
        coupon.setLeftNumber(saveIn.getTotalNumber());
        coupon.setStationId(UserContext.getStationId());

        // 总后台判断优惠结束券日期大于今天则为生效中,否则则为已过期
        if(coupon.getUseEndTime().getTime() > System.currentTimeMillis()){
            coupon.setStatus(CouponStatusEnum.USED.getValue());
        }else {
            coupon.setStatus(CouponStatusEnum.OVERDUE.getValue());
        }

        //先保存优惠券表
        couponMapper.insert(coupon);

        // 保存映射表,便于以后查询
        CouponStationRef ref = new CouponStationRef();
        ref.setStationId(UserContext.getStationId());
        ref.setCouponId(coupon.getId());
        ref.setCreateTime(new Date());
        couponStationRefMapper.insert(ref);
    }

    @Override
    public CouponOut stationInfo(int id) {
        CouponOut info = this.couponMapper.info(id);

        //计算 领取张数 和 使用张数
        List<UserCoupon> userCoupons = userCouponMapper.getByCouponId(id);

        info.setUserGetNumber(userCoupons.size());

        //过滤出用户使用的张数
        if(Collections3.isNotEmpty(userCoupons)) {
            List<UserCoupon> userCount = userCoupons.stream().filter(userCoupon -> UserCouponStatusEnum.USED.getValue().equals(userCoupon.getStatus())).collect(Collectors.toList());
            info.setUsedNumber(userCount.size());
        } else {
            info.setUsedNumber(0);
        }
        return info;
    }

    /**
     * 查看优惠券是否重复
     * @param couponIn
     * @param stationIds
     * @return
     */
    private boolean checkPricIsRepeat(Coupon couponIn, List<Integer> stationIds){

        // 根据面额查询数据
        List<Coupon> couponList = this.couponMapper.getByParams(couponIn);
        if(couponList == null || couponList.size() == 0){
            return true;
        }

        couponList.forEach(item ->{
            // 查询数据为全部桩站适用，有则判断为重复
            if(item.getScopeType() == null || item.getScopeType() == 0){
                throw new BizException(ERROR.COUPON_REPEAT);
            }
        });

        // 通用优惠券存在包含关系，有则判断为重复
        if(couponIn.getScopeType().equals(CouponScopeTypeEnum.All_Scope.getValue())){
            throw new BizException(ERROR.COUPON_REPEAT);
        }
        // 查看指定券是否存在包含关系
        int[] ids = new int[couponList.size()];
        for (int i = 0; i < couponList.size(); i++) {
            ids[i] = couponList.get(i).getId();
        }

        // 查询该优惠券对应的桩站
        List<CouponStationRef> refs = couponStationRefMapper.getByCouponIds(ids);

        // 如果存在与新增/修改的桩站相同的，则判断为重复
        if(stationIds.size() == 0){
            throw new BizException(ERROR.COUPON_REPEAT);
        }

        // 添加的为全部券，则判断为重复
        refs.forEach(item ->{
            if(stationIds.contains(item.getStationId())){
                throw new BizException(ERROR.COUPON_REPEAT);
            }
        });
        return true;

    }
}
