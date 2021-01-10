package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.StationParkingLotStatusEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.ERROR;
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

/**
 * 车主端 桩站信息管理
 */
@PlatLogModule(moduleId = "/api/clientStation", moduleName = "车主端 桩站信息管理")
@Api(description = "车主端 桩站信息管理")
@RestController
@RequestMapping("/api/clientStation")
public class ClientStationController extends BaseController {

    @Autowired
    private ClientStationService clientStationService;


    /**
     * 车主端 桩站车位详情
     * @param infoIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站车位详情", notes = "桩站车位详情")
    @RequestMapping(value = "/car/truckSpaceDetail", method = RequestMethod.POST)
    public Response<ClientTruckSpaceOut> truckSpaceDetail (
            @RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();

        ClientTruckSpaceOut result = this.clientStationService.truckSpaceDetail(infoIn,userId);

        return success(result);
    }

    /**
     * 车主端 选定车位操作及返回结果
     * @param infoIn 车位id
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "选定车位操作及返回结果", notes = "选定车位操作及返回结果")
    @RequestMapping(value = "/car/bindingParking", method = RequestMethod.POST)
    public Response<BindingParkingOut> bindingParking(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult){
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();

        BindingParkingOut out = this.clientStationService.bindingParking(infoIn, userId);

        return success(out);
    }

    /**
     * 车主端 取消选定车位
     * @param infoIn 车位id
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "车主端 取消选定车位", notes = "车主端 取消选定车位")
    @RequestMapping(value = "/car/cancelBinding", method = RequestMethod.POST)
    public Response cancelBinding(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult){
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        Integer result = this.clientStationService.cancelBinding(infoIn, userId);

        if(result == StationParkingLotStatusEnum.FREE.getValue()) {
            return success().setMessage("取消成功");
        }
        return error(ERROR.RESULT_NULL);
    }

    /**
     * 车主端 插入充电枪后操作
     * @param infoIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "插入充电枪后操作", notes = "插入充电枪后操作")
    @RequestMapping(value = "/car/plugInGun", method = RequestMethod.POST)
    public Response<PlugInGunOut> plugInGun(@RequestBody @Valid @ApiParam(required = true) PlugInGunIn infoIn,
                                            BindingResult bindingResult){
        throwBindingResultErrorMsg(bindingResult);
        this.clientStationService.plugInGun(infoIn);
        return success(new PlugInGunOut());
    }


    /**
     * 车主端 确认充电操作
     * @param infoIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "确认充电操作", notes = "确认充电操作")
    @RequestMapping(value = "/car/chargeBegin", method = RequestMethod.POST)
    public Response<ChargeBeginOut> chargeBegin(@RequestBody @Valid @ApiParam(required = true) ChargeParamsIn infoIn,
                                                BindingResult bindingResult){
        throwBindingResultErrorMsg(bindingResult);
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        ChargeBeginOut out = this.clientStationService.chargeBegin(infoIn, userId);
        return success(out);
    }

    /**
     * 车主端 手动停止充电
     * @param infoIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "手动停止充电", notes = "手动停止充电")
    @RequestMapping(value = "/car/stopCharge", method = RequestMethod.POST)
    public Response<ChargeBeginOut> stopCharge(@RequestBody @Valid @ApiParam(required = true) ChargeParamsIn infoIn,
                                                BindingResult bindingResult){
        throwBindingResultErrorMsg(bindingResult);
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        this.clientStationService.stopCharge(infoIn, userId, 0);
        return success().setMessage("请求成功");
    }

    /**
     * 车主端 充电时获取实时充电数据
     * @param infoIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "充电时获取实时充电数据", notes = "充电时获取实时充电数据")
    @RequestMapping(value = "/car/getTimeData", method = RequestMethod.POST)
    public Response<ChargeBeginOut> getTimeData(@RequestBody @Valid @ApiParam(required = true) ChargeTimeDataIn infoIn,
                                                   BindingResult bindingResult){
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();

        ChargeBeginOut out = this.clientStationService.getTimeData(infoIn, userId);

        return success(out);
    }

    /**
     * 车主端 首页跳转到实时充电数据页面
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "首页跳转到实时充电数据页面", notes = "首页跳转到实时充电数据页面")
    @RequestMapping(value = "/car/jumpToTimeData", method = RequestMethod.POST)
    public Response<ChargeBeginOut> jumpToTimeData(@RequestBody @Valid @ApiParam(required = true) ChargeStatusOut infoIn,
                                                   BindingResult bindingResult){
        throwBindingResultErrorMsg(bindingResult);
        ChargeTimeDataIn dataIn = new ChargeTimeDataIn();
        dataIn.setGunId(infoIn.getClientGunId());
        dataIn.setLotId(infoIn.getParkingLotId());
        dataIn.setOrderSn(infoIn.getOrderSn());
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        ChargeBeginOut out = this.clientStationService.getTimeData(dataIn, userId);
        return success(out);

    }

    /**
     * 车主端 首页查看充电状态
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "首页查看充电状态", notes = "首页查看充电状态")
    @RequestMapping(value = "/car/chargeStatus", method = RequestMethod.POST)
    public Response<ChargeStatusOut> chargeStatus() {

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();

        ChargeStatusOut out = this.clientStationService.chargeStatus(userId);
        if(out != null){
            return success(out);
        }
        return success().setMessage("没有数据");

    }

    /**
     * 车主端 跳转到用户选定车位界面
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "跳转到用户选定车位界面", notes = "跳转到用户选定车位界面")
    @RequestMapping(value = "/car/jumpToTruckSpaceDetail", method = RequestMethod.POST)
    public Response<ClientTruckSpaceOut> jumpToTruckSpaceDetail() {

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();

        ClientTruckSpaceOut out = this.clientStationService.jumpToTruckSpaceDetail(userId);
        if(out != null){
            return success(out);
        }
        return error(ERROR.DATA_NOT_FOUND);

    }

    /**
     * 车主端 查看充电结束后数据
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "查看充电结束后数据", notes = "查看充电结束后数据")
    @RequestMapping(value = "/car/getChargeLog", method = RequestMethod.POST)
    public Response<ChargeEndOut> getChargeLogData(@RequestBody @Valid @ApiParam(required = true) ChargeLogDataIn infoIn,
                                                      BindingResult bindingResult){
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();

        ChargeEndOut out = this.clientStationService.getChargeLogData(infoIn.getOrderSn(), userId);
        if(out != null){
            return success(out);
        }
        return error(ERROR.DATA_NOT_FOUND);

    }



}
