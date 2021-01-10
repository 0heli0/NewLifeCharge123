package com.newlife.charge.service;


import com.newlife.charge.core.domain.StationInfo;
import com.newlife.charge.core.domain.StationTimePrice;
import com.newlife.charge.core.domain.exModel.StationInfoEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 桩站信息 service 类
 * <p>
 */
public interface StationInfoService {


    /**
     * 总后台 分页查询桩站信息
     *
     * @param pageIn
     * @return
     */
    PageInfo<GeneralStationInfoPageOut> page(GeneralStationInfoPageIn pageIn);

    /**
     * 总后台 桩站下拉列表（状态可用并且未删除）
     *
     * @return
     */
    List<SelectListOut> selectList();


    /**
     * 根据主键ID查询桩站账户及详情信息
     *
     * @param id 桩站ID
     * @return
     */
    StationInfoEx getById(Integer id);


    /**
     * 根据桩站主键ID查询桩站当前实时电价（基础电价+时段电价增长率/100*基础电价）
     *
     * @param id 桩站ID
     * @return
     */
    BigDecimal getCurrentChargePrice(Integer id);


    /**
     * 根据公司ID统计桩站数量
     *
     * @param companyId 公司ID
     * @return
     */
    int countByCompanyId(Integer companyId);


    /**
     * 总后台 桩站详情展示
     *
     * @param id 桩站ID
     * @return
     */
    GeneralStationInfoOut getInfoById(Integer id);

    /**
     * 总后台 桩站编辑数据回填
     *
     * @param id 桩站ID
     * @return
     */
    GeneralStationUpdateInfoOut getUpdateInfoById(Integer id);


    /**
     * 总后台桩站信息新增
     *
     * @param saveIn
     */
    void save(StationInfoSaveIn saveIn);

    /**
     * 总后台桩站信息更新
     *
     * @param updateIn
     */
    void update(StationInfoUpdateIn updateIn);

    /**
     * 总后台桩站账号信息更新
     *
     * @param stationInfo
     */
    int updateStationInfo(StationInfo stationInfo);

    /**
     * 总后台桩站单条删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 总后台桩站信息批量删除
     *
     * @param ids
     */
    void deletes(int[] ids);

    /**
     * @Description: 桩站后台设置电费和服务费
     * @Author: Linzq
     * @CreateDate: 2019/5/8 0008 13:30
     */
    void setChargeAndServicePrice(StationInfoSetChargeIn setChargeIn);

    /**
     * @Description: 桩站后台 设置时段电费
     * @Author: Linzq
     * @CreateDate: 2019/5/8 0008 16:47
     */
    void setStationTimePrice(StationTimePriceIn stationTimePriceIn);

    /**
     * @Description: 桩站后台 设置时段电费
     * @Author: Linzq
     * @CreateDate: 2019/5/8 0008 16:47
     */
    StationInfoOut chargeAndServicePriceInfo(Integer station);

    /**
     * @Description: 桩站后台 删除时段电费
     * @Author: Linzq
     * @CreateDate: 2019/5/9 0009 15:31
     */
    void deleteStationTimePrice(StationTimePriceQuery query);

    /**
     * @Description: 桩站后台 时段电费查询
     * @Author: Linzq
     * @CreateDate: 2019/5/9 0009 16:10
     */
    List<StationTimePriceListOut> stationTimePriceList();

    /**
     * @Description: 桩站后台 时段电费详情
     * @Author: Linzq
     * @CreateDate: 2019/5/9 0009 16:10
     */
    List<StationTimePrice> stationTimePriceInfo(StationTimePriceQuery stationTimePriceQuery);

    /**
     * @Description: 桩站后台 根据公司Id查询当前用户关联的桩站列表
     * @Author: Linzq
     * @CreateDate: 2019/5/16 0016 10:53
     */
    List<StationInfoOut> selectListByCompanyId(Integer companyId, Integer userId);


    /**
     * TODO:同步桩站的时段电价和服务费到其下所有的充电桩上
     *
     * @param id 桩站ID
     * @return
     */
    String syncTimeAndServicePrice(Integer id);

}
