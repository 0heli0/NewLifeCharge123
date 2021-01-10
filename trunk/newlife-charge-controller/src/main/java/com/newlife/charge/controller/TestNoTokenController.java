package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.dto.in.StartStopCommandIn;
import com.newlife.charge.service.StationClientGunService;
import com.newlife.charge.service.task.NewLifeChargeTaskScheduler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试用接口
 */
@Api(description = "测试用接口")
@RestController
@RequestMapping("/api/noToken/test")
public class TestNoTokenController extends BaseController {


    @Autowired
    private StationClientGunService stationClientGunService;

    /**
     * 测试下发启动充电命令
     *
     * @return
     */
    @ApiOperation(value = "测试下发启动充电命令", notes = "测试下发启动充电命令")
    @RequestMapping(value = "/startCharge", method = RequestMethod.POST)
    public Response startCharge(@RequestBody @ApiParam(required = true) StartStopCommandIn startStopCommandIn) {
        try {
            String s = stationClientGunService.startCharge(startStopCommandIn);
            LOGGER.info("启动充电结果:"+s);
            return success();
        }catch (Exception e){
            LOGGER.error("启动充电异常",e);
            return error("启动充电异常");
        }
    }


    /**
     * 测试下发启动充电命令
     *
     * @return
     */
    @ApiOperation(value = "测试下发停止充电命令", notes = "测试下发停止充电命令")
    @RequestMapping(value = "/stopCharge", method = RequestMethod.POST)
    public Response stopCharge(@RequestBody @ApiParam(required = true) StartStopCommandIn startStopCommandIn) {
        try {
            String s = stationClientGunService.stopCharge(startStopCommandIn);
            LOGGER.info("测试下发停止充电命令结果:"+s);
            return success();
        }catch (Exception e){
            LOGGER.error("测试下发停止充电命令异常",e);
            return error("测试下发停止充电命令异常");
        }
    }



}
