package com.newlife.charge.controller;


import com.newlife.charge.common.Reg.RegExp;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.GeneralCarUserPageOut;
import com.newlife.charge.core.dto.out.GeneralUserOut;
import com.newlife.charge.core.dto.out.StationUserOut;
import com.newlife.charge.core.enums.UserStatusEnum;
import com.newlife.charge.core.enums.UserTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
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
import java.util.regex.Pattern;

/**
 * 账号管理
 */
@PlatLogModule(moduleId = "/api/user", moduleName = "账号管理")
@Api(description = "账号管理")
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {


    @Autowired
    private UserService userService;

    /**
     * 总后台 账号管理-分页查询数据
     * 只查询总后台账号
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:user:general:menu")
    @ApiOperation(value = "总后台 账号管理-分页查询数据", notes = "总后台 账号管理-分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralUserOut>> generalPageList(@RequestBody @ApiParam(required = true) GeneralUserPageIn pageIn) {
        PageInfo<GeneralUserOut> page = new PageInfo<>();
        try {

            page = this.userService.generalPage(pageIn.getPageNo(), pageIn.getPageSize(), UserTypeEnum.ADMIN.getValue(), pageIn.getNickName());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general user pageList error", e);
            return error(page);
        }
    }


    /**
     * 总后台 账号管理-添加账号
     *
     * @param generalUserSaveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = {"sys:user:general:create"})
    @ApiOperation(value = "总后台 账号管理-添加账号", notes = "总后台 账号管理-添加账号")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response generalSave(@RequestBody @Valid @ApiParam(required = true) GeneralUserSaveIn generalUserSaveIn, BindingResult bindingResult) {

        //抛出异常信息
        throwBindingResultErrorMsg(bindingResult);

        //保存
        this.userService.generalSave(generalUserSaveIn);
        return success().setMessage("保存成功");
    }


    /**
     * 总后台 账号管理-更新账号
     *
     * @param generalUserUpdateIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @RequiresPermissions(value = {"sys:user:general:update"})
    @ApiOperation(value = "总后台 账号管理-更新账号", notes = "总后台 账号管理-更新账号")
    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
    public Response generalUpdate(@RequestBody @Valid @ApiParam(required = true) GeneralUserUpdateIn generalUserUpdateIn, BindingResult bindingResult) {

        //抛出异常信息
        throwBindingResultErrorMsg(bindingResult);
        String password = generalUserUpdateIn.getPassword();
        if(StringUtils.isNotBlank(password)){
            Pattern pattern = Pattern.compile(RegExp.PASSWORD);
            if (!pattern.matcher(password).matches()) {
                throw new BizException("密码格式不对,6-18为数字和大小写字母组合");
            }
        }

        //更新
        this.userService.generalUpdate(generalUserUpdateIn);
        return success().setMessage("更新成功");
    }


    /**
     * 总后台 账号管理-启用
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.ENABLE)
    @RequiresPermissions(value = {"sys:user:general:enable"})
    @ApiOperation(value = "总后台 账号管理-启用", notes = "总后台 账号管理-启用")
    @RequestMapping(value = "/general/enable", method = RequestMethod.POST)
    public Response generalEnable(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
                                  BindingResult bindingResult) {

        //抛出异常信息
        throwBindingResultErrorMsg(bindingResult);


        //保存
        this.userService.generalUpdateStatus(infoIn.getId(), UserStatusEnum.ENABLED);
        return success().setMessage("启用成功");
    }


    /**
     * 总后台 账号管理-禁用
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DISABLE)
    @RequiresPermissions(value = {"sys:user:general:disable"})
    @ApiOperation(value = "总后台 账号管理禁用", notes = "总后台 账号管理禁用")
    @RequestMapping(value = "/general/disable", method = RequestMethod.POST)
    public Response generalDisable(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
                                   BindingResult bindingResult) {

        //抛出异常信息
        throwBindingResultErrorMsg(bindingResult);

        //保存
        this.userService.generalUpdateStatus(infoIn.getId(), UserStatusEnum.DISABLE);
        return success().setMessage("禁用成功");
    }


    /**
     * 总后台 账号管理-账号详情
     * @param infoIn
     * @return
     */
    @ApiOperation(value = "总后台 账号管理-账号详情", notes = "总后台 账号管理-账号详情")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<GeneralUserOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        //抛出异常信息
        throwBindingResultErrorMsg(bindingResult);
        GeneralUserOut out = this.userService.generalInfo(infoIn.getId());
        return success(out);
    }


    /**
     * 总后台 账号管理-账号删除
     *
     * @param delIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:user:general:delete")
    @ApiOperation(value = "总后台 账号管理-账号删除", notes = "总后台 账号管理-账号删除")
    @RequestMapping(value = "/general/del", method = RequestMethod.POST)
    public Response generalDel(@RequestBody @Valid @ApiParam(required = true) DelIn delIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer id = delIn.getId();
        if (id != null) {
            this.userService.delete(id);

            return success().setMessage("删除成功");
        } else {
            return error("请选择需要删除的记录");
        }
    }

    /**
     * 总后台 账号管理-账号批量删除
     *
     * @param delsIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:user:general:delete")
    @ApiOperation(value = "总后台 账号管理-账号批量删除", notes = "总后台 账号管理-账号批量删除")
    @RequestMapping(value = "/general/dels", method = RequestMethod.POST)
    public Response generalDels(@RequestBody @Valid @ApiParam(required = true) DelsIn delsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (delsIn != null && delsIn.getIds() != null) {
            this.userService.deletes(delsIn.getIds());
            return success().setMessage("批量删除成功");
        } else {
            return error("请选择需要删除的记录");
        }
    }

    /**
     * 桩站后台 用户修改密码
     * @param userInPassDto
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.PWD)
    @ApiOperation(value = "桩站后台 用户修改密码", notes = "桩站后台 用户修改密码")
    @RequestMapping(value = "/station/modifyPass", method = RequestMethod.POST)
    public Response modifyPass(@RequestBody @ApiParam(required = true) UserInPassDto userInPassDto) {

        this.userService.modifyPassword(userInPassDto);
        return success().setMessage("修改成功");
    }

    /**
     * 桩站后台 用户找回密码
     * @param userRegisterPasswordIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.PWD)
    @ApiOperation(value = "桩站后台 用户找回密码", notes = "桩站后台 用户找回密码")
    @RequestMapping(value = "/station/resetPass", method = RequestMethod.POST)
    public Response modifyPass(@RequestBody @ApiParam(required = true) UserRegisterPasswordIn userRegisterPasswordIn) {

        this.userService.resetPass(userRegisterPasswordIn);
        return success().setMessage("修改成功");
    }


    /**
     * 桩站后台 账号管理-分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:user:station:menu")
    @ApiOperation(value = "桩站后台 账号管理-分页查询数据", notes = "桩站后台 账号管理-分页查询数据")
    @RequestMapping(value = "/station/userList", method = RequestMethod.POST)
    public Response<PageInfo<StationUserOut>> userList(@RequestBody @ApiParam(required = true) StationUserPageQuery query) {
        PageInfo<StationUserOut> page = new PageInfo<>();
        try {
            page = this.userService.stationUserPage(query);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("station user pageList error", e);
            return error(page);
        }
    }

    /**
     * 桩站后台 添加子账号
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:user:station:create")
    @ApiOperation(value = "桩站后台 添加子账号", notes = "桩站后台 添加子账号")
    @RequestMapping(value = "/station/save", method = RequestMethod.POST)
    public Response stationSave(@RequestBody @ApiParam(required = true) StationUserIn userIn) {

        //手机号验证
        boolean flag = Pattern.matches(RegExp.MOBILE, userIn.getUserName());
        if (!flag) {
            return error("行手机号码，位数或格式不正确。)");
        }
        userService.stationSave(userIn);
        return success().setMessage("添加成功");
    }

    /**
     * 桩站后台 修改子账号
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:user:station:update")
    @ApiOperation(value = "桩站后台 修改子账号", notes = "桩站后台 修改子账号")
    @RequestMapping(value = "/station/update", method = RequestMethod.POST)
    public Response stationUpdate(@RequestBody @ApiParam(required = true) StationUserUpdateIn userIn) {

        userService.stationUpdate(userIn);
        return success().setMessage("修改成功");
    }

    /**
     *
     * 桩站后台 账号管理-账号批量删除
     *
     * @param delsIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:user:station:delete")
    @ApiOperation(value = "桩站后台 账号管理-账号批量删除", notes = "桩站后台 账号管理-账号批量删除")
    @RequestMapping(value = "/station/dels", method = RequestMethod.POST)
    public Response stationDels(@RequestBody @Valid @ApiParam(required = true) DelsIn delsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (delsIn != null && delsIn.getIds() != null) {
            this.userService.deletesStationUser(delsIn.getIds());
            return success().setMessage("批量删除成功");
        } else {
            return error("请选择需要删除的记录");
        }
    }

    /**
     * 桩站后台 账号管理-账号详情
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = {"sys:user:station:info", "sys:user:station:update"})
    @ApiOperation(value = "桩站后台 账号管理-账号详情", notes = "桩站后台 账号管理-账号详情")
    @RequestMapping(value = "/station/info", method = RequestMethod.POST)
    public Response<StationUserOut> stationInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        //抛出异常信息
        throwBindingResultErrorMsg(bindingResult);
        StationUserOut out = this.userService.stationUserInfo(infoIn.getId(), UserContext.getStationId());
        return success(out);
    }

}
