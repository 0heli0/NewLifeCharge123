package com.newlife.charge.controller.api.rpc;


import com.newlife.charge.controller.BaseController;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.exModel.StationClientEx;
import com.newlife.charge.core.domain.exModel.StationClientGunEx;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.enums.GunStatusEnum;
import com.newlife.charge.core.enums.PileStatusEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.ClientAlarmDataService;
import com.newlife.charge.service.ClientHistoryDataService;
import com.newlife.charge.service.StationClientGunService;
import com.newlife.charge.service.StationClientService;
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
 * RPC 硬件对接
 */
@PlatLogModule(moduleId = "/api/rpc/stationClient", moduleName = "RPC硬件对接")
@Api(description = "RPC硬件对接")
@RestController
@RequestMapping("/api/rpc/stationClient")
public class RpcStationClientController extends BaseController {


    @Autowired
    private StationClientService stationClientService;


    @Autowired
    private StationClientGunService stationClientGunService;
    @Autowired
    private ClientHistoryDataService clientHistoryDataService;
    @Autowired
    private ClientAlarmDataService clientAlarmDataService;


    /**
     * 获取所有充电桩编号
     *
     * @return
     */
//    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "RPC获取所有充电桩编号", notes = "RPC获取所有充电桩编号")
    @RequestMapping(value = "/getAllStationClientCode", method = RequestMethod.POST)
    public Response<List<String>> getAllStationClientCode() {
        List<String> allStationClientCodeList = this.stationClientService.getAllStationClientCode();
        return success(allStationClientCodeList);
    }


