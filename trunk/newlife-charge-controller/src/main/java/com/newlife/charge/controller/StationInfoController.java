package com.newlife.charge.controller;


import com.alibaba.fastjson.JSON;
import com.newlife.charge.common.DateUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.RoleInfo;
import com.newlife.charge.core.domain.User;
import com.newlife.charge.core.domain.exModel.RolePermissionEx;
import com.newlife.charge.core.domain.exModel.UserEx;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.AuditStatusEnum;
import com.newlife.charge.core.enums.ProjectTypeEnum;
import com.newlife.charge.core.enums.UserTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.security.TokenManager;
import com.newlife.charge.security.TokenPrincipal;
import com.newlife.charge.service.PermissionInfoService;
import com.newlife.charge.service.RoleService;
import com.newlife.charge.service.StationInfoService;
import com.newlife.charge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 总后台 桩站/站点管理
 */
@PlatLogModule(moduleId = "/api/stationInfo", moduleName = "站点管理")
@Api(description = "总后台 桩站/站点管理")
@RestController
@RequestMapping("/api/stationInfo")
public class StationInfoController extends BaseController {


    @Autowired
    private StationInfoService service;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionInfoService permissionInfoService;


    /**
     * 桩站管理总后台分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:stationInfo:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralStationInfoPageOut>> generalPageList(
            @RequestBody @ApiParam(required = true) GeneralStationInfoPageIn pageIn) {
        PageInfo<GeneralStationInfoPageOut> page = new PageInfo<>();
        try {
            page = this.service.page(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general stationInfo pageList error", e);
            return error(page);
        }
    }


    /**
     * 总后台 桩站列表查询，用于做桩站下拉框
     *
     * @return
     */
    @ApiOperation(value = "总后台 桩站列表查询，用于做桩站下拉框", notes = "总后台 桩站列表查询，用于做桩站下拉框")
    @RequestMapping(value = "/general/selectList", method = RequestMethod.POST)
    public Response<List<SelectListOut>> generalSelectList() {
        List<SelectListOut> list = new ArrayList<>();
        try {
            list = this.service.selectList();
            return success(list);
        } catch (Exception e) {
            LOGGER.error("general stationInfo selectList error", e);
            return error(list);
        }
    }


    /**
     * 总后台查看详情
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:stationInfo:general:info")
    @ApiOperation(value = "总后台查看详情", notes = "总后台根据主键ID查询详情信息")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<GeneralStationInfoOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        GeneralStationInfoOut info = this.service.getInfoById(infoIn.getId());
        return success(info);
    }


    /**
     * 总后台详情-用于编辑时数据回填
     *
     * @param infoIn
     * @return
     */
    @RequiresPermissions(value = "sys:stationInfo:general:update")
    @ApiOperation(value = "总后台详情-用于编辑数据回填", notes = "总后台详情-用于编辑数据回填")
    @RequestMapping(value = "/general/updateInfo", method = RequestMethod.POST)
    public Response<GeneralStationUpdateInfoOut> generalUpdateInfo(
            @RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
            BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer id = infoIn.getId();
        GeneralStationUpdateInfoOut info = this.service.getUpdateInfoById(id);
        return success(info);
    }


    /**
     * 总后台新增桩站/站点
     *
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:stationInfo:general:create")
    @ApiOperation(value = "总后台新增", notes = "总后台新增")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response generalSave(@RequestBody @Valid @ApiParam(required = true) StationInfoSaveIn saveIn,
                                BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.service.save(saveIn);
        return success().setMessage("保存成功");
    }


    /**
     * 总后台更新
     *
     * @param updateIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @RequiresPermissions(value = "sys:stationInfo:general:update")
    @ApiOperation(value = "总后台更新", notes = "总后台更新")
    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
    public Response generalUpdate(@RequestBody @Valid @ApiParam(required = true) StationInfoUpdateIn updateIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (updateIn.getId() == null) {
            return error("站点ID不能为空");
        }
        this.service.update(updateIn);

        return success().setMessage("更新成功");
    }


//    /**
//     * 总后台删除，逻辑删除
//     * 废弃
//     * @param delIn
//     * @return
//     */
//    @Deprecated
//    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
//    @RequiresPermissions(value = "sys:stationInfo:general:delete")
//    @ApiOperation(value = "总后台删除，逻辑删除", notes = "总后台删除，逻辑删除")
//    @RequestMapping(value = "/general/del", method = RequestMethod.POST)
//    public Response generalDel(@RequestBody @Valid @ApiParam(required = true) DelIn delIn, BindingResult bindingResult) {
//
//        throwBindingResultErrorMsg(bindingResult);
//
////        Integer id = delIn.getId();
////        if (id != null) {
////            this.service.delete(id);
////
////            return success().setMessage("删除成功");
////        } else {
////            return error("请选择需要删除的记录");
////        }
//
//        return error("没有删除功能");
//    }

//    /**
//     * 总后台批量删除，逻辑删除
//     * 废弃
//     * @param delsIn
//     * @return
//     */
//    @Deprecated
//    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
//    @RequiresPermissions(value = "sys:stationInfo:general:delete")
//    @ApiOperation(value = "总后台批量删除，逻辑删除", notes = "总后台批量删除，逻辑删除")
//    @RequestMapping(value = "/general/dels", method = RequestMethod.POST)
//    public Response generalDels(@RequestBody @Valid @ApiParam(required = true) DelsIn delsIn, BindingResult bindingResult) {
//
//        throwBindingResultErrorMsg(bindingResult);
////
////        if (delsIn != null && delsIn.getIds() != null) {
////            this.service.deletes(delsIn.getIds());
////            return success().setMessage("批量删除成功");
////        } else {
////            return error("请选择需要批量删除的记录");
////        }
//
//        return error("没有删除功能");
//    }

