package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.GeneralStationSpendLogPageIn;
import com.newlife.charge.core.dto.in.StationSpendLogInfoIn;
import com.newlife.charge.core.dto.out.GeneralStationSpendLogPageOut;
import com.newlife.charge.core.dto.out.NewLifeSpendLogInfoOut;
import com.newlife.charge.core.dto.out.StationChargeOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.ChargeLogService;
import com.newlife.charge.service.NewLifeSpendLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 桩站资金管理
 */
@PlatLogModule(moduleId = "/api/stationSpendLog", moduleName = "桩站资金管理")
@Api(description = "桩站资金管理")
@RestController
@RequestMapping("/api/stationSpendLog")
public class StationSpendLogController extends BaseController {

    @Autowired
    private NewLifeSpendLogService newLifeSpendLogService;
    @Autowired
    private ChargeLogService chargeLogService;

    /**
     * 总后台 桩站资金统计-分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:stationSpendLog:general:menu")
    @ApiOperation(value = "总后台 桩站资金统计-分页查询数据", notes = "总后台 桩站资金统计-分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralStationSpendLogPageOut>> generalPageList(
            @RequestBody @Valid @ApiParam(required = true) GeneralStationSpendLogPageIn pageIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        PageInfo<GeneralStationSpendLogPageOut> page = new PageInfo<>();
        try {
            page = this.newLifeSpendLogService.stationPage(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general stationSpendLog pageList error", e);
            return error(page);
        }
    }

    /**
     * 总后台 桩站资金统计-查看详情(卖电账单、卖电流水)
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:stationSpendLog:general:info")
    @ApiOperation(value = "总后台 桩站资金统计-卖电账单查看详情", notes = "总后台 桩站资金统计-卖电账单查看详情")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<NewLifeSpendLogInfoOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) StationSpendLogInfoIn infoIn,
                                                        BindingResult bindingResult) {
        NewLifeSpendLogInfoOut infoOut;

        throwBindingResultErrorMsg(bindingResult);
        Integer type = infoIn.getType();
        if (type == 41) {
            //卖电流水
            StationChargeOut stationChargeInfo = this.chargeLogService.getStationChargeInfo(infoIn.getId());
            infoOut = new NewLifeSpendLogInfoOut();
            infoOut.setType(41);
            infoOut.setStationChargeOut(stationChargeInfo);
        } else {
            //卖电账单
            infoOut = this.newLifeSpendLogService.getInfo(infoIn.getId());
        }
        return success(infoOut);
    }
}
