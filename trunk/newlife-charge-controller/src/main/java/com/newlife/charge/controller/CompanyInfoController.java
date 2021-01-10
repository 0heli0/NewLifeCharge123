package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.CompanyInfo;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.*;
import com.newlife.charge.core.enums.AuditStatusEnum;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 总后台 公司信息管理
 */
@PlatLogModule(moduleId = "/api/companyInfo", moduleName = "公司信息管理")
@Api(description = "公司信息")
@RestController
@RequestMapping("/api/companyInfo")
public class CompanyInfoController extends BaseController {


    @Autowired
    private CompanyInfoService companyInfoService;


    @Autowired
    private UserService userService;

    /**
     * 总后台 审核通过的公司分页查询
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:companyInfo:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralCompanyInfoPageOut>> generalPageList(
            @RequestBody @Valid @ApiParam(required = true) GeneralCompanyInfoPageIn pageIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);
        PageInfo<GeneralCompanyInfoPageOut> page = new PageInfo<>();
        try {
            page = this.companyInfoService.page(pageIn.getPageNo(), pageIn.getPageSize(), AuditStatusEnum.PASS.getValue(), pageIn.getCompanyName());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general companyInfo pageList error", e);
            return error(page);
        }
    }

    /**
     * 总后台 审核通过的公司列表查询，用于新增桩站是下拉框选择公司
     * 选择所属公司
     *
     * @return
     */
    @ApiOperation(value = "选择所属公司", notes = "总后台 审核通过的公司列表查询，用于新增桩站是下拉框选择公司")
    @RequestMapping(value = "/general/selectList", method = RequestMethod.POST)
    public Response<List<GeneralCompanyInfoSelectListOut>> generalSelectList() {
        List<GeneralCompanyInfoSelectListOut> list = new ArrayList<>();
        try {
            list = this.companyInfoService.selectList(AuditStatusEnum.PASS.getValue(), null);
            return success(list);
        } catch (Exception e) {
            LOGGER.error("general companyInfo selectList error", e);
            return error(list);
        }
    }

    /**
     * 总后台 审核通过的公司查看详情
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:companyInfo:general:info")
    @ApiOperation(value = "总后台根据公司主键ID查看详情", notes = "总后台根据公司主键ID查询详情信息")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<GeneralCompanyInfoOut> generalCompanyInfo(
            @RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
            BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer id = infoIn.getId();

        GeneralCompanyInfoOut info = this.companyInfoService.getInfoById(id);
        return success(info);
    }



    /**
     *  桩站 公司提交资质
     *
     * @param companyInfo
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "桩站 公司提交资质", notes = "桩站 公司提交资质")
    @RequestMapping(value = "/station/company/save", method = RequestMethod.POST)
    public Response companySave(@RequestBody @Valid @ApiParam(required = true) CompanyInfoIn companyInfo, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        return success(companyInfoService.save(companyInfo));
    }

    /**
     *  桩站 主账号登入查询公司提交资质审核详情
     *
     * @param
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站 公司提交资质审核详情", notes = "桩站 公司提交资质审核详情")
    @RequestMapping(value = "/station/company/info", method = RequestMethod.POST)
    public Response stationCompanyInfo() {

        return success(companyInfoService.stationCompanyInfo());
    }

    /**
     *  桩站切换 账号关联的公司
     *
     * @param
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站切换 账号关联的公司", notes = "桩站切换 账号关联的公司")
    @RequestMapping(value = "/station/userCompanys", method = RequestMethod.POST)
    public Response userCompanys() {

        return success(companyInfoService.userCompanys());
    }

    /**
     *  桩站 公司详情
     *
     * @param
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:company:station:menu")
    @ApiOperation(value = "桩站 公司详情", notes = "桩站 公司详情")
    @RequestMapping(value = "/station/companyInfo", method = RequestMethod.POST)
    public Response companyInfo() {
        System.out.println(UserContext.getStationId());
        return success(companyInfoService.getInfoByStationId(UserContext.getStationId()));
    }
}
