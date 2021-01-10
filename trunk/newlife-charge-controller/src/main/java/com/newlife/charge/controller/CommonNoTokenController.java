package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.enums.*;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.service.StationInfoService;
import com.newlife.charge.service.impl.StationInfoServiceImpl;
import com.newlife.charge.service.task.NewLifeChargeTaskScheduler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 一些公用的不需要token的接口
 */
@Api(description = "一些公用的不需要token的接口")
@RestController
@RequestMapping("/api/noToken/common")
public class CommonNoTokenController extends BaseController {



    @Autowired
    private NewLifeChargeTaskScheduler taskScheduler;

    /**
     * TODO 测试卖电账单定时任务,一天执行一次,上线时需要删除此接口
     *
     * @return
     */
    @Deprecated
    @ApiOperation(value = "测试卖电账单定时任务", notes = "测试卖电账单定时任务")
    @RequestMapping(value = "/test/commissionSettlement", method = RequestMethod.GET)
    public Response testCommissionSettlement() {
        try {
            taskScheduler.commissionSettlement();
            return success();
        }catch (Exception e){
            LOGGER.error("卖电账单定时任务异常",e);
            return error("卖电账单定时任务异常");
        }
    }


    /**
     * 新活资金统计类型
     *
     * @return
     */
    @ApiOperation(value = "新活资金统计类型", notes = "新活资金统计类型")
    @RequestMapping(value = "/general/logType", method = RequestMethod.POST)
    public Response<Map<Integer, String>> generalLogType() {
        return success(NewLifeSpendLogTypeEnum.toMap());
    }


    /**
     * 新活资金统计类型备注
     *
     * @return
     */
    @ApiOperation(value = "新活资金统计类型备注", notes = "新活资金统计类型备注")
    @RequestMapping(value = "/general/logTypeRemark", method = RequestMethod.POST)
    public Response<Map<Integer, String>> generalLogTypeRemark() {
        return success(NewLifeSpendLogTypeRemarkEnum.toMap());
    }


    /**
     * (车主)用户资金统计类型
     *
     * @return
     */
    @ApiOperation(value = "(车主)用户资金统计类型", notes = "(车主)用户资金统计类型")
    @RequestMapping(value = "/general/carUserLogType", method = RequestMethod.POST)
    public Response<Map<Integer, String>> generalCarUserLogType() {
        return success(NewLifeUserSpendLogTypeEnum.toMap());
    }


    /**
     * (车主)用户资金统计类型备注
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "(车主)用户资金统计类型备注", notes = "(车主)用户资金统计类型备注")
    @RequestMapping(value = "/general/carUserLogTypeRemark", method = RequestMethod.POST)
    public Response<Map<Integer, String>> generalCarUserLogTypeRemark() {
        return success(NewLifeUserSpendLogTypeRemarkEnum.toMap());
    }


    /**
     * 桩站资金统计类型
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站资金统计类型", notes = "桩站资金统计类型")
    @RequestMapping(value = "/general/stationLogType", method = RequestMethod.POST)
    public Response<Map<Integer, String>> generalStationLogType() {
        return success(NewLifeStationSpendLogTypeEnum.toMap());
    }


    /**
     * 桩站资金统计类型备注
     *
     * @return
     */
    @ApiOperation(value = "桩站资金统计类型备注", notes = "桩站资金统计类型备注")
    @RequestMapping(value = "/general/stationTypeRemark", method = RequestMethod.POST)
    public Response<Map<Integer, String>> generalStationLogTypeRemark() {
        return success(NewLifeStationSpendLogTypeRemarkEnum.toMap());
    }


    /**
     * 充电枪状态
     *
     * @return
     */
    @ApiOperation(value = "充电枪状态", notes = "充电枪状态")
    @RequestMapping(value = "/general/stationClientGunStatus", method = RequestMethod.POST)
    public Response<Map<Integer, String>> generalStationClientGunStatus() {
        return success(GunStatusEnum.toMap());
    }


}
