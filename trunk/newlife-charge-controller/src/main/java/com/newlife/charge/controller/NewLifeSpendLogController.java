package com.newlife.charge.controller;


import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.NewLifeSpendLog;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.GeneralNewLifeSpendLogPageIn;
import com.newlife.charge.core.dto.in.InfoIn;
import com.newlife.charge.core.dto.in.StationNewLifeSpendLogPageIn;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新活资金统计
 */
@PlatLogModule(moduleId = "/api/newLifeSpendLog", moduleName = "新活资金统计")
@Api(description = "新活资金统计")
@RestController
@RequestMapping("/api/newLifeSpendLog")
public class NewLifeSpendLogController extends BaseController {


    @Autowired
    private NewLifeSpendLogService newLifeSpendLogService;


    /**
     * 总后台分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:newLifeSpendLog:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<NewLifeSpendLogPageOut>> generalPageList(@RequestBody @ApiParam(required = true) GeneralNewLifeSpendLogPageIn pageIn) {
        PageInfo<NewLifeSpendLogPageOut> page = new PageInfo<>();
        try {
            page = this.newLifeSpendLogService.page(pageIn.getPageNo(), pageIn.getPageSize(), pageIn.getUserMobile(), null, pageIn.getType());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general newLifeSpendLog pageList error", e);
            return error(page);
        }
    }


    /**
     * 总后台查看详情
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:newLifeSpendLog:general:info")
    @ApiOperation(value = "总后台查看详情", notes = "总后台根据主键ID查询详情信息")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<NewLifeSpendLogInfoOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
                                                        BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        NewLifeSpendLogInfoOut infoOut = this.newLifeSpendLogService.getInfo(infoIn.getId());
        return success(infoOut);
    }

    /**
     * 桩站后台 分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:newLifeSpendLog:station:menu")
    @ApiOperation(value = "桩站后台 分页查询数据", notes = "桩站后台 分页查询数据")
    @RequestMapping(value = "/station/pageList", method = RequestMethod.POST)
    public Response<PageInfo<NewLifeUserSpendLogPageOut>> stationPageList(@RequestBody @ApiParam(required = true) StationNewLifeSpendLogPageIn pageIn) {
        PageInfo<NewLifeUserSpendLogPageOut> page = new PageInfo<>();
        try {
            pageIn.setType(NewLifeSpendLogTypeEnum.SELL_ELECTRIC_BILL.getValue());
            pageIn.setStationId(UserContext.getStationId());
            page = this.newLifeSpendLogService.stationPageList(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general newLifeSpendLog pageList error", e);
            return error(page);
        }
    }

    /**
     * 桩站后台 查看详情
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:newLifeSpendLog:station:info")
    @ApiOperation(value = "桩站后台 查看详情", notes = "桩站后台 根据主键ID查询详情信息")
    @RequestMapping(value = "/station/info", method = RequestMethod.POST)
    public Response<NewLifeSpendLogInfoOut> stationInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
                                                        BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        NewLifeSpendLogInfoOut infoOut = this.newLifeSpendLogService.getInfo(infoIn.getId());
        return success(infoOut);
    }

    /**
     * 桩站后台 查看当日订单笔数和订单总额
     *
     * @param
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站后台 查看当日订单笔数和订单总额", notes = "桩站后台 查看当日订单笔数和订单总额")
    @RequestMapping(value = "/station/indexList", method = RequestMethod.POST)
    public Response stationIndexList(StationNewLifeSpendLogPageIn pageIn) {

        Map<String, Object> map = new HashMap<>();

        //获取当日时间
        pageIn.setDateTime(DateUtils.getDate("yyyy-MM-dd"));
        //设置显示条数
        pageIn.setLimitNo(5);
        pageIn.setType(NewLifeSpendLogTypeEnum.CHARGE_CONSUMPTION.getValue());
        pageIn.setStationId(UserContext.getStationId());
        List<NewLifeUserSpendLogPageOut> result = newLifeSpendLogService.getDaySpendLog(pageIn);

        //查询今天总的 充电次数 和统计总共用电度数
        pageIn.setLimitNo(null);
        List<NewLifeUserSpendLogPageOut> allList = newLifeSpendLogService.getDaySpendLog(pageIn);
        //计算总金额
        BigDecimal balance = new BigDecimal(0);
        if(Collections3.isNotEmpty(allList)) {
            for (NewLifeUserSpendLogPageOut out : allList){
                balance = balance.add(out.getAmount());
            }
        }
        map.put("result", result);
        map.put("size", result.size());
        map.put("balance", balance);
        return success(map);
    }

    /**
     * 桩站后台 查询卖电账单余额
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站后台 查询卖电账单余额", notes = "桩站后台 查询卖电账单余额")
    @RequestMapping(value = "/station/stationGainAmount", method = RequestMethod.POST)
    public Response stationGainAmount() {

        List<NewLifeSpendLog> newLifeSpendLogs = newLifeSpendLogService.stationGainAmount(NewLifeSpendLogTypeEnum.SELL_ELECTRIC_BILL.getValue(),UserContext.getStationId());
        BigDecimal totalMoney = new BigDecimal(0);
        if(Collections3.isNotEmpty(newLifeSpendLogs)) {
            for (NewLifeSpendLog newLifeSpendLog : newLifeSpendLogs) {
                BigDecimal returnedMoney = newLifeSpendLog.getAmount();
                totalMoney = totalMoney.add(returnedMoney);
            }
        }
        return success(totalMoney);
    }
}
