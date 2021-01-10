package com.newlife.charge.dao;/* -------------------------------------------
 * com.newlife.charge.dao.CompanyInfoMapper.java
 * Copyright(C) 2016-2020 咪师教育科技有限公司
 * All rights reserved.
 * 2019-04-22 Created
 * ------------------------------------------- */


import com.github.pagehelper.Page;
import com.newlife.charge.core.domain.CompanyInfo;
import com.newlife.charge.core.domain.exModel.CompanyInfoEx;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.dao.common.CrudRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyInfoMapper extends CrudRepository<CompanyInfo> {


    /**
     * 总后台首页待审核公司信息
     *
     * @param companyName 公司名称
     * @param mobile      账号（手机号）
     * @param managerName 管理员姓名
     * @param auditStatus 审核状态(0审核中 1审核通过 2审核不通过，空表示全部)
     * @return
     */
    Page<GeneralIndexNoAuditCompanyOut> generalIndexNoAuditCompanyList(@Param("companyName") String companyName,
                                                                       @Param("mobile") String mobile,
                                                                       @Param("managerName") String managerName,
                                                                       @Param("auditStatus") Integer auditStatus);


    /**
     * 根据条件查询公司信息
     *
     * @param auditStatus 审核状态
     * @param companyName 公司名称
     * @return
     */
    Page<CompanyInfo> page(@Param("auditStatus") Integer auditStatus, @Param("companyName") String companyName);

    /**
     * 根据条件查询公司信息,用于下拉展示
     *
     * @param auditStatus 审核状态
     * @param companyName 公司名称
     * @return
     */
    List<GeneralCompanyInfoSelectListOut> selectList(@Param("auditStatus") Integer auditStatus, @Param("companyName") String companyName);


    /**
     * 总后台根据公司ID查询详情
     *
     * @param id 公司ID
     * @return
     */
    GeneralCompanyInfoOut getInfoById(@Param("id") Integer id);


    /**
     * 总后台 桩站主账号详情
     *
     * @param id 用户ID
     * @return
     */
    GeneralCompanyUserInfoOut getCompanyUserInfo(@Param("id") Integer id);

    /**
     * @Description: 根据桩站主账号id  获取公司信息
     * @Author: Linzq
     * @CreateDate: 2019/5/6 0006 9:22
     */
    CompanyInfoEx getByUserId(@Param("userId") Integer userId);

    /**
     * @Description: 桩站切换 账号关联的公司
     * @Author: Linzq
     * @CreateDate: 2019/5/6 0006 9:22
     */
    List<CompanyInfo> userCompanys(@Param("userId") Integer userId);

    /**
     * @Description: 桩站后台  查询公司详情
     * @Author: Linzq
     * @CreateDate:  2019/5/16 0016 10:03
     */
    StationCompanyInfoOut getInfoByStationId(@Param("stationId")Integer stationId);
}