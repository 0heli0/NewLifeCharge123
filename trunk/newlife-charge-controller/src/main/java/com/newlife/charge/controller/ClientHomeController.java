package com.newlife.charge.controller;


import com.google.common.collect.Lists;
import com.newlife.charge.common.RedisUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.Order;
import com.newlife.charge.core.domain.StationClientGun;
import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.StationHomeDetailOut;
import com.newlife.charge.core.dto.out.StationHomeListOut;
import com.newlife.charge.core.dto.out.StationHomeMapListOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.dao.OrderMapper;
import com.newlife.charge.dao.StationClientGunMapper;
import com.newlife.charge.dao.UserMapper;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.security.JwtTokenManager;
import com.newlife.charge.security.TokenPrincipal;
import com.newlife.charge.service.ClientHistoryDataService;
import com.newlife.charge.service.ClientHomeService;
import com.newlife.charge.service.StationClientGunService;
import com.newlife.charge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * 车主端 首页功能
 */
@PlatLogModule(moduleId = "/api/noToken/clientHome", moduleName = "车主端 首页功能")
@Api(description = "车主端 首页功能")
@RestController
@RequestMapping("/api/noToken/clientHome")
public class ClientHomeController extends BaseController {

    @Autowired
    private ClientHomeService clientHomeService;


    /**
     * 车主端 首页地图上附近的充电桩查询
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "首页地图上附近的充电桩查询", notes = "首页地图上附近的充电桩查询")
    @RequestMapping(value = "/car/mapStation", method = RequestMethod.POST)
    public Response<List<StationHomeMapListOut>> mapStation(
            @RequestBody @ApiParam(required = true) StationHomeListIn listIn) {
        List<StationHomeMapListOut> result = clientHomeService.mapStation(listIn);
        return success(result);
    }

    /**
     * 车主端 搜一搜显示桩站列表
     * @param listIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "搜一搜显示桩站列表", notes = "搜一搜显示桩站列表")
    @RequestMapping(value = "/car/stationList", method = RequestMethod.POST)
    public Response<List<StationHomeListOut>> stationList(
            @RequestBody @Valid @ApiParam(required = true) StationHomeListIn listIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);
        List<StationHomeListOut> result = this.clientHomeService.stationList(listIn);


        return success(result);
    }

    /**
     * 车主端 首页显示桩站详情
     * @param detailIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "首页显示桩站详情", notes = "首页显示桩站详情")
    @RequestMapping(value = "/car/stationDetail", method = RequestMethod.POST)
    public Response<StationHomeDetailOut> stationDetail(
            @RequestBody @Valid @ApiParam(required = true) StationDetailIn detailIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        StationHomeDetailOut detail = this.clientHomeService.stationDetail(detailIn);

        return success(detail);
    }

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenManager jwtTokenManager;

    // http://192.168.101.52:8990/api/noToken/clientHome/car/createToken
    @RequestMapping(value = "/car/createToken", method = RequestMethod.GET)
    public String getToken(HttpServletRequest request){

        String id = request.getParameter("id");
        String stationId = request.getParameter("stationId");
        String companyId = request.getParameter("companyId");
        stationId = stationId==null?"1":stationId;
        companyId = companyId==null?"1":companyId;
        if(id == null){
            return "";
        }
        User user = userMapper.get(Integer.parseInt(id));
        //用户的权限
        List<String> permissionStrs = this.userService.getUserPermissions(user.getId());
        TokenPrincipal tokenPrincipal = dozer.map(user, TokenPrincipal.class);
        tokenPrincipal.setUserId(user.getId());
        tokenPrincipal.setPermissionStrs(permissionStrs);
        tokenPrincipal.setStationId(Integer.parseInt(stationId));
        tokenPrincipal.setCompanyInfoId(Integer.parseInt(companyId));

        return "Bearer "+jwtTokenManager.create(tokenPrincipal);
    }

    @Autowired
    private StationClientGunService stationClientGunService;

    @Autowired
    private StationClientGunMapper stationClientGunMapper;

    /*
     * 插枪模拟 http://192.168.101.52:8990/api/noToken/clientHome/car/plugInGun?gunId=1&status=3
     * https://testapi.xmnewlife.com/api/noToken/clientHome/car/plugInGun?gunId=50&status=3
     * @param request
     */
    @Value("${newLife.charge.ip}")
    private String ip;

    @Value("${pay.isReal}")
    private boolean isReal;

    @RequestMapping(value = "/car/plugInGun", method = RequestMethod.GET)
    public void plugInGun(HttpServletRequest request){
        System.err.println(ip);
        System.err.println(isReal);
        String gunId = request.getParameter("gunId");
        String status = request.getParameter("status");
        if("2".equalsIgnoreCase(gunId)){
            // 先将枪状态改为2
            StationClientGun gun = stationClientGunMapper.get(Integer.parseInt(gunId));
            gun.setStatus(2);
            stationClientGunMapper.update(gun);
            return;
        }


        PileRealTimeDataIn pileRealTimeDataIn = new PileRealTimeDataIn();
        stationClientGunService.updateStatus(Integer.parseInt(gunId),Integer.parseInt(status),pileRealTimeDataIn);
    }

