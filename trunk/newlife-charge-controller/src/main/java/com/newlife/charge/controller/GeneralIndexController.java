package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.AuditStatusEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.CompanyInfoService;
import com.newlife.charge.service.NewLifeSpendLogService;
import com.newlife.charge.service.StationClientGunService;
import com.newlife.charge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 总后台首页
 * 不记录系统日志
 */
@Api(description = "总后台首页")
@RestController
@RequestMapping("/api/generalIndex")
public class GeneralIndexController extends BaseController {


    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private StationClientGunService stationClientGunService;
    @Autowired
    private NewLifeSpendLogService newLifeSpendLogService;


    /**
     * 1.待审核桩站公司（即桩站账号列表）
     * 总后台首页待审核桩站公司(5条)
     *
     * @return
     */
    @ApiOperation(value = "总后台首页待审核桩站公司", notes = "总后台首页待审核桩站公司")
    @RequestMapping(value = "/general/noAuditCompanyList", method = RequestMethod.POST)
    public Response<List<GeneralIndexNoAuditCompanyOut>> generalIndexNoAuditCompanyList() {

        GeneralStationUserPageIn pageIn = new GeneralStationUserPageIn();
        pageIn.setAuditStatus(AuditStatusEnum.NOAudited.getValue());
        pageIn.setPageNo(1);
        pageIn.setPageSize(5);

        List<GeneralIndexNoAuditCompanyOut> list = null;
        try {
            list = this.companyInfoService.generalIndexNoAuditCompanyList(pageIn);
            return success(list);
        } catch (Exception e) {
            LOGGER.error("general noAuditCompanyList list error", e);
            return error(list);
        }
    }


    /**
     * 2.总后台首页新注册用户列表（5条）-- 即：用户管理-分页查询数据
     *
     * @return
     */
    @ApiOperation(value = "总后台首页新注册用户列表", notes = "总后台首页新注册用户列表")
    @RequestMapping(value = "/general/carUserList", method = RequestMethod.POST)
    public Response<List<GeneralIndexCarUserListOut>> generalIndexCarUserList() {

        List<GeneralIndexCarUserListOut> list = null;
        try {
            list = this.userService.generalIndexCarUserList(1, 5);
            return success(list);
        } catch (Exception e) {
            LOGGER.error("general generalIndexCarUserList error", e);
            return error(list);
        }
    }


    /**
     * 3.总后台首页资金明细（新活资金统计）
     *
     * @return
     */
    @ApiOperation(value = "总后台首页资金明细", notes = "总后台首页资金明细")
    @RequestMapping(value = "/general/spendLogPageList", method = RequestMethod.POST)
    public Response<PageInfo<NewLifeSpendLogPageOut>> generalPageList() {

        GeneralNewLifeSpendLogPageIn pageIn = new GeneralNewLifeSpendLogPageIn();
        pageIn.setPageNo(1);
        pageIn.setPageSize(5);
        PageInfo<NewLifeSpendLogPageOut> page = new PageInfo<>();
        try {
            page = this.newLifeSpendLogService.page(pageIn.getPageNo(), pageIn.getPageSize());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general index spendLogPageList pageList error", e);
            return error(page);
        }
    }


    /**
     * 4.总后台全部充电枪状态分页查询
     *
     * @return
     */
//    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "总后台首页全部充电枪状态分页查询", notes = "总后台首页全部充电枪状态分页查询")
    @RequestMapping(value = "/general/gunStatusPageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralIndexStationClientGunInfoOut>> generalIndexGunStatusPageList(
            @RequestBody @ApiParam(required = true) GeneralIndexStationClientGunPageIn pageIn) {
        PageInfo<GeneralIndexStationClientGunInfoOut> page = new PageInfo<>();
        try {
            page = this.stationClientGunService.generalIndexGunStatusPageList(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general generalGunStatusPageList pageList error", e);
            return error(page);
        }
    }


    /**
     * 5.总后台首页全部充电枪状态数量统计
     *
     * @return
     */
    @ApiOperation(value = "总后台首页全部充电枪状态数量统计", notes = "总后台首页全部充电枪状态数量统计")
    @RequestMapping(value = "/general/gunStatusStatistics", method = RequestMethod.POST)
    public Response<GunStatusStatisticsOut> generalIndexStatusStatistics(@RequestBody @ApiParam(required = true) IDIn idIn) {

        GunStatusStatisticsOut out = new GunStatusStatisticsOut();
        try {
            Integer stationId = idIn.getId();//桩站ID

            out = this.stationClientGunService.getGunStatusStatistics(stationId);
            return success(out);
        } catch (Exception e) {
            LOGGER.error("general generalIndexStatusStatistics pageList error", e);
            return error(out);
        }
    }

}
