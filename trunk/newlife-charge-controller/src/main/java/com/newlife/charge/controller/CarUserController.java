package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.GeneralCarUserPageIn;
import com.newlife.charge.core.dto.out.GeneralCarUserPageOut;
import com.newlife.charge.core.enums.UserTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 总后台 （车主）用户数据查询
 */
@PlatLogModule(moduleId = "/api/carUser", moduleName = "用户数据查询")
@Api(description = "账号管理")
@RestController
@RequestMapping("/api/carUser")
public class CarUserController extends BaseController {


    @Autowired
    private UserService userService;

    /**
     * 总后台 用户管理-用户数据分页查询数据
     * 只查询车主用户账号
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:carUser:general:menu")
    @ApiOperation(value = "总后台 用户管理-分页查询数据", notes = "总后台 用户管理-分页查询数据")
    @RequestMapping(value = "/general/carUserPageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralCarUserPageOut>> generalCarUserPageList(
            @RequestBody @ApiParam(required = true) GeneralCarUserPageIn pageIn) {
        PageInfo<GeneralCarUserPageOut> page = new PageInfo<>();
        try {

            page = this.userService.generalCarUserPage(pageIn.getPageNo(), pageIn.getPageSize(), UserTypeEnum.CAR.getValue(), pageIn.getMobile());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general car user pageList error", e);
            return error(page);
        }
    }

}
