package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.Order;
import com.newlife.charge.core.domain.UserAccount;
import com.newlife.charge.core.dto.in.WRefindCreateOrderIn;
import com.newlife.charge.core.dto.in.WeiRefundIn;
import com.newlife.charge.core.dto.in.WeiRefundResult;
import com.newlife.charge.core.dto.out.FormulaModeGeneralOut;
import com.newlife.charge.core.dto.out.FormulaModeOut;
import com.newlife.charge.core.dto.out.UserAccountOut;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeStrEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.WeiRefundService;
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
import java.util.List;

/**
 * 车主端 微信退款管理
 */
@PlatLogModule(moduleId = "/api/weiChatRefund", moduleName = "微信退款管理")
@Api(description = "车主端 微信退款管理")
@RestController
@RequestMapping("/api/weiChatRefund")
public class UserWeiChatRefundController extends BaseController {

    @Autowired
    private WeiRefundService weiRefundService;

    /**
     * 车主端 获取用户资金信息
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "车主端 获取用户资金信息", notes = "车主端 获取用户资金信息")
    @RequestMapping(value = "/car/getAccount", method = RequestMethod.POST)
    public Response<UserAccountOut> getAccount() {
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        UserAccountOut account = this.weiRefundService.getAccount(userId);

        return success(account);
    }

    /**
     * 车主端 退款金额计算方式
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "车主端 退款金额计算方式", notes = "车主端 退款金额计算方式")
    @RequestMapping(value = "/car/formulaMode", method = RequestMethod.POST)
    public Response<FormulaModeGeneralOut> formulaMode() {
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        FormulaModeGeneralOut out = this.weiRefundService.formulaMode(userId);

        return success(out);
    }

    /**
     * 车主端 生成用户退款订单
     * @param orderIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "车主端 生成用户退款订单", notes = "车主端 生成用户退款订单")
    @RequestMapping(value = "/car/createOrder", method = RequestMethod.POST)
    public Response<Order> createOrder(
            @RequestBody @Valid @ApiParam(required = true) WRefindCreateOrderIn orderIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        String type = NewLifeSpendLogTypeStrEnum.BALANCE_REFUND.getDescription();
        Order order = this.weiRefundService.createOrder(orderIn,userId,type);

        return success(order);
    }

    /**
     * 车主端 通知微信服务端执行退款操作
     * @param refundIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "车主端 通知微信服务端执行退款操作", notes = "车主端 通知微信服务端执行退款操作")
    @RequestMapping(value = "/car/weiChatRefund", method = RequestMethod.POST)
    public Response<WeiRefundResult> weiChatRefund(
            @RequestBody @Valid @ApiParam(required = true) WeiRefundIn refundIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        WeiRefundResult refundResult = this.weiRefundService.weiChatRefund(refundIn,userId);

        return success(refundResult);
    }



}
