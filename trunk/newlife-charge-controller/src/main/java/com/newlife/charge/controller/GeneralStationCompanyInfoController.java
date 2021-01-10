package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.GeneralCompanyRationIn;
import com.newlife.charge.core.dto.in.GeneralStationUserAuditIn;
import com.newlife.charge.core.dto.in.GeneralStationUserPageIn;
import com.newlife.charge.core.dto.in.InfoIn;
import com.newlife.charge.core.dto.out.GeneralCompanyUserInfoOut;
import com.newlife.charge.core.dto.out.GeneralCompanyUserPageOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.CompanyInfoService;
import com.newlife.charge.service.UserService;
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
 * 总后台 桩站公司管理
 */
@PlatLogModule(moduleId = "/api/companyUser", moduleName = "桩站公司管理")
@Api(description = "总后台桩站公司管理")
@RestController
@RequestMapping("/api/companyUser")
public class GeneralStationCompanyInfoController extends BaseController {


    @Autowired
    private CompanyInfoService companyInfoService;


    @Autowired
    private UserService userService;

    /**
     * 总后台 桩站注册用户分页查询（主账号）
     * 桩站账号管理
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:companyUser:general:menu")
    @ApiOperation(value = "总后台 桩站注册用户分页查询（主账号）", notes = "总后台 桩站注册用户分页查询（主账号）")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralCompanyUserPageOut>> generalCompanyUserPageList(
            @RequestBody @Valid @ApiParam(required = true) GeneralStationUserPageIn pageIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);
        PageInfo<GeneralCompanyUserPageOut> page = new PageInfo<>();
        try {
            page = this.userService.generalCompanyUserPageList(pageIn.getPageNo(), pageIn.getPageSize(), pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general stationUser pageList error", e);
            return error(page);
        }
    }


    /**
     * 总后台 桩站主账号详情
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = {"sys:companyUser:general:info"})
    @ApiOperation(value = "总后台 桩站主账号详情", notes = "总后台 桩站主账号详情")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<GeneralCompanyUserInfoOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
                                                           BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        GeneralCompanyUserInfoOut out = this.companyInfoService.getCompanyUserInfo(infoIn.getId());
        return success(out);
    }


    /**
     * 总后台 桩站注册用户公司审核资质认证
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.AUDIT)
    @RequiresPermissions(value = "sys:companyUser:general:audit")
    @ApiOperation(value = "总后台 桩站注册用户公司审核资质认证", notes = "总后台 桩站注册用户公司审核资质认证")
    @RequestMapping(value = "/general/audit", method = RequestMethod.POST)
    public Response generalAudit(
            @RequestBody @Valid @ApiParam(required = true) GeneralStationUserAuditIn auditIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);
        this.companyInfoService.audit(auditIn);
        return success();

    }


    /**
     * 总后台 更改桩站公司抽佣比例
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.AUDIT)
    @RequiresPermissions(value = "sys:companyUser:general:audit")
    @ApiOperation(value = "总后台 更改桩站公司抽佣比例", notes = "总后台 更改桩站公司抽佣比例")
    @RequestMapping(value = "/general/ration", method = RequestMethod.POST)
    public Response generalRation(
            @RequestBody @Valid @ApiParam(required = true) GeneralCompanyRationIn rationIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        this.companyInfoService.updateRation(rationIn.getCompanyId(), rationIn.getCommissionRation());

        return success();

    }


}
