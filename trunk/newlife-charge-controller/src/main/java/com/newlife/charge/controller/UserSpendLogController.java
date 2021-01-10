package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.GeneralUserSpendLogPageIn;
import com.newlife.charge.core.dto.in.InfoIn;
import com.newlife.charge.core.dto.in.StationNewLifeSpendLogPageIn;
import com.newlife.charge.core.dto.out.NewLifeSpendLogInfoOut;
import com.newlife.charge.core.dto.out.NewLifeUserSpendLogPageOut;
import com.newlife.charge.core.dto.out.UserNewLifeSpendLogChargeOut;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.NewLifeSpendLogService;
import com.newlife.charge.service.UserRechargeAccountService;
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
 * (车主)用户资金记录
 */
@PlatLogModule(moduleId = "/api/userSpendLog", moduleName = "用户资金记录")
@Api(description = "(车主)用户资金记录")
@RestController
@RequestMapping("/api/userSpendLog")
public class UserSpendLogController extends BaseController {

    @Autowired
    private NewLifeSpendLogService newLifeSpendLogService;

    @Autowired
    private UserRechargeAccountService userRechargeService;

    /**
     * 总后台 用户资金统计-分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:userSpendLog:general:menu")
    @ApiOperation(value = "总后台 用户资金统计-分页查询数据", notes = "总后台 用户资金统计-分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<NewLifeUserSpendLogPageOut>> generalPageList(
            @RequestBody @ApiParam(required = true) GeneralUserSpendLogPageIn pageIn) {
        PageInfo<NewLifeUserSpendLogPageOut> page = new PageInfo<>();
        try {
            page = this.newLifeSpendLogService.carUserPage(pageIn.getPageNo(), pageIn.getPageSize(), pageIn.getUserMobile(), pageIn.getType());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general userSpendLog pageList error", e);
            return error(page);
        }
    }


    /**
     * 总后台 用户资金统计-详情(包括1:账户充值,2:充电消费,3.余额退款)
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:userSpendLog:general:info")
    @ApiOperation(value = "总后台 用户资金统计-详情(包括1:账户充值,2:充电消费,3.余额退款)", notes = "总后台 用户资金统计-详情(包括1:账户充值,2:充电消费,3.余额退款)")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<NewLifeSpendLogInfoOut> generalInfo(
            @RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
            BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        NewLifeSpendLogInfoOut out = this.newLifeSpendLogService.getInfo(infoIn.getId());
        if (out == null) {
            throw new BizException(ERROR.DATA_NOT_FOUND);
        }
        return success(out);
    }

    /**
     * 桩站后台 卖电流水-分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:userSpendLog:station:menu")
    @ApiOperation(value = "桩站后台 卖电流水-分页查询数据", notes = "桩站后台 卖电流水-分页查询数据")
    @RequestMapping(value = "/station/pageList", method = RequestMethod.POST)
    public Response<PageInfo<NewLifeUserSpendLogPageOut>> stationPageList(
            @RequestBody @ApiParam(required = true) StationNewLifeSpendLogPageIn pageIn) {
        PageInfo<NewLifeUserSpendLogPageOut> page = new PageInfo<>();
        try {
            pageIn.setType(NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getValue());
            page = this.newLifeSpendLogService.stationPageList(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general userSpendLog pageList error", e);
            return error(page);
        }
    }

    /**
     * 桩站后台 用户充电消费详情
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @ApiOperation(value = "用户充电消费详情", notes = "用户充电消费详情")
    @RequestMapping(value = "/station/chargeDetail", method = RequestMethod.POST)
    public Response<UserNewLifeSpendLogChargeOut> chargeDetail(@RequestBody @Valid @ApiParam InfoIn infoIn,
                                                               BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);

        UserNewLifeSpendLogChargeOut out = this.userRechargeService.chargeDetail(infoIn.getId());

        return success(out);
    }

}
