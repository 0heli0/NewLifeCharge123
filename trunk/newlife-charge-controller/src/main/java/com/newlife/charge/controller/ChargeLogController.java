package com.newlife.charge.controller;


import com.newlife.charge.common.Collections3;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.ChargeLog;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.ChargeLogService;
import com.newlife.charge.service.CouponService;
import com.newlife.charge.service.UserCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户充电记录
 */
@PlatLogModule(moduleId = "/api/chargeLog", moduleName = "用户充电记录")
@Api(description = "用户充电记录")
@RestController
@RequestMapping("/api/chargeLog")
public class ChargeLogController extends BaseController {

    @Autowired
    private ChargeLogService chargeLogService;

    /**
     * @Description: 桩站后台 首页查询当日充电账单
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 16:46
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
//    @RequiresPermissions(value = "sys:chargeLog:station:index")
    @ApiOperation(value = "桩站后台 首页查询当日充电账单", notes = "桩站后台 首页查询当日充电账单")
    @RequestMapping(value = "/station/chargeLog", method = RequestMethod.POST)
    public Response chargeLogIndex(
            @RequestBody @ApiParam(required = true) ChargeLogIn chargeLogIn) {

        Map<String, Object> map = new HashMap<>();

        //获取当日时间
        chargeLogIn.setDateTime(DateUtils.getDate("yyyy-MM-dd"));
        //设置显示条数
        chargeLogIn.setLimitNo(5);
        chargeLogIn.setStationId(UserContext.getStationId());
        List<StationChargeOut> result = chargeLogService.getDayChargeLog(chargeLogIn);

        //查询今天总的 充电次数 和统计总共用电度数
        chargeLogIn.setLimitNo(null);
        List<StationChargeOut> allCharge = chargeLogService.getDayChargeLog(chargeLogIn);
        //充电度数
        BigDecimal degree = new BigDecimal(0);
        if(Collections3.isNotEmpty(allCharge)) {
            for (StationChargeOut stationChargeOut : allCharge){
                degree = degree.add(stationChargeOut.getDegreeReal());
            }
        }
        map.put("result", result);
        map.put("size", result.size());
        map.put("degree", degree);
        return success(map);
    }

    /**
     * @Description: 桩站后台 用电账单
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 16:46
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:chargeLog:station:menu")
    @ApiOperation(value = "桩站后台 用电账单", notes = "桩站后台 用电账单")
    @RequestMapping(value = "/station/chargeLogPage", method = RequestMethod.POST)
    public Response<PageInfo<StationChargeOut>> chargeLogPage(
            @RequestBody @ApiParam(required = true) ChargeLogIn chargeLogIn) {
        chargeLogIn.setStationId(UserContext.getStationId());
        PageInfo<StationChargeOut> page = chargeLogService.chargeLogPage(chargeLogIn);

        return success(page);
    }

    /**
     * @Description: 桩站后台 用电账单详情
     * @Author: Linzq
     * @CreateDate:  2019/5/15 0015 16:46
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:chargeLog:station:info")
    @ApiOperation(value = "桩站后台 用电账单详情", notes = "桩站后台 用电账单详情")
    @RequestMapping(value = "/station/chargeLog/info", method = RequestMethod.POST)
    public Response info(
            @RequestBody @ApiParam(required = true) ChargeLogIn chargeLogIn) {

        return success(chargeLogService.getInfo(chargeLogIn.getId()));
    }
}