    /**
     * 桩站后台设置电费服务费
     *
     * @param setChargeIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:chargePrice:station:setting")
    @ApiOperation(value = "桩站后台设置电费服务费", notes = "桩站后台设置电费服务费")
    @RequestMapping(value = "/station/setChargeAndServicePrice", method = RequestMethod.POST)
    public Response setChargeAndServicePrice(@RequestBody @Valid @ApiParam(required = true) StationInfoSetChargeIn setChargeIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.service.setChargeAndServicePrice(setChargeIn);
        return success().setMessage("设置成功");
    }

    /**
     * 桩站后台 查询电费服务费
     *
     * @param
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:chargePrice:station:menu")
    @ApiOperation(value = "桩站后台 查询电费服务费", notes = "桩站后台 查询电费服务费")
    @RequestMapping(value = "/station/chargeAndServicePriceInfo", method = RequestMethod.POST)
    public Response chargeAndServicePriceInfo() {

        return success(this.service.chargeAndServicePriceInfo(UserContext.getStationId())).setMessage("设置成功");
    }

    /**
     * 桩站后台 时段电费查询
     *
     * @param
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:stationTimePrice:station:menu")
    @ApiOperation(value = "桩站后台 时段电费查询", notes = "桩站后台 时段电费查询")
    @RequestMapping(value = "/station/stationTimePriceList", method = RequestMethod.POST)
    public Response stationTimePriceList() {


        return success(this.service.stationTimePriceList()).setMessage("查询成功");
    }

    /**
     * 桩站后台 设置/修改时段电费
     *
     * @param stationTimePriceIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = {"sys:stationTimePrice:station:create", "sys:stationTimePrice:station:update"})
    @ApiOperation(value = "桩站后台 设置/修改时段电费", notes = "桩站后台 设置/修改时段电费")
    @RequestMapping(value = "/station/setStationTimePrice", method = RequestMethod.POST)
    public Response setStationTimePrice(@RequestBody @Valid @ApiParam(required = true) StationTimePriceIn stationTimePriceIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.service.setStationTimePrice(stationTimePriceIn);
        return success().setMessage("设置成功");
    }

    /**
     * 桩站后台 时段电费详情
     *
     * @param query
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站后台 时段电费详情", notes = "桩站后台 时段电费详情")
    @RequestMapping(value = "/station/stationTimePriceInfo", method = RequestMethod.POST)
    public Response stationTimePriceInfo(@RequestBody @Valid @ApiParam(required = true) StationTimePriceQuery query) {

        if (query.getTimeNo() == null) {
            return error(ERROR.INVALID_PARAM.message());
        }
        query.setStationId(UserContext.getStationId());
        return success(service.stationTimePriceInfo(query)).setMessage("查询成功");
    }


    /**
     * 桩站后台 删除时段电费
     *
     * @param query
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:stationTimePrice:station:delete")
    @ApiOperation(value = "桩站后台 删除时段电费", notes = "桩站后台 删除时段电费")
    @RequestMapping(value = "/station/deleteStationTimePrice", method = RequestMethod.POST)
    public Response deleteStationTimePrice(@RequestBody @Valid @ApiParam(required = true) StationTimePriceQuery query, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.service.deleteStationTimePrice(query);
        return success().setMessage("删除成功");
    }

    /**
     * @Description: 桩站切换
     * @Author: Linzq
     * @CreateDate: 2019/5/14 0014 14:17
     */
    @PlatLogMethod(operateName = PlatLogMethodType.LOGIN_SWITCH)
    @ApiOperation(value = "桩站后台 桩站切换", notes = "桩站后台 桩站切换")
    @RequestMapping(value = "/station/switchStation", method = RequestMethod.POST)
    public Response switchStation(@RequestBody @Valid @ApiParam(required = true) SwitchStationIn switchStationIn, BindingResult bindingResult) {

        if (switchStationIn.getStationId() == null) {
            return error(ERROR.INVALID_PARAM.message());
        }
        throwBindingResultErrorMsg(bindingResult);

        User user = userService.get(UserContext.getUserId());

        UserEx userEx = dozer.map(user, UserEx.class);

        userEx.setStationId(switchStationIn.getStationId());

        userEx.setCompanyInfoId(switchStationIn.getCompanyId());

        //处理登录时间
        userEx.setLoginTime(DateUtils.getTimestamp());
        //更新登录时间
        this.userService.update(userEx);

        //用户的权限
        List<String> permissionStrs = new ArrayList<>();
        if (user.getUserType().equals(UserTypeEnum.STATION_MAIN.getValue())) {
            //后台管理员 查询所有权限
            permissionStrs = userService.getUserPermissions(userEx.getId());
        } else {
            //查询用户所在桩站对应的角色
            RoleInfoQuery query = new RoleInfoQuery();
            query.setStationId(switchStationIn.getStationId());
            query.setUserId(userEx.getId());
            query.setProject(ProjectTypeEnum.PRO_STATION.getValue());
            RoleInfo roleInfo = roleService.getByUserId(query);
            if (roleInfo == null) {
                return error("用户信息错误");
            }
            List<RolePermissionEx> permissionOuts = new ArrayList<>();
            permissionOuts = this.permissionInfoService.permissionSname(roleInfo.getId());
            permissionStrs = permissionOuts.stream().map(RolePermissionEx::getPermissionSname).collect(Collectors.toList());
        }

        //生成2天效期的token
        String token = this.tokenManager.create(new TokenPrincipal(userEx, permissionStrs));

        //输出
        UserLoginOut userLoginOut = new UserLoginOut();
        userLoginOut.setToken(token);//授权的token
        userLoginOut.setUserType(userEx.getUserType());//用户类型
        userLoginOut.setStatus(userEx.getStatus());//账号状态
        userLoginOut.setUserName(userEx.getUserName());//用户名
        userLoginOut.setRealName(userEx.getRealName());//真实姓名
        userLoginOut.setNickName(userEx.getNickName());//昵称
        userLoginOut.setMobile(userEx.getMobile());//手机号
        userLoginOut.setStationId(userEx.getStationId());//桩站ID
        userLoginOut.setCompanyInfoId(userEx.getCompanyInfoId());
        userLoginOut.setAuditStatus(AuditStatusEnum.PASS.getValue());

        userLoginOut.setLoginTime(userEx.getLoginTime());//登录时间
        userLoginOut.setLiftBanTime(userEx.getLiftBanTime());//解锁时间
        userLoginOut.setPermissionStrs(permissionStrs);//用户的权限
        return success(userLoginOut).setMessage("切换成功");
    }

