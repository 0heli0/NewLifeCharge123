package com.newlife.charge.service;


import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.CouponUsePageOut;
import com.newlife.charge.core.dto.out.UserCouponDetail;
import com.newlife.charge.core.dto.out.UserCouponOut;
import com.newlife.charge.core.dto.out.UserCouponPageOut;

/**
 * 用户领取和查看优惠券service类
 * <p>
 */
public interface UserCouponService {

    /**
     * 根据用户信息展示可领取优惠券
     * @param query
     * @return
     */
    UserCouponOut pageList(UserCouponQueryIn query, Integer userId);


    /**
     * 指定桩站可领取优惠券
     * @param query
     * @param userId
     * @return
     */
    UserCouponOut stationPageList(UserStationCouponQueryIn query, Integer userId);

    /**
     * 领取优惠券
     * @param takeIn
     */
    void take(TakeCouponIn takeIn, Integer userId);

    /**
     * 通过状态分页查找已领取的优惠券
     * @param queryIn
     * @return
     */
    PageInfo<UserCouponPageOut> userCouponPage(UserCouponListIn queryIn, Integer userId);

    /**
     * 查看已经领取的优惠券的详情
     * @param infoIn
     * @return
     */
    UserCouponDetail userCouponDetail(InfoIn infoIn);

    /**
     * 通过状态和手机号分页查找指定优惠券使用情况
     * @param queryIn
     * @return
     */
    PageInfo<CouponUsePageOut> usePage(CouponUseQueryIn queryIn);

}
