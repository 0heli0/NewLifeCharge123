package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.UserCouponDetail;
import com.newlife.charge.core.dto.out.UserCouponOut;
import com.newlife.charge.core.dto.out.UserCouponPageOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.UserCouponService;
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
 * 用户端优惠券展示和领取接口
 */
@PlatLogModule(moduleId = "/api/noToken/userCoupon", moduleName = "用户端优惠券展示和领取（不需要token）")
@Api(description = "用户端优惠券展示和领取（不需要token）")
@RestController
@RequestMapping("/api/noToken/userCoupon")
public class UserCouponNotokenController extends BaseController {

    @Autowired
    private UserCouponService userCouponService;

    /**
     * 点击图标/首页天降神券功能,展示的可领优惠券列表
     * @param query
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "点击图标/首页天降神券功能,展示的可领优惠券列表", notes = "点击图标/首页天降神券功能,展示的可领优惠券列表")
    @RequestMapping(value = "/car/pageList", method = RequestMethod.POST)
    public Response<UserCouponOut> page(@RequestBody @Valid @ApiParam UserCouponQueryIn query, BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);

        Integer userId = 0;
        UserCouponOut result = userCouponService.pageList(query, userId);

        return success(result);
    }

    /**
     * 桩站详情页天降神券功能
     * @param query
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站详情展示的可领优惠券列表", notes = "桩站详情展示的可领优惠券列表")
    @RequestMapping(value = "/car/StationAutoList", method = RequestMethod.POST)
    public Response<UserCouponOut> StationAutoList(@RequestBody @Valid @ApiParam UserStationCouponQueryIn query,
                                                   BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);
        Integer userId = 0;
        UserCouponOut result = userCouponService.stationPageList(query, userId);

        return success(result);
    }


}
