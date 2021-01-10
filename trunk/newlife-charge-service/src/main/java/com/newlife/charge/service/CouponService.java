package com.newlife.charge.service;


import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.CouponOut;

/**
 * 优惠券service类
 * <p>
 */
public interface CouponService {

    /**
     * 保存单个
     * @param saveIn
     * @return
     */
   int save(CouponSaveIn saveIn);


    /**
     * 预充值更新
     * @param updateIn
     * @return
     */
   int update(CouponUpdateIn updateIn);

    /**
     * 根据条件分页查询
     * @param queryIn
     * @return
     */
   PageInfo<CouponOut> page(CouponQueryIn queryIn);

    /**
     * 查询单个信息
     * @param id
     * @return
     */
   CouponOut info(int id);

    /**
     * 根据id下架
     * @param delIn
     * @return
     */
   int out(DelIn delIn);

    /**
     * 根据id删除
     * @param delIn
     * @return
     */
   int deleteById(DelIn delIn);

    /**
     * 批量删除
     * @param delsIn
     */
   void deleteByIds(DelsIn delsIn);

    /**
     * 桩站 根据条件分页查询
     * @param queryIn
     * @return
     */
    PageInfo<CouponOut> pageList(CouponStationQueryIn queryIn);

    /**
     * 桩站保存单个
     * @param saveIn
     * @return
     */
    void stationSave(CouponStationSaveIn saveIn);

    /**
     * @Description: 桩站查询优惠劵
     * @Author: Linzq
     * @CreateDate:  2019/5/13 0013 10:18
     */
    CouponOut stationInfo(int id);
}
