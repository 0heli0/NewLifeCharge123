package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.dto.in.InfoIn;
import com.newlife.charge.core.dto.out.ClientStationDetailOut;
import com.newlife.charge.core.dto.out.ClientStationTimePriceOut;
import com.newlife.charge.core.dto.out.ClientTruckSpaceOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.ClientStationService;
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
 * 车主端 桩站信息管理
 */
@PlatLogModule(moduleId = "/api/noToken/clientStation", moduleName = "车主端-桩站信息管理（noToken）")
@Api(description = "车主端 桩站信息管理（不需要token接口）")
@RestController
@RequestMapping("/api/noToken/clientStation")
public class ClientStationNoTokenController extends BaseController {

    @Autowired
    private ClientStationService clientStationService;

    /**
     * 车主端 查看桩站车位列表
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站车位列表", notes = "桩站车位列表")
    @RequestMapping(value = "/car/truckSpaceList", method = RequestMethod.POST)
    public Response<List<ClientTruckSpaceOut>> truckSpaceList (
            @RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        List<ClientTruckSpaceOut> result = this.clientStationService.truckSpaceList(infoIn);

        return success(result);
    }

    /**
     * 车主端 电站详情
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "电站详情", notes = "电站详情")
    @RequestMapping(value = "/car/stationDetail", method = RequestMethod.POST)
    public Response<ClientStationDetailOut> stationDetail(
            @RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        ClientStationDetailOut detail = clientStationService.stationDetail(infoIn);

        return success(detail);
    }

    /**
     * 车主端 时段电价介绍
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "时段电价介绍", notes = "时段电价介绍")
    @RequestMapping(value = "/car/timePriceDetail", method = RequestMethod.POST)
    public Response<List<ClientStationTimePriceOut>> timePriceDetail(
            @RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        List<ClientStationTimePriceOut> result = this.clientStationService.timePriceDetail(infoIn);

        return success(result);
    }


}