    /*
     * 模拟实时数据 http://192.168.101.52:8990/api/noToken/clientHome/car/realTimeData?gunId=1&status=4
     * https://testapi.xmnewlife.com/api/noToken/clientHome/car/realTimeData?gunId=50&status=4
     * @param request
     */
    @RequestMapping(value = "/car/realTimeData", method = RequestMethod.GET)
    public void realTimeData(HttpServletRequest request){
        String gunId = request.getParameter("gunId");
        String status = request.getParameter("status");
        PileRealTimeDataIn pileRealTimeDataIn = new PileRealTimeDataIn();
/*        gun.setPercentage(pileRealTimeDataIn.getCurrentSoc());
        gun.setElectric(pileRealTimeDataIn.getChargeVoltage());
        gun.setVoltage(pileRealTimeDataIn.getChargeVoltage());
        gun.setChargeTime(pileRealTimeDataIn.getChargeTime());
        gun.setChargeEnergy(pileRealTimeDataIn.getChargeEnergy());
        gun.setChargeAmount(pileRealTimeDataIn.getChargeAmount());*/
        pileRealTimeDataIn.setCurrentSoc("50");
        pileRealTimeDataIn.setChargeVoltage("220");
        pileRealTimeDataIn.setChargeTime(60*60*20+"");
        pileRealTimeDataIn.setChargeEnergy("20");
        pileRealTimeDataIn.setChargeAmount("20");

        stationClientGunService.updateStatus(Integer.parseInt(gunId),Integer.parseInt(status),pileRealTimeDataIn);
    }

    @Autowired
    private ClientHistoryDataService clientHistoryDataService;

    @Autowired
    private OrderMapper orderMapper;

    /*
     * 充电结束后历史账单数据
     * http://192.168.101.52:8990/api/noToken/clientHome/car/dealPileHistoryData?gunId=1&status=2&oredrSn=123
     * https://testapi.xmnewlife.com/api/noToken/clientHome/car/dealPileHistoryData?gunId=50&status=2&oredrSn=123
     */
    @RequestMapping(value = "/car/dealPileHistoryData", method = RequestMethod.GET)
    public void dealPileHistoryData(HttpServletRequest request){


        String gunId = request.getParameter("gunId");
        String status = request.getParameter("status");
        String oredrSn = request.getParameter("oredrSn");
        Order order = orderMapper.getBySn(oredrSn);

        PileHistoryDataIn pileHistoryDataIn = new PileHistoryDataIn();
        pileHistoryDataIn.setStationClientCode("asf2354");
        pileHistoryDataIn.setGunCode(1+"");
        pileHistoryDataIn.setChargeWay(0);
        pileHistoryDataIn.setChargeMode(0);
        pileHistoryDataIn.setChargeCardType(1);
//        pileHistoryDataIn.setChargeCardNo("15520722683");
        pileHistoryDataIn.setCarVin("");
        pileHistoryDataIn.setBabe("1000");
        pileHistoryDataIn.setChargeVoltage("220");
        pileHistoryDataIn.setChargeElectricity("10");
        pileHistoryDataIn.setChargeTime("72000");
        pileHistoryDataIn.setChargeAmount("200");
        pileHistoryDataIn.setChargeEnergy("50");
        pileHistoryDataIn.setChargeEnergyStart("10");
        pileHistoryDataIn.setChargeEnergyEnd("60");
        pileHistoryDataIn.setLeftTime("1000");
        pileHistoryDataIn.setCurrentSoc("");
        pileHistoryDataIn.setUploadMaster(0);
        pileHistoryDataIn.setPay(1);
        pileHistoryDataIn.setChargeEndReason(0);
        pileHistoryDataIn.setChargeTimeStart(new Date());
        pileHistoryDataIn.setChargeTimeEnd(new Date());
        pileHistoryDataIn.setRecordNo(order.getId());
        pileHistoryDataIn.setRecordStorageNo(12134);

        StageDataIn stageDataIn = new StageDataIn();
        stageDataIn.setBalance("100");
        stageDataIn.setPower("20");
        stageDataIn.setTime(1000);
        List<StageDataIn> stageDataIns = Lists.newArrayList();
        stageDataIns.add(stageDataIn);
        pileHistoryDataIn.setStageDataInList(stageDataIns);

        // 查询userId
        String userIdStr = RedisUtils.getRedisValue("gun_user_id_"+gunId);
        User user = userMapper.get(Integer.parseInt(userIdStr));
        System.err.println("===> 查询userId:"+userIdStr+" 电话号码："+user.getMobile());
        pileHistoryDataIn.setChargeCardNo(user.getMobile());

        clientHistoryDataService.dealPileHistoryData(pileHistoryDataIn);

        PileRealTimeDataIn pileRealTimeDataIn = new PileRealTimeDataIn();
        stationClientGunService.updateStatus(Integer.parseInt(gunId),Integer.parseInt(status),pileRealTimeDataIn);
    }


}
