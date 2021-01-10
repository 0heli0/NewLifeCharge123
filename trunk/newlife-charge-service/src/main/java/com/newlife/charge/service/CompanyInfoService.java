package com.newlife.charge.service;


import com.newlife.charge.core.domain.CompanyInfo;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.CompanyInfoIn;
import com.newlife.charge.core.dto.in.GeneralCompanyInfoUpdateIn;
import com.newlife.charge.core.dto.in.GeneralStationUserAuditIn;
import com.newlife.charge.core.dto.in.GeneralStationUserPageIn;
import com.newlife.charge.core.dto.out.*;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * 桩站运营公司 service 类
 * <p>
 */
public interface CompanyInfoService {


    /**
     * 总后台首页待审核公司信息
     *
     * @return
     */
    List<GeneralIndexNoAuditCompanyOut> generalIndexNoAuditCompanyList(GeneralStationUserPageIn pageIn);


    /**
     * 根据主键ID查询
     *
     * @param id
     * @return
     */
    CompanyInfo getById(Integer id);

    /**
     * 资质审核
     *
     * @param auditIn
     */
    void audit(GeneralStationUserAuditIn auditIn);

    /**
     * 总后台 公司信息分页查询
     *
     * @param pageNo
     * @param pageSize
     * @param auditStatus
     * @param companyName
     * @return
     */
    PageInfo<GeneralCompanyInfoPageOut> page(int pageNo, int pageSize, Integer auditStatus, String companyName);

    /**
     * 总后台 公司信息列表查询
     *
     * @param auditStatus
     * @param companyName
     * @return
     */
    List<GeneralCompanyInfoSelectListOut> selectList(Integer auditStatus, String companyName);


    /**
     * 总后台根据公司ID查询详情
     * @param id
     * @return
     */
    GeneralCompanyInfoOut getInfoById(Integer id);


    /**
     * 更新公司信息
     *
     * @param companyInfo
     * @return
     */
    int update(CompanyInfo companyInfo);


    /**
     * 更新公司信息
     *
     * @param updateIn
     * @return
     */
    int update(GeneralCompanyInfoUpdateIn updateIn);

    /**
     * 更新公司抽佣比例
     *
     * @param companyId
     * @param commissionRation
     * @return
     */
    int updateRation(Integer companyId, Integer commissionRation);


    /**
     * 总后台 桩站主账号详情
     *
     * @param id 用户ID
     * @return
     */
    GeneralCompanyUserInfoOut getCompanyUserInfo(Integer id);


    /**
     * @Description: 提交公司资质
     * @Author: Linzq
     * @CreateDate:  2019/5/6 0006 13:41
     */
    CompanyInfo save(CompanyInfoIn companyInfo);

    /**
     * @Description: 桩站 主账号登入查询公司提交资质审核详情
     * @Author: Linzq
     * @CreateDate:  2019/5/9 0009 14:09
     */
    StationCompanyInfoOut stationCompanyInfo();

    /**
     * @Description: 桩站切换 账号关联的公司
     * @Author: Linzq
     * @CreateDate:  2019/5/14 0014 15:55
     */
    List<CompanyInfo> userCompanys();

    /**
     * @Description: 桩站后台  查询公司详情
     * @Author: Linzq
     * @CreateDate:  2019/5/16 0016 10:03
     */
    StationCompanyInfoOut getInfoByStationId(Integer stationId);
}
