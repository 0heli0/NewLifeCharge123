package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.InfoIn;
import com.newlife.charge.core.dto.in.OperationLogQueryIn;
import com.newlife.charge.core.dto.out.OperationLogInfoOut;
import com.newlife.charge.core.dto.out.OperationLogPageOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.OperationLogService;
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
 * 操作日志/系统日志
 */
@Api(description = "系统日志")
@RestController
@RequestMapping("/api/operationLog")
public class OperationLogController extends BaseController {

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 操作日志分页查询
     *
     * @param queryIn
     * @return
     */
    @RequiresPermissions(value = "sys:operationLog:general:menu")
    @ApiOperation(value = "操作日志分页查询", notes = "操作日志分页查询")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<OperationLogPageOut>> delete(@RequestBody @ApiParam(required = true) OperationLogQueryIn queryIn) {
        PageInfo<OperationLogPageOut> result = operationLogService.page(queryIn);
        return success(result);
    }


    /**
     * 操作日志详情查看
     *
     * @param infoIn
     * @return
     */
    @ApiOperation(value = "操作日志详情查看", notes = "操作日志详情查看")
    @RequestMapping(value = "/general/detailInfo", method = RequestMethod.POST)
    public Response<OperationLogInfoOut> detailInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);
        OperationLogInfoOut result = operationLogService.info(infoIn.getId());
        return success(result);
    }

}
