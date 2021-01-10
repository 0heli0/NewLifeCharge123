package com.newlife.charge.controller;


import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.newlife.charge.common.CommonUtil;
import com.newlife.charge.common.weixin.WeiXinUtils;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.MaxUseableCouponOut;
import com.newlife.charge.core.enums.NewLifeSpendLogTypeStrEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.UnifiedOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 车主端 微信支付领取接口
 */
@PlatLogModule(moduleId = "/api/appletWeiChatPay", moduleName = "车主端微信支付")
@Api(description = "车主端微信支付")
@RestController
@RequestMapping("/api/appletWeiChatPay")
public class AppletWeiChatPayController extends BaseController {


    @Autowired
    private UnifiedOrderService unifiedOrderService;

    /**
     * 车主端 微信支付下单,生成订单
     * @param queryIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "车主端 微信支付下单,生成订单", notes = "车主端 微信支付下单,生成订单")
    @RequestMapping(value = "/car/createOrder", method = RequestMethod.POST)
    public Response<String> createOrder(
            @RequestBody @Valid @ApiParam(required = true) CreateOrderIn queryIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0: UserContext.getUserId();
        String type = NewLifeSpendLogTypeStrEnum.USER_RECHARGE.getDescription();
        String result = unifiedOrderService.createOrder(queryIn, userId, type);

        return success(result);
    }


    /**
     * 车主端 微信支付统一下单
     * @param queryIn
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "车主端 微信支付统一下单", notes = "车主端 微信支付统一下单")
    @RequestMapping(value = "/car/unifiedOrder", method = RequestMethod.POST)
    public Response<UnifiedOrderResult> unifiedOrder(
            @RequestBody @Valid @ApiParam(required = true) UnifiedOrderQueryIn queryIn, BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0: UserContext.getUserId();
        UnifiedOrderResult result = unifiedOrderService.unifiedOrder(queryIn, userId);

        return success(result);
    }

    /**
     * 车主端 微信支付统一下单成功后，查看支付是否成功接口
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "车主端 微信支付统一下单成功后，查看支付是否成功接口", notes = "车主端 微信支付统一下单成功后，查看支付是否成功接口")
    @RequestMapping(value = "/car/weiChatPayResult", method = RequestMethod.POST)
    public Response weiChatPayResult(@RequestBody @Valid @ApiParam(required = true) StringIn orderSnIn,
                                     BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();

        this.unifiedOrderService.weiChatPayResult(orderSnIn, userId);
        return success();
    }

    /**
     * 车主端 微信支付统一下单成功后,微信回调接口
     * @return
     */
    @ApiOperation(value = "车主端 微信支付统一下单成功后，微信回调接口", notes = "车主端 微信支付统一下单成功后，微信回调接口")
    @RequestMapping(value = "/car/weiChatPayCallBack", method = RequestMethod.GET)
    public Response weiChatPayCallBack(HttpServletRequest request) {
        try {
            LOGGER.info("===> 微信支付回调开始");
            WxPayService wxPayService = new WxPayServiceImpl();
            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId(WeiXinUtils.APP_Id);
            payConfig.setMchId(WeiXinUtils.MCH_Id);
            payConfig.setNotifyUrl(WeiXinUtils.NODITY_URL);
            payConfig.setMchKey(WeiXinUtils.APP_KEY);
            wxPayService.setConfig(payConfig);
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            System.err.println(xmlResult);
            WeiChatCheckOrderResult result = (WeiChatCheckOrderResult)CommonUtil.toBean(WeiChatCheckOrderResult.class, xmlResult);
            this.unifiedOrderService.weiChatPayCallBack(result);
            //商品订单号
            return success(result);
        }  catch (Exception e){
            LOGGER.info("===> 微信回调发生异常");
            return error(e);
        }
    }

    /**
     * 车主端 获取可用的最大优惠券
     * @param priceIn
     * @return
     */
    @ApiOperation(value = "车主端 获取可用的最大优惠券", notes = "车主端 获取可用的最大优惠券")
    @RequestMapping(value = "/car/getMaxUsableCoupon", method = RequestMethod.POST)
    public Response<MaxUseableCouponOut> getMaxUsableCoupon(@RequestBody @Valid @ApiParam(required = true) PriceIn priceIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        MaxUseableCouponOut out = this.unifiedOrderService.getMaxUsableCoupon(priceIn ,userId);

        return success(out);
    }

    /**
     * 车主端 取消订单
     * @param cancelOrderIn
     * @return
     */
    @ApiOperation(value = "车主端 取消订单", notes = "车主端 取消订单")
    @RequestMapping(value = "/car/cancelOrder", method = RequestMethod.POST)
    public Response cancelOrder(@RequestBody @Valid @ApiParam(required = true) CancelOrderIn cancelOrderIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        this.unifiedOrderService.cancelOrder(cancelOrderIn ,userId);

        return success().setMessage("取消成功");
    }


}
