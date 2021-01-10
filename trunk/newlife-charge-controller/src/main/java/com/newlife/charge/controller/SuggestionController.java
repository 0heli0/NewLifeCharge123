package com.newlife.charge.controller;


import com.newlife.charge.common.StringUtil;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.domain.page.Pageable;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.SuggestionOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.SuggestionService;
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
 * 用户反馈管理
 */
@PlatLogModule(moduleId = "/api/suggestion", moduleName = "用户反馈管理")
@Api(description = "用户反馈管理")
@RestController
@RequestMapping("/api/suggestion")
public class SuggestionController extends BaseController {


    @Autowired
    private SuggestionService suggestionService;

    /**
     * 总后台 分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:suggestion:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<SuggestionOut>> pageList(@RequestBody @Valid @ApiParam(required = true, name = "查询条件") Pageable pageable
            , BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        PageInfo<SuggestionOut> page = new PageInfo<>();

        try {
            page = this.suggestionService.page(pageable.getPageNo(), pageable.getPageSize(),null);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("====> suggestion pageList error", e);
            return error(page);
        }
    }


    /**
     * 某车主用户的意见反馈分页查询
     * page
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "某车主用户的意见反馈分页查询", notes = "某车主用户的意见反馈分页查询")
    @RequestMapping(value = "/car/pageList", method = RequestMethod.POST)
    public Response<PageInfo<SuggestionOut>> carPageList(@RequestBody @ApiParam(required = true, name = "查询条件") SuggestionPageIn pageIn) {
        PageInfo<SuggestionOut> page = this.suggestionService.page(pageIn.getPageNo(), pageIn.getPageSize(), UserContext.getUserId());
        return success(page);

    }

    /**
     * （车主端）获取意见反馈详情
     * infoIn
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "（车主端）获取意见反馈详情", notes = "（车主端）获取意见反馈详情")
    @RequestMapping(value = "/car/info", method = RequestMethod.POST)
    public Response<SuggestionOut> carInfo(@RequestBody @Valid @ApiParam(required = true, name = "查询条件") InfoIn infoIn,
                                                    BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        SuggestionOut pageList = this.suggestionService.info(infoIn.getId());

        return success(pageList);

    }


    /**
     * 添加反馈信息(用于车主客户端使用)
     *
     * @param suggestion
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @ApiOperation(value = "新增", notes = "新增")
    @RequestMapping(value = "/car/save", method = RequestMethod.POST)
    public Response save(@RequestBody @Valid @ApiParam(required = true) SuggestionSaveIn suggestion
            , BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        String mobile = suggestion.getMobile();

        if (StringUtil.isEmpty(mobile)) {
            throw new BizException(ERROR.PARAMATER_ERROR);
        }

        int result = this.suggestionService.insert(suggestion);

        if (result > 0) {
            return success().setMessage("新增成功");
        } else {
            throw new BizException(ERROR.INTERNAL_ERROR);
        }

    }

//    /**
//     * 根据条件更新
//     *
//     * @param suggestion
//     * @return
//     */
//    @Deprecated
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
////    @RequiresPermissions(value = "sys:suggestion:general:update")
//    @ApiOperation(value = "根据条件更新", notes = "根据条件更新")
//    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
//    public Response update(@RequestBody @Valid @ApiParam(required = true) SuggestionUpdateIn suggestion
//            , BindingResult bindingResult) {
//
//        throwBindingResultErrorMsg(bindingResult);
//
////        int result = this.suggestionService.update(suggestion);
////
////        if (result > 0) {
////            return success().setMessage("修改成功");
////        } else {
////            throw new BizException(ERROR.RESULT_NULL);
////        }
//
//
//        return error("更新功能已废弃");
//
//    }


//    /**
//     * 根据条件删除
//     *
//     * @param suggestion
//     * @return
//     */
//    @Deprecated
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
//    @RequiresPermissions(value = "sys:suggestion:general:delete")
//    @ApiOperation(value = "根据条件删除", notes = "根据条件删除")
//    @RequestMapping(value = "/general/delete", method = RequestMethod.POST)
//    public Response delete(@RequestBody @ApiParam(required = true) SuggestionDelIn suggestion) {
//
////        Integer id = suggestion.getId();
////
////        if ((id == null || id == 0)) {
////            throw new BizException(ERROR.PARAMATER_ERROR);
////        }
////
////        int result = this.suggestionService.delete(suggestion);
////
////        if (result > 0) {
////            return success().setMessage("删除成功");
////        } else {
////            throw new BizException("删除失败,请重新确认参数");
////        }
//
//
//        return error("删除功能已废弃");
//    }

//    /**
//     * 根据id批量删除
//     *
//     * @param ids
//     * @return
//     */
//    @Deprecated
//    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
//    @RequiresPermissions(value = "sys:suggestion:general:delete")
//    @ApiOperation(value = "根据id批量删除", notes = "根据id批量删除")
//    @RequestMapping(value = "/general/batchDelete", method = RequestMethod.POST)
//    public Response deleteByIds(@RequestBody @Valid @ApiParam(required = true) SuggestionDelsIn ids
//            , BindingResult bindingResult) {
//
//        throwBindingResultErrorMsg(bindingResult);
//
////        try {
////            this.suggestionService.deleteByIds(ids);
////            return success().setMessage("删除成功");
////        } catch (Exception e) {
////            LOGGER.error("====> suggestion batch-delete error", e);
////            return error(e.getMessage());
////        }
//
//        return error("删除功能已废弃");
//
//    }

}
