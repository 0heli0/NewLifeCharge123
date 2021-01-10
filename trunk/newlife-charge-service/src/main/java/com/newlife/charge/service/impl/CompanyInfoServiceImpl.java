package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.CompanyInfo;
import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.domain.exModel.CompanyInfoEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.CompanyInfoIn;
import com.newlife.charge.core.dto.in.GeneralCompanyInfoUpdateIn;
import com.newlife.charge.core.dto.in.GeneralStationUserAuditIn;
import com.newlife.charge.core.dto.in.GeneralStationUserPageIn;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.AuditStatusEnum;
import com.newlife.charge.core.enums.UserTypeEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.CompanyInfoMapper;
import com.newlife.charge.dao.UserMapper;
import com.newlife.charge.service.*;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {

    @Autowired
    private CompanyInfoMapper mapper;

    @Autowired
    private StationInfoService stationInfoService;
    @Autowired
    private StationClientService stationClientService;
    @Autowired
    private StationParkingLotService stationParkingLotService;
    @Autowired
    private StationClientGunService stationClientGunService;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private Mapper dozer;


    @Override
    public List<GeneralIndexNoAuditCompanyOut> generalIndexNoAuditCompanyList(GeneralStationUserPageIn pageIn) {
        PageHelper.startPage(pageIn.getPageNo(), pageIn.getPageSize());
        Page<GeneralIndexNoAuditCompanyOut> page = this.mapper.generalIndexNoAuditCompanyList(pageIn.getCompanyName(), pageIn.getMobile(), pageIn.getManagerName(), pageIn.getAuditStatus());
        return page.getResult();
    }


    @Override
    public CompanyInfo getById(Integer id) {
        return this.mapper.get(id);
    }

    @Override
    public void audit(GeneralStationUserAuditIn auditIn) {
        CompanyInfo companyInfo_db = this.getById(auditIn.getCompanyId());
        if (companyInfo_db == null) {
            throw new BizException("待审核的公司信息未找到");
        }
        companyInfo_db.setAuditStatus(auditIn.getAuditStatus());
        companyInfo_db.setRemark(auditIn.getRemark());
        Integer commissionRation = auditIn.getCommissionRation();
        if (commissionRation == null) {
            commissionRation = 0;
        }
        companyInfo_db.setCommissionRation(new BigDecimal(commissionRation));
        this.update(companyInfo_db);
    }

    @Override
    public PageInfo<GeneralCompanyInfoPageOut> page(int pageNo, int pageSize, Integer auditStatus, String companyName) {

        PageHelper.startPage(pageNo, pageSize);
        Page<CompanyInfo> page = this.mapper.page(auditStatus, companyName);
        List<CompanyInfo> list = page.getResult();
        List<GeneralCompanyInfoPageOut> outList = BeanMapper.mapList(list, GeneralCompanyInfoPageOut.class);
        if (outList != null && outList.size() > 0) {
            Iterator<GeneralCompanyInfoPageOut> it = outList.iterator();
            GeneralCompanyInfoPageOut next;
            //TODO:代码重构，减少数据库查询次数
            while (it.hasNext()) {
                next = it.next();
                next.setStationInfoCount(stationInfoService.countByCompanyId(next.getId()));
                next.setStationParkingLotCount(stationParkingLotService.countByCompanyId(next.getId()));
                next.setStationClientCount(stationClientService.countByCompanyId(next.getId()));
                next.setStationClientGunCount(stationClientGunService.countByCompanyId(next.getId()));
            }
        }
        return new PageInfo<>(pageNo, pageSize, page.getTotal(), outList);
    }

    @Override
    public List<GeneralCompanyInfoSelectListOut> selectList(Integer auditStatus, String companyName) {
        return this.mapper.selectList(auditStatus, companyName);
    }

    @Override
    public GeneralCompanyInfoOut getInfoById(Integer id) {
        GeneralCompanyInfoOut info = this.mapper.getInfoById(id);
        if (info != null) {
            info.setStationInfoCount(stationInfoService.countByCompanyId(id));
            info.setStationParkingLotCount(stationParkingLotService.countByCompanyId(id));
            info.setStationClientCount(stationClientService.countByCompanyId(id));
            info.setStationClientGunCount(stationClientGunService.countByCompanyId(id));
        }
        return info;
    }

    @Override
    public int update(CompanyInfo companyInfo) {
        return this.mapper.update(companyInfo);
    }

    @Override
    public int update(GeneralCompanyInfoUpdateIn updateIn) {
        Integer id = updateIn.getId();
        CompanyInfo companyInfo = this.getById(id);
        if (companyInfo == null) {
            throw new BizException(ERROR.COMPANY_INFO_NOT_EXIST);
        }
        if (AuditStatusEnum.PASS.getValue() != companyInfo.getAuditStatus()) {
            //这个方法只能修改已审核的数据
            throw new BizException(ERROR.COMPANY_AUDIT_STATUS_NOT_PASS);
        }

        CompanyInfo info = this.dozer.map(updateIn, CompanyInfo.class);
        return this.update(info);

    }

    @Override
    public int updateRation(Integer companyId, Integer commissionRation) {
        CompanyInfo companyInfo = this.getById(companyId);
        if (companyInfo == null) {
            throw new BizException("公司信息未找到");
        }
        if (commissionRation == null) {
            commissionRation = 0;
        }
        companyInfo.setCommissionRation(new BigDecimal(commissionRation));
        return this.update(companyInfo);
    }

    @Override
    public GeneralCompanyUserInfoOut getCompanyUserInfo(Integer id) {

        GeneralCompanyUserInfoOut companyUserInfo = this.mapper.getCompanyUserInfo(id);

        Integer auditStatus = companyUserInfo.getAuditStatus();
        String openId = companyUserInfo.getOpenId();
        if (StringUtils.isBlank(openId)) {
            companyUserInfo.setOpenId("未绑定");
        }
        if (auditStatus != null) {
            companyUserInfo.setAuditStatusStr(AuditStatusEnum.getDescriptionByValue(auditStatus));
        }
        return companyUserInfo;
    }

    @Override
    public CompanyInfo save(CompanyInfoIn companyInfo) {


        CompanyInfo companyInfo1 = dozer.map(companyInfo, CompanyInfo.class);
        //首次提交资质
        companyInfo1.setUserId(UserContext.getUserId());
        companyInfo1.setAuditStatus(AuditStatusEnum.NOAudited.getValue());
        companyInfo1.setAuditTime(new Date());
        companyInfo1.setRemark("");
        companyInfo1.setCommissionRation(new BigDecimal(0));
        companyInfo1.setCreateTime(new Date());
        if(companyInfo.getId() == null) {
            CompanyInfoEx ci = mapper.getByUserId(UserContext.getUserId());
            if(ci != null) {
                throw new BizException("您已提交资质，请勿重复提交");
            }
            this.mapper.insert(companyInfo1);
        } else {
            //重新提交资质 更新
            this.mapper.update(companyInfo1);
        }

        return companyInfo1;
    }

    @Override
    public StationCompanyInfoOut stationCompanyInfo() {
        StationCompanyInfoOut stationCompanyInfoOut = new StationCompanyInfoOut();
        CompanyInfoEx companyInfo = mapper.getByUserId(UserContext.getUserId());
        if (companyInfo != null) {
            stationCompanyInfoOut = dozer.map(companyInfo, StationCompanyInfoOut.class);
        } else {
            throw new BizException(ERROR.COMPANY_INFO_NOT_EXIST.message());
        }
        return stationCompanyInfoOut;
    }

    @Override
    public List<CompanyInfo> userCompanys() {

        List<CompanyInfo> companyInfos = new ArrayList<>();
        //查找该账号 是否有多种类型
        List<User> users = userMapper.getByMobile(UserContext.getMobile());
        if(Collections3.isNotEmpty(users)) {
            for(User u:users) {
                if(u.getUserType().equals(UserTypeEnum.STATION_MAIN.getValue())) {
                    //桩站主账号 直接查询 公司信息表
                    CompanyInfo companyInfo = mapper.getByUserId(u.getId());
                    companyInfos.add(companyInfo);
                }
                if(u.getUserType().equals(UserTypeEnum.STATION_SUB.getValue())) {
                    //桩站子账号 直接查询 用户角色关联 桩站 关联公司
                    List<CompanyInfo> companyInfoList = mapper.userCompanys(u.getId());
                    companyInfos.addAll(companyInfoList);
                }
            }
        }

        return companyInfos;
    }

    @Override
    public StationCompanyInfoOut getInfoByStationId(Integer stationId) {

        StationCompanyInfoOut stationCompanyInfoOut = mapper.getInfoByStationId(stationId);

         return stationCompanyInfoOut;
    }
}
