package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.CouponOut;
import com.newlife.charge.core.dto.out.CouponUsePageOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.CouponService;
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
 * 优惠券接口
 */
@PlatLogModule(moduleId = "/api/coupon", moduleName = "通用优惠券管理")
@Api(description = "优惠券管理")
@RestController
@RequestMapping("/api/coupon")
public class CouponController extends BaseController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserCouponService userCouponService;

    /**
     * 新增通用优惠券(分为充值优惠券和用电优惠券,
     * 由于保存数据基本一致,指定桩站的用电优惠券只需另外保存,故通过参数判断,不分开写接口)
     *
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:coupon:general:create")
    @ApiOperation(value = "新增充值通用优惠券", notes = "新增充值通用优惠券")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response save(@RequestBody @Valid @ApiParam CouponSaveIn saveIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.couponService.save(saveIn);

        return success().setMessage("新增成功");
    }

    /**
     * 根据条件分页查询
     *
     * @param queryIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:coupon:general:menu")
    @ApiOperation(value = "根据条件分页查询", notes = "根据条件分页查询")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<CouponOut>> page(@RequestBody @Valid @ApiParam CouponQueryIn queryIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        PageInfo<CouponOut> page = this.couponService.page(queryIn);

        return success(page);
    }

    /**
     * 查询优惠券详情
     *
     * @param queryIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:coupon:general:info")
    @ApiOperation(value = "查询优惠券详情", notes = "查询优惠券详情")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<CouponOut> info(@RequestBody @Valid @ApiParam InfoIn queryIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        CouponOut out = this.couponService.info(queryIn.getId());

        return success(out);
    }

    /**
     * 查询优惠券使用情况
     *
     * @param queryIn
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "查询优惠券使用情况", notes = "查询优惠券使用情况")
    @RequestMapping(value = "/general/useList", method = RequestMethod.POST)
    public Response<PageInfo<CouponUsePageOut>> useList(@RequestBody @Valid @ApiParam CouponUseQueryIn queryIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        PageInfo<CouponUsePageOut> page = this.userCouponService.usePage(queryIn);

        return success(page);
    }

//    /**
//     *更新优惠券
//     * @param updateIn
//     * @param bindingResult
//     * @return
//     */
//    @Deprecated
////    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
////    @RequiresPermissions(value = "sys:coupon:general:update")
//    @ApiOperation(value = "更新优惠券", notes = "更新优惠券")
//    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
//    public Response update(@RequestBody @Valid @ApiParam CouponUpdateIn updateIn, BindingResult bindingResult){
//
//        throwBindingResultErrorMsg(bindingResult);
//
//         this.couponService.update(updateIn);
//
//        return success().setMessage("更新成功");
//    }

    /**
     * 优惠券下架
     *
     * @param delIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SOLD_OUT)
    @ApiOperation(value = "优惠券下架", notes = "优惠券下架")
    @RequestMapping(value = "/general/out", method = RequestMethod.POST)
    public Response out(@RequestBody @Valid @ApiParam DelIn delIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.couponService.out(delIn);

        return success().setMessage("下架成功");
    }

    /**
     * 根据id删除
     *
     * @param delIn
     * @param bindingResult
     * @return
     */
    @Deprecated
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:coupon:general:delete")
    @ApiOperation(value = "根据id删除", notes = "根据id删除")
    @RequestMapping(value = "/general/delete", method = RequestMethod.POST)
    public Response deleteById(@RequestBody @Valid @ApiParam DelIn delIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.couponService.deleteById(delIn);

        return success().setMessage("删除成功");
    }

    /**
     * 根据id批量删除
     *
     * @param delsIn
     * @param bindingResult
     * @return
     */
    @Deprecated
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:coupon:general:delete")
    @ApiOperation(value = "根据id批量删除", notes = "根据id批量删除")
    @RequestMapping(value = "/general/batchDelete", method = RequestMethod.POST)
    public Response deleteByIds(@RequestBody @Valid @ApiParam DelsIn delsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.couponService.deleteByIds(delsIn);

        return success().setMessage("批量删除成功");
    }

    /**
     * 新增桩站的用电优惠券
     *
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:coupon:station:create")
    @ApiOperation(value = "新增桩站的用电优惠券", notes = "新增桩站的用电优惠券")
    @RequestMapping(value = "/station/save", method = RequestMethod.POST)
    public Response stationSave(@RequestBody @Valid @ApiParam CouponStationSaveIn saveIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.couponService.stationSave(saveIn);

        return success().setMessage("新增成功");
    }

    /**
     * 桩站 用电优惠券列表
     *
     * @param queryIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:coupon:station:menu")
    @ApiOperation(value = "用电优惠券列表", notes = "用电优惠券列表")
    @RequestMapping(value = "/station/couponList", method = RequestMethod.POST)
    public Response stationCouponList(@RequestBody @Valid @ApiParam CouponStationQueryIn queryIn) {

        queryIn.setStationId(UserContext.getStationId());

        return success(couponService.pageList(queryIn)).setMessage("查询成功");
    }

    /**
     * 根据id删除
     *
     * @param delIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:coupon:station:delete")
    @ApiOperation(value = "根据id删除", notes = "根据id删除")
    @RequestMapping(value = "/station/delete", method = RequestMethod.POST)
    public Response stationDeleteById(@RequestBody @Valid @ApiParam DelIn delIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.couponService.deleteById(delIn);

        return success().setMessage("删除成功");
    }

    /**
     * 根据id批量删除
     *
     * @param delsIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:coupon:station:delete")
    @ApiOperation(value = "根据id批量删除", notes = "根据id批量删除")
    @RequestMapping(value = "/station/batchDelete", method = RequestMethod.POST)
    public Response stationDeleteByIds(@RequestBody @Valid @ApiParam DelsIn delsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.couponService.deleteByIds(delsIn);

        return success().setMessage("批量删除成功");
    }

    /**
     * 查询优惠券详情
     *
     * @param queryIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:coupon:station:info")
    @ApiOperation(value = "查询优惠券详情", notes = "查询优惠券详情")
    @RequestMapping(value = "/station/info", method = RequestMethod.POST)
    public Response<CouponOut> stationInfo(@RequestBody @Valid @ApiParam InfoIn queryIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        CouponOut out = this.couponService.stationInfo(queryIn.getId());

        return success(out);
    }
}
