package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.domain.page.Pageable;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.PreChargeOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.PreChargeService;
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
import java.util.List;

/**
 * 总后台充值设置
 */
@PlatLogModule(moduleId = "/api/preCharge", moduleName = "充值设置")
@Api(description = "充值设置")
@RestController
@RequestMapping("/api/preCharge")
public class PreChargeController extends BaseController {

    @Autowired
    private PreChargeService preChargeService;

    /**
     * 根据参数分页查询
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:preCharge:general:menu")
    @ApiOperation(value = "根据参数分页查询", notes = "根据参数分页查询")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<PreChargeOut>> page(@RequestBody @ApiParam Pageable pageIn) {

        PageInfo<PreChargeOut> pageInfo = preChargeService.page(pageIn.getPageNo(), pageIn.getPageSize());

        return success(pageInfo);
    }

    /**
     * 车主端 根据参数分页查询
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "车主端 根据参数分页查询", notes = "车主端 根据参数分页查询")
    @RequestMapping(value = "/car/userPageList", method = RequestMethod.POST)
    public Response<List<PreChargeOut>> userPage() {

        List<PreChargeOut> pageInfo = preChargeService.preChargeList();

        return success(pageInfo);
    }

    /**
     * 总后台 编辑时数据回填接口
     *
     * @param infoIn
     * @return
     */
    @RequiresPermissions(value = "sys:preCharge:general:update")
    @ApiOperation(value = "查询详情", notes = "查询详情")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<PreChargeOut> info(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        PreChargeOut info = preChargeService.info(infoIn.getId());

        return success(info);
    }


    /**
     * 新增预充值金额
     *
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:preCharge:general:create")
    @ApiOperation(value = "新增预充值金额", notes = "新增预充值金额")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response save(@RequestBody @Valid @ApiParam PreChargeSaveIn saveIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        preChargeService.insertOne(saveIn);

        return success().setMessage("设置成功");
    }

//    /**
//     *批量添加预充值金额
//     * @param saveIn
//     * @return
//     */
//    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
//    @RequiresPermissions(value = "sys:preCharge:general:create")
//    @ApiOperation(value = "批量添加预充值金额", notes = "批量添加预充值金额")
//    @RequestMapping(value = "/general/batchSave", method = RequestMethod.POST)
//    public Response batchSave(@RequestBody @Valid @ApiParam PreChargeBatchSaveIn saveIn, BindingResult bindingResult){
//
//        throwBindingResultErrorMsg(bindingResult);
//
//        this.preChargeService.batchSave(saveIn);
//
//
//        return success().setMessage("批量设置成功");
//    }

    /**
     * 更新
     *
     * @param updateIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @RequiresPermissions(value = "sys:preCharge:general:update")
    @ApiOperation(value = "预充值更新", notes = "预充值更新")
    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
    public Response update(@RequestBody @Valid @ApiParam PreChargeUpdateIn updateIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        preChargeService.update(updateIn);

        return success().setMessage("修改成功");
    }

    /**
     * 删除
     *
     * @param deleteIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:preCharge:general:delete")
    @ApiOperation(value = "根据id删除", notes = "根据id删除")
    @RequestMapping(value = "/general/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody @Valid @ApiParam PreChargeDeleteIn deleteIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        preChargeService.delete(deleteIn);

        return success().setMessage("删除成功");
    }

    /**
     * 批量删除
     *
     * @param deleteIdsIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:preCharge:general:delete")
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @RequestMapping(value = "/general/deleteIds", method = RequestMethod.POST)
    public Response deleteByIds(@RequestBody @Valid @ApiParam PreChargeDeleteIdsIn deleteIdsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        preChargeService.deleteByIds(deleteIdsIn);

        return success().setMessage("批量删除成功");
    }

}
