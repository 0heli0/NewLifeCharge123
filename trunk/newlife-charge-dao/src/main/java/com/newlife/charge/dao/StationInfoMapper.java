package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.StationInfoMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.StationInfo;
import com.newlife.charge.core.domain.exModel.StationInfoEx;
import com.newlife.charge.core.dto.out.GeneralStationInfoPageOut;
import com.newlife.charge.core.dto.out.SelectListOut;
import com.newlife.charge.core.dto.out.StationInfoOut;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StationInfoMapper extends CrudRepository<StationInfo> {



    /**
     * 总后台 桩站下拉列表（状态可用并且未删除）
     *
     * @return
     */
    List<SelectListOut> selectList();


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
    int countByCompanyId(@Param("companyId") Integer companyId);


    /**
     * 根据主键桩站ID查询桩站账户及详情信息
     *
     * @param id 桩站ID
     * @return
     */
    StationInfoEx getById(@Param("id") Integer id);

    /**
     * 分页或列表查询
     *
     * @param stationName 桩站名称
     * @param companyName 公司名称
     * @return
     */
    Page<GeneralStationInfoPageOut> page(@Param("stationName") String stationName, @Param("companyName") String companyName);

    /**
     * 设置桩站状态
     *
     * @param ids    桩站IDs
     * @param status 桩站状态
     * @return
     */
    int updateStatusByIds(@Param("ids") int[] ids, @Param("status") Integer status);

    /**
     * 逻辑删除
     *
     * @param ids     桩站IDs
     * @param delFlag 删除状态
     * @return
     */
    int updateDelFlagByIds(@Param("ids") int[] ids, @Param("delFlag") Integer delFlag);

    /**
     * @Description: 桩站后台 根据公司Id查询桩站列表
     * @Author: Linzq
     * @CreateDate:  2019/5/16 0016 10:53
     */
    List<StationInfoOut> selectListByCompanyId(@Param("companyId")Integer companyId, @Param("userId")Integer userId);

    /**
     * 根据名称模糊查询
     * @param name
     * @return
     */
    List<StationInfo> getByName(@Param("name") String name);

}