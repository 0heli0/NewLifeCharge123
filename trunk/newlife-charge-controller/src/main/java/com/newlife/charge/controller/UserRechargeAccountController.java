package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.dto.in.InfoIn;
import com.newlife.charge.core.dto.out.UserNewLifeSpendLogChargeOut;
import com.newlife.charge.core.dto.out.UserNewLifeSpendLogRechargeOut;
import com.newlife.charge.core.dto.out.UserNewLifeSpendLogRefundOut;
import com.newlife.charge.core.dto.out.UserRechargeAccountOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.UserRechargeAccountService;
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
 *车主端 用户充值记录-账户管理记录
 */
@PlatLogModule(moduleId = "/api/userAccount", moduleName = "车主端充值账户记录管理")
@Api(description = "车主端 用户充值记录-账户管理记录")
@RestController
@RequestMapping("/api/userAccount")
public class UserRechargeAccountController extends BaseController {

    @Autowired
    private UserRechargeAccountService userRechargeService;

    /**
     * 车主端 用户账户列表展示
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "用户账户列表展示", notes = "用户账户列表展示")
    @RequestMapping(value = "/car/rechargeAccountList", method = RequestMethod.POST)
    public Response<UserRechargeAccountOut> rechargeAccountList(){

        Integer userId = UserContext.getUserId()==null?0: UserContext.getUserId();
        UserRechargeAccountOut out = this.userRechargeService.rechargeAccountList(userId);
        return success(out);
    }

    /**
     * 车主端 用户账户充值详情
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "用户账户充值详情", notes = "用户账户充值详情")
    @RequestMapping(value = "/car/rechargeDetail", method = RequestMethod.POST)
    public Response<UserNewLifeSpendLogRechargeOut> rechargeDetail(@RequestBody @Valid @ApiParam InfoIn infoIn,
                                                                   BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);

        UserNewLifeSpendLogRechargeOut out = this.userRechargeService.rechargeDetail(infoIn.getId());

        return success(out);
    }

    /**
     * 车主端 用户充电消费详情
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "用户充电消费详情", notes = "用户充电消费详情")
    @RequestMapping(value = "/car/chargeDetail", method = RequestMethod.POST)
    public Response<UserNewLifeSpendLogChargeOut> chargeDetail(@RequestBody @Valid @ApiParam InfoIn infoIn,
                                                                   BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);

        UserNewLifeSpendLogChargeOut out = this.userRechargeService.chargeDetail(infoIn.getId());

        return success(out);
    }

    /**
     * 车主端 用户退款记录详情
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "用户退款记录详情", notes = "用户退款记录详情")
    @RequestMapping(value = "/car/refundDetail", method = RequestMethod.POST)
    public Response<UserNewLifeSpendLogRefundOut> refundDetail(@RequestBody @Valid @ApiParam InfoIn infoIn,
                                                               BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);

        UserNewLifeSpendLogRefundOut out = this.userRechargeService.refundDetail(infoIn.getId());

        return success(out);
    }

}
