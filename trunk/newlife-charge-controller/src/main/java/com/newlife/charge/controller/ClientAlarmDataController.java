package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.ClientAlarmDataPageIn;
import com.newlife.charge.core.dto.out.ClientAlarmDataPageOut;
import com.newlife.charge.core.dto.out.StationClientPageOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.ClientAlarmDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 总后台 充电桩故障日志-即充电桩告警记录
 */
@PlatLogModule(moduleId = "/api/clientAlarmData", moduleName = "充电桩故障日志")
@Api(description = "总后台 充电桩故障日志")
@RestController
@RequestMapping("/api/clientAlarmData")
public class ClientAlarmDataController extends BaseController {


    @Autowired
    private ClientAlarmDataService service;

    /**
     * 总后台 分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:clientAlarmData:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<ClientAlarmDataPageOut>> generalPageList(
            @RequestBody @ApiParam(required = true) ClientAlarmDataPageIn pageIn) {
        PageInfo<ClientAlarmDataPageOut> page = new PageInfo<>();
        try {
            page = this.service.page(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general clientAlarmData pageList error", e);
            return error(page);
        }
    }


}
