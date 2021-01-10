package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.domain.page.Pageable;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.CarNoticeOut;
import com.newlife.charge.core.dto.out.NoticeOut;
import com.newlife.charge.core.enums.ReadStatusEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.NoticeService;
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
 * 站内信管理
 */
@PlatLogModule(moduleId = "/api/notice", moduleName = "站内信管理")
@Api(description = "站内信管理")
@RestController
@RequestMapping("/api/notice")
public class NoticeController extends BaseController {


    @Autowired
    private NoticeService noticeService;


    /**
     * 总后台分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:notice:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<NoticeOut>> generalPageList(@RequestBody @ApiParam(required = true) GeneralNoticePageIn pageIn) {
        PageInfo<NoticeOut> page = new PageInfo<>();
        try {
            page = this.noticeService.page(pageIn.getPageNo(), pageIn.getPageSize(),pageIn.getTitle());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("notice pageList error", e);
            return error(page);
        }
    }


    /**
     * 车主分页查询数据（包含已读未读）
     *
     * @return
     */
    @ApiOperation(value = "车主分页查询数据", notes = "车主分页查询数据")
    @RequestMapping(value = "/car/pageList", method = RequestMethod.POST)
    public Response<PageInfo<CarNoticeOut>> carPageList(@RequestBody @ApiParam(required = true) Pageable pageable) {
        PageInfo<CarNoticeOut> page = new PageInfo<>();
        try {
            page = this.noticeService.carPage(pageable.getPageNo(), pageable.getPageSize(), UserContext.getUserId());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("car notice pageList error", e);
            return error(page);
        }
    }


    /**
     * 车主站内信状态更新为已读
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @ApiOperation(value = "车主站内信状态更新为已读", notes = "车主站内信状态更新为已读")
    @RequestMapping(value = "/car/updateStatus", method = RequestMethod.POST)
    public Response carUpdateStatus(@RequestBody @ApiParam(required = true) CarNoticeReadIn carNoticeReadIn) {
        try {
            this.noticeService.carUpdateStatus(UserContext.getUserId(), carNoticeReadIn.getNoticeId(), ReadStatusEnum.READED.getValue());
            return success();
        } catch (Exception e) {
            LOGGER.error("car notice updateStatus error", e);
            return error();
        }
    }


    /**
     * 总后台查看详情
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:notice:general:info")
    @ApiOperation(value = "总后台查看详情", notes = "总后台根据主键ID查询详情信息")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<NoticeOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer id = infoIn.getId();
        NoticeOut noticeOut = this.noticeService.info(id);
        return success(noticeOut);
    }

    /**
     * 总后台新增
     *
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:notice:general:create")
    @ApiOperation(value = "总后台新增", notes = "总后台新增")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response generalSave(@RequestBody @Valid @ApiParam(required = true) NoticeSaveIn saveIn,
                         BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.noticeService.save(saveIn);
        return success().setMessage("保存成功");
    }


//    /**
//     * 总后台更新
//     *
//     * @param updateIn
//     * @return
//     */
//    @Deprecated
//    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
//    @RequiresPermissions(value = "sys:notice:general:update")
//    @ApiOperation(value = "总后台更新", notes = "总后台更新")
//    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
//    public Response generalUpdate(@RequestBody @Valid @ApiParam(required = true) NoticeUpdateIn updateIn, BindingResult bindingResult) {
//
//        throwBindingResultErrorMsg(bindingResult);
//        this.noticeService.update(updateIn);
//        return success().setMessage("更新成功");
//    }

    /**
     * 总后台删除
     *
     * @param delIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:notice:general:delete")
    @ApiOperation(value = "总后台删除", notes = "总后台根据站内信主键ID删除单条记录")
    @RequestMapping(value = "/general/del", method = RequestMethod.POST)
    public Response generalDel(@RequestBody @Valid @ApiParam(required = true) DelIn delIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer id = delIn.getId();
        if (id != null) {
            this.noticeService.delete(id);

            return success().setMessage("删除成功");
        } else {
            return error("需要删除的站内信主键ID不能为空");
        }
    }

    /**
     * 总后台批量删除
     *
     * @param delsIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:notice:general:delete")
    @ApiOperation(value = "总后台批量删除", notes = "总后台根据主键ID数组，批量删除")
    @RequestMapping(value = "/general/dels", method = RequestMethod.POST)
    public Response generalDels(@RequestBody @Valid @ApiParam(required = true) DelsIn delsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (delsIn != null && delsIn.getIds() != null) {
            this.noticeService.deletes(delsIn.getIds());
            return success().setMessage("批量删除成功");
        } else {
            return error("批量删除的站内信主键不能为空");
        }
    }
}