    /**
     * 充电桩硬件登录
     * 更新充电桩下的枪状态为空闲
     *
     * @param loginIn
     * @return
     */
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "RPC充电桩硬件登录", notes = "RPC充电桩硬件登录")
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public Response login(@RequestBody @Valid @ApiParam(required = true) RpcChargePileInfoIn loginIn,
                          BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        //充电桩信息是否存在
        StationClientEx stationClientEx = this.stationClientService.getByCode(loginIn.getStationClientTerminalNo());
        if (stationClientEx == null) {
            throw new BizException(ERROR.STATION_CLIENT_INFO_NOT_EXIST);
        }
        int i = this.stationClientGunService.updateStatusByStationClientId(stationClientEx.getId(), GunStatusEnum.FREE.getValue());
        if (i > 0) {
            return success();
        } else {
            return error("充电桩登录时更新枪状态失败");
        }
    }


    /**
     * 充电桩硬件退出
     * 更新充电桩下的枪状态为离线
     *
     * @param logoutIn
     * @return
     */
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "RPC充电桩硬件退出", notes = "RPC充电桩硬件退出")
    @RequestMapping(value = "/doLogout", method = RequestMethod.POST)
    public Response logout(@RequestBody @Valid @ApiParam(required = true) RpcChargePileInfoIn logoutIn,
                           BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        //充电桩信息是否存在
        StationClientEx stationClientEx = this.stationClientService.getByCode(logoutIn.getStationClientTerminalNo());
        if (stationClientEx == null) {
            throw new BizException(ERROR.STATION_CLIENT_INFO_NOT_EXIST);
        }

        Integer stationClientId = stationClientEx.getId();

        //充电枪是否存在
        StationClientGunEx stationClientGunEx = this.stationClientGunService.getByCode(logoutIn.getGunNo(), stationClientId);
        if (stationClientGunEx == null) {
            throw new BizException(ERROR.STATION_CLIENT_GUN_INFO_NOT_EXIST);
        }

        int i = this.stationClientGunService.updateStatus(stationClientGunEx.getId(), GunStatusEnum.OFFLINE.getValue(), null);
        return success();
    }


    /**
     * RPC更新充电枪状态
     *
     * @param updateIn
     * @return
     */
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "RPC更新充电枪状态", notes = "RPC更新充电枪状态")
    @RequestMapping(value = "/updateGunStatus", method = RequestMethod.POST)
    public Response updateGunStatus(@RequestBody @Valid @ApiParam(required = true) RpcStationClientGunStatusUpdateIn updateIn,
                                    BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);
        StationClientEx stationClientEx = this.stationClientService.getByCode(updateIn.getStationClientTerminalNo());
        if (stationClientEx == null) {
            throw new BizException(ERROR.STATION_CLIENT_INFO_NOT_EXIST);
        }

        Integer stationClientId = stationClientEx.getId();

        StationClientGunEx stationClientGunEx = this.stationClientGunService.getByCode(updateIn.getGunNo(), stationClientId);
        if (stationClientGunEx == null) {
            throw new BizException(ERROR.STATION_CLIENT_GUN_INFO_NOT_EXIST);
        }

        int i = this.stationClientGunService.updateStatus(stationClientGunEx.getId(), updateIn.getStatus(), null);
        if (i > 0) {
            return success().setMessage("更新成功");
        } else {
            return error("更新失败");
        }
    }


    /**
     * RPC更新充电桩下所有充电枪状态
     *
     * @param updateIn
     * @return
     */
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "RPC更新充电桩下所有充电枪状态", notes = "RPC更新充电桩下所有充电枪状态")
    @RequestMapping(value = "/updateGunStatusByClientCodeList", method = RequestMethod.POST)
    public Response updateGunStatusByClientCodeList(@RequestBody @Valid @ApiParam(required = true) RpcStationClientStatusUpdateIn updateIn,
                                                    BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        List<String> stationClientCodeList = updateIn.getStationClientCodeList();
        if (stationClientCodeList != null && stationClientCodeList.size() > 0) {
            int i = this.stationClientGunService.updateStatusByClientCodeList(updateIn.getStationClientCodeList(), updateIn.getStatus());
            if (i > 0) {
                return success().setMessage("更新成功");
            } else {
                return error("更新失败");
            }
        }
        return error("没有需要更新的数据");
    }


    /**
     * RPC处理充电桩上报的实时数据
     * 1.更新枪状态
     *
     * @param pileUploadRealTimeDataIn
     * @return
     */
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "RPC处理充电桩上报的实时数据", notes = "RPC处理充电桩上报的实时数据")
    @RequestMapping(value = "/dealPileRealTimeData", method = RequestMethod.POST)
    public Response dealPileRealTimeData(@RequestBody @ApiParam(required = true) PileUploadRealTimeDataIn pileUploadRealTimeDataIn,
                                         BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        PileRealTimeDataIn pileRealTimeDataIn = pileUploadRealTimeDataIn.getPileRealTimeDataIn();

        StationClientEx stationClientEx = this.stationClientService.getByCode(pileRealTimeDataIn.getStationClientCode());
        if (stationClientEx == null) {
            throw new BizException(ERROR.STATION_CLIENT_INFO_NOT_EXIST);
        }

        Integer stationClientId = stationClientEx.getId();

        StationClientGunEx stationClientGunEx = this.stationClientGunService.getByCode(pileRealTimeDataIn.getGunCode(), stationClientId);
        if (stationClientGunEx == null) {
            throw new BizException(ERROR.STATION_CLIENT_GUN_INFO_NOT_EXIST);
        }

        String pileStatus = pileRealTimeDataIn.getPileStatus();//硬件枪状态
        Integer status = GunStatusEnum.OFFLINE.getValue();//默认离线

        if (PileStatusEnum.FREE.getValue().equals(pileStatus)) {
            //空闲状态
            status = GunStatusEnum.FREE.getValue();
        } else if (PileStatusEnum.CONNECTING.getValue().equals(pileStatus)) {
            //连接中
            status = GunStatusEnum.LINKING.getValue();
        } else if (PileStatusEnum.CHARGING.getValue().equals(pileStatus)) {
            //充电中
            status = GunStatusEnum.CHARGING.getValue();
        } else if (PileStatusEnum.COMPLETED.getValue().equals(pileStatus)) {
            //充电完成
            //TODO:硬件充电完成->总后台充电中
            status = GunStatusEnum.CHARGING.getValue();
        } else if (PileStatusEnum.APPOINTMENT.getValue().equals(pileStatus)) {
            //被预约
            status = GunStatusEnum.BOOKED.getValue();
        } else if (PileStatusEnum.QUEUE.getValue().equals(pileStatus)) {
            //排队中
            status = GunStatusEnum.LINE.getValue();
        } else {
            LOGGER.error("未知硬件状态：" + pileStatus);
        }

        int i = this.stationClientGunService.updateStatus(stationClientGunEx.getId(), status, pileRealTimeDataIn);

        if (i > 0) {
            return success().setMessage("枪实时数据处理成功");
        } else {
            return error("枪实时数据处理失败");
        }
    }


    /**
     * RPC处理历史数据-硬件上传的充电账单
     *
     * @param pileUploadHistoryDataIn
     * @return
     */
    @ApiOperation(value = "RPC处理历史数据-硬件上传的充电账单", notes = "RPC处理历史数据-硬件上传的充电账单")
    @RequestMapping(value = "/dealPileHistoryData", method = RequestMethod.POST)
    public Response dealPileHistoryData(@RequestBody @ApiParam(required = true) PileUploadHistoryDataIn pileUploadHistoryDataIn,
                                        BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);


        int i = this.clientHistoryDataService.dealPileHistoryData(pileUploadHistoryDataIn.getPileHistoryDataIn());

        if (i > 0) {
            return success().setMessage("RPC处理历史数据成功");
        } else {
            return error("RPC处理历史数据失败");
        }
    }


    /**
     * RPC处理硬件上报的告警记录数据
     *
     * @param pileUploadAlarmDataIn
     * @return
     */
    @ApiOperation(value = "RPC处理硬件上报的告警记录数据", notes = "RPC处理硬件上报的告警记录数据")
    @RequestMapping(value = "/dealPileAlarmData", method = RequestMethod.POST)
    public Response dealPileHistoryData(@RequestBody @ApiParam(required = true) PileUploadAlarmDataIn pileUploadAlarmDataIn,
                                        BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);
        int i = this.clientAlarmDataService.dealPileAlarmData(pileUploadAlarmDataIn.getPileAlarmDataIn());
        if (i > 0) {
            return success().setMessage("RPC处理硬件上报的告警记录数据成功");
        } else {
            return error("RPC处理硬件上报的告警记录数据失败");
        }
    }


}
