package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.domain.page.Pageable;
import com.newlife.charge.core.dto.in.GeneralizeStationLogDelIn;
import com.newlife.charge.core.dto.in.GeneralizeStationLogDelsIn;
import com.newlife.charge.core.dto.in.GeneralizeStationLogQueryIn;
import com.newlife.charge.core.dto.in.GeneralizeStationLogUpdateIn;
import com.newlife.charge.core.dto.out.GeneralizeStationLogOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.GeneralizeStationLogService;
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
 * 推广建站记录
 */
@PlatLogModule(moduleId = "/api/generalizeStation", moduleName = "推广建站记录")
@Api(description = "推广建站记录")
@RestController
@RequestMapping("/api/generalizeStation")
public class GeneralizeStationLogController extends BaseController {

    @Autowired
    private GeneralizeStationLogService stationLogService;

    /**
     * 根据条件分页查询建站推广人记录
     * @param queryIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:generalizeStation:general:menu")
    @ApiOperation(value = "根据条件分页查询", notes = "根据条件分页查询")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralizeStationLogOut>> page(@RequestBody @Valid @ApiParam(required = true) GeneralizeStationLogQueryIn queryIn
            , BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        PageInfo<GeneralizeStationLogOut> result = this.stationLogService.page(queryIn,queryIn.pageNo,queryIn.getPageSize());

        return success(result);
    }


    /**
     * 根据条件删除建站推广人记录
     * @param delIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:generalizeStation:general:delete")
    @ApiOperation(value = "根据条件删除建站推广人记录", notes = "根据条件删除建站推广人记录")
    @RequestMapping(value = "/general/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody @Valid @ApiParam(required = true) GeneralizeStationLogDelIn delIn
            , BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.stationLogService.delete(delIn);

        return success().setMessage("删除成功");
    }

    /**
     * 批量删除建站推广人记录
     * @param delsIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:generalizeStation:general:delete")
    @ApiOperation(value = "批量删除建站推广人记录", notes = "批量删除建站推广人记录")
    @RequestMapping(value = "/general/batchDelete", method = RequestMethod.POST)
    public Response batchDelete(@RequestBody @Valid @ApiParam(required = true) GeneralizeStationLogDelsIn delsIn
            , BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.stationLogService.deleteByIds(delsIn);

        return success().setMessage("删除成功");
    }

}