    /**
     * 桩站后台 根据公司Id查询当前用户关联的桩站列表
     *
     * @param switchStationIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站后台 根据公司Id查询当前用户关联的桩站列表", notes = "桩站后台 根据公司Id查询当前用户关联的桩站列表")
    @RequestMapping(value = "/station/selectListByCompanyId", method = RequestMethod.POST)
    public Response selectListByCompanyId(@RequestBody @Valid @ApiParam(required = true) SwitchStationIn switchStationIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        return success(service.selectListByCompanyId(switchStationIn.getCompanyId(), UserContext.getUserId())).setMessage("查询成功");
    }


    /**
     * 设置桩站计费规则（同步时段电价和服务费）
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @RequiresPermissions(value = "sys:stationTimePrice:station:syncTimeAndServicePrice")
    @ApiOperation(value = "设置桩站计费规则（同步时段电价和服务费）", notes = "设置桩站计费规则（同步时段电价和服务费）")
    @RequestMapping(value = "/station/syncTimeAndServicePrice", method = RequestMethod.POST)
    public Response syncTimeAndServicePrice(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        String result = this.service.syncTimeAndServicePrice(infoIn.getId());
        if (StringUtils.isNotBlank(result)) {
            Response response = JSON.parseObject(result, Response.class);
            return response;
        } else {
            return error("设置桩站计费规则失败");
        }
    }
}
