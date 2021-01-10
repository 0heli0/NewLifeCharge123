package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.UserCouponDetail;
import com.newlife.charge.core.dto.out.UserCouponOut;
import com.newlife.charge.core.dto.out.UserCouponPageOut;
import com.newlife.charge.core.dto.out.UserCouponResult;
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
@PlatLogModule(moduleId = "/api/userCoupon", moduleName = "用户端优惠券展示和领取")
@Api(description = "用户端优惠券展示和领取")
@RestController
@RequestMapping("/api/userCoupon")
public class UserCouponController extends BaseController {

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

        Integer userId = UserContext.getUserId()==null ? 0 : UserContext.getUserId();
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
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        UserCouponOut result = userCouponService.stationPageList(query, userId);

        return success(result);
    }

    /**
     * 领取优惠券操作
     * @param takeIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "领取优惠券操作", notes = "领取优惠券操作")
    @RequestMapping(value = "/car/take", method = RequestMethod.POST)
    public Response take(@RequestBody @Valid @ApiParam TakeCouponIn takeIn, BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId();
        this.userCouponService.take(takeIn, userId);

        return success().setMessage("领取成功");
    }

    /**
     * 通过状态分页查找已领取的优惠券
     * @param queryIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "通过状态分页查找已领取的优惠券", notes = "通过状态分页查找已领取的优惠券")
    @RequestMapping(value = "/car/couponList", method = RequestMethod.POST)
    public Response<PageInfo<UserCouponPageOut>> userCouponPage(@RequestBody @Valid @ApiParam UserCouponListIn queryIn, BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId();
        PageInfo<UserCouponPageOut> page =  this.userCouponService.userCouponPage(queryIn,userId);

        return success(page);
    }

    /**
     * 查看已经领取的优惠券的详情
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "查看已经领取的优惠券的详情", notes = "查看已经领取的优惠券的详情")
    @RequestMapping(value = "/car/detail", method = RequestMethod.POST)
    public Response<UserCouponDetail> userCouponDetail(@RequestBody @Valid @ApiParam InfoIn infoIn, BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);

        UserCouponDetail result =  this.userCouponService.userCouponDetail(infoIn);

        return success(result);
    }


}
