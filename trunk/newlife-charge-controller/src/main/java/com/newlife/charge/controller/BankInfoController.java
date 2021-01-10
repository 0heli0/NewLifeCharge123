package com.newlife.charge.controller;

import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.dto.out.BankInfoOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.BankInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 银行信息 controller
 * <p>
 */
@PlatLogModule(moduleId = "/api/bankInfo", moduleName = "银行模块")
@Api(description = "银行信息")
@RestController
@RequestMapping("/api/bankInfo")
public class BankInfoController extends BaseController {

    @Autowired
    private BankInfoService bankInfoService;

    /**
     * 银行信息列表
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "查询银行信息列表", notes = "查询银行信息列表")
    @RequestMapping(value = "/selectList", method = RequestMethod.POST)
    public Response<List<BankInfoOut>> selectList() {
        List<BankInfoOut> list = new ArrayList<>();
        try {
            list = this.bankInfoService.list();
        } catch (Exception e) {
            LOGGER.error("bankInfo selectList error", e);
            return error(list);
        }
        return success(list);
    }


}
