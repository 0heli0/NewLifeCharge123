/**
 * Author: Admin
 * Date:   2019/5/22 14:50
 * Descripition:
 */
package com.newlife.charge.controller.webSocket;

import com.newlife.charge.controller.BaseController;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.service.impl.WebSocketServerImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/api/checkCenter")
@Api(description = "webSocket连接")
public class CheckCenterController extends BaseController {

    @Autowired
    private WebSocketServerImpl webSocketServerImpl;

    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }

    //推送数据接口
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ResponseBody
    @ApiOperation(value = "webSocket推送消息到指定用户", notes = "webSocket推送消息到指定用户")
    @RequestMapping(value = "/socket/push/{cid}",method = RequestMethod.POST)
    public Response pushToWeb(@PathVariable String cid, @ApiParam(required = true) String message) {
        try {
            WebSocketServerImpl.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            return error(cid+"#"+e.getMessage());
        }
        return success(cid);
    }

    //推送数据接口
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ResponseBody
    @ApiOperation(value = "webSocket推送消息到指定用户-测试用", notes = "webSocket推送消息到指定用户-测试用")
    @RequestMapping(value = "/socket/send/{cid}",method = RequestMethod.POST)
    public Response sendToWeb(@PathVariable String cid, @ApiParam(required = true) String message) {
        try {
            WebSocketServerImpl.sendMessage(cid, null);
        } catch (IOException e) {
            e.printStackTrace();
            return error(cid+"#"+e.getMessage());
        }
        return success(cid);
    }

    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/send/message")
    public Response sendMessage(@PathVariable String cid, String message) {
        try {
            webSocketServerImpl.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return error(cid+"#"+e.getMessage());
        }
        return success(cid);
    }
}
