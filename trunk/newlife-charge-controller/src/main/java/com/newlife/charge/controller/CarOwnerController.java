package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.dto.in.CarOwnerUpdateIn;
import com.newlife.charge.core.dto.in.IntegerIn;
import com.newlife.charge.core.dto.in.StringIn;
import com.newlife.charge.core.dto.out.CarOwnerOut;
import com.newlife.charge.core.enums.UserTypeEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.CarOwnerService;
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
 * 车主用户管理
 */
@PlatLogModule(moduleId = "/api/carOwner", moduleName = "车主用户管理")
@Api(description = "车主用户管理")
@RestController
@RequestMapping("/api/carOwner")
public class CarOwnerController extends BaseController {

    @Autowired
    private CarOwnerService carOwnerService;

    /**
     * 查询车主总信息
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "查询车主总信息", notes = "查询车主总信息")
    @RequestMapping(value = "/car/info", method = RequestMethod.POST)
    public Response<CarOwnerOut> getInfo(){

        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        Integer userType = UserTypeEnum.CAR.getValue();
        CarOwnerOut out = carOwnerService.info(userId, userType);

        return success(out);
    }

    /**
     * 修改车主信息
     * @param updateIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "修改车主信息", notes = "修改车主信息")
    @RequestMapping(value = "/car/updateCarOwner", method = RequestMethod.POST)
    public Response updateCarOwner(@RequestBody @ApiParam CarOwnerUpdateIn updateIn){
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        carOwnerService.updateCarOwner(updateIn,userId);

        return success().setMessage("修改成功");
    }

    /**
     * 车主首页修改性别
     * @param gender
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "车主首页修改性别", notes = "车主首页修改性别")
    @RequestMapping(value = "/car/updateGender", method = RequestMethod.POST)
    public Response updateGender(@RequestBody @Valid @ApiParam IntegerIn gender, BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        carOwnerService.updateGender(gender.getNumber(),userId);

        return success().setMessage("修改成功");
    }

    /**
     * 车主首页修改头像
     * @param avatarUrl
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "车主首页修改头像", notes = "车主首页修改头像")
    @RequestMapping(value = "/car/updateAvatarUrl", method = RequestMethod.POST)
    public Response updateAvatarUrl(@RequestBody @Valid @ApiParam StringIn avatarUrl, BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        carOwnerService.updateAvatarUrl(avatarUrl.getStr(),userId);

        return success().setMessage("修改成功");
    }

    /**
     * 车主首页修改昵称
     * @param nickName
     * @param bindingResult
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "车主首页修改昵称", notes = "车主首页修改昵称")
    @RequestMapping(value = "/car/updateNickName", method = RequestMethod.POST)
    public Response updateNickName(@RequestBody @Valid @ApiParam StringIn nickName, BindingResult bindingResult){

        throwBindingResultErrorMsg(bindingResult);
        Integer userId = UserContext.getUserId()==null?0:UserContext.getUserId();
        carOwnerService.updateNickName(nickName.getStr(),userId);

        return success().setMessage("修改成功");
    }

}
