package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.SelectListOut;
import com.newlife.charge.core.dto.out.StationClientGunInfoOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.StationClientGunService;
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
 * 总后台 桩站充电枪管理
 */
@PlatLogModule(moduleId = "/api/stationClientGun", moduleName = "充电枪管理")
@Api(description = "总后台 桩站充电枪管理")
@RestController
@RequestMapping("/api/stationClientGun")
public class StationClientGunController extends BaseController {


    @Autowired
    private StationClientGunService service;


    /**
     * 总后台 桩站充电枪分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:stationClientGun:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<StationClientGunInfoOut>> generalPageList(
            @RequestBody @ApiParam(required = true) StationClientGunPageIn pageIn) {
        PageInfo<StationClientGunInfoOut> page = new PageInfo<>();
        try {
            page = this.service.page(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general stationClientGun pageList error", e);
            return error(page);
        }
    }



    /**
     * 总后台 未使用的枪编号下拉框
     *
     * @return
     */
    @ApiOperation(value = "总后台 未使用的枪编号下拉框", notes = "总后台 未使用的枪编号下拉框")
    @RequestMapping(value = "/general/selectList", method = RequestMethod.POST)
    public Response<List<SelectListOut>> generalSelectList(
            @RequestBody @Valid @ApiParam(required = true) StationClientGunSelectListIn listIn,
            BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);
        List<SelectListOut> list = null;
        try {
            list = this.service.selectList(listIn.getStationClientId(),listIn.getStationClientGunCode());
            return success(list);
        } catch (Exception e) {
            LOGGER.error("general stationClientGun selectList error", e);
            return error(list);
        }
    }





    /**
     * 总后台-编辑时数据回填接口
     *
     * @param infoIn
     * @return
     */
    @RequiresPermissions(value =  "sys:stationClientGun:general:update")
    @ApiOperation(value = "总后台编辑时数据回填接口", notes = "总后台编辑时数据回填接口")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<StationClientGunInfoOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        StationClientGunInfoOut info = this.service.getInfoById(infoIn.getId());
        return success(info);
    }


    /**
     * 总后台 新增充电枪
     *  1枪1车位绑定
     *  桩上的枪数量限制
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:stationClientGun:general:create")
    @ApiOperation(value = "总后台新增充电枪", notes = "总后台新增充电枪")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response generalSave(@RequestBody @Valid @ApiParam(required = true) StationClientGunSaveIn saveIn,
                                BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.service.save(saveIn);
        return success().setMessage("保存成功");
    }


    /**
     * 总后台更新
     *
     * @param updateIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @RequiresPermissions(value = "sys:stationClientGun:general:update")
    @ApiOperation(value = "总后台更新", notes = "总后台更新")
    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
    public Response generalUpdate(@RequestBody @Valid @ApiParam(required = true) StationClientGunUpdateIn updateIn,
                                  BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (updateIn.getId() == null) {
            return error("ID不能为空");
        }
        this.service.update(updateIn);

        return success().setMessage("更新成功");
    }

    /**
     * 总后台删除
     *
     * @param delIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:stationClientGun:general:delete")
    @ApiOperation(value = "总后台删除", notes = "总后台根据站内信主键ID删除单条记录")
    @RequestMapping(value = "/general/del", method = RequestMethod.POST)
    public Response generalDel(@RequestBody @Valid @ApiParam(required = true) DelIn delIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer id = delIn.getId();
        if (id != null) {
            this.service.delete(id);

            return success().setMessage("删除成功");
        } else {
            return error("请选择需要删除的记录");
        }
    }

    /**
     * 总后台批量删除
     *
     * @param delsIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:stationClientGun:general:delete")
    @ApiOperation(value = "总后台批量删除", notes = "总后台根据主键ID数组，批量删除")
    @RequestMapping(value = "/general/dels", method = RequestMethod.POST)
    public Response generalDels(@RequestBody @Valid @ApiParam(required = true) DelsIn delsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (delsIn != null && delsIn.getIds() != null) {
            this.service.deletes(delsIn.getIds());
            return success().setMessage("批量删除成功");
        } else {
            return error("请选择需要批量删除的记录");
        }
    }

    /**
     * 桩站后台首页 桩站充电枪分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站后台首页 桩站充电枪分页查询数据", notes = "桩站后台首页 桩站充电枪分页查询数据")
    @RequestMapping(value = "/station/pageList", method = RequestMethod.POST)
    public Response<PageInfo<StationClientGunInfoOut>> stationPageList(
            @RequestBody @ApiParam(required = true) StationClientGunPageIn pageIn) {
        PageInfo<StationClientGunInfoOut> page = new PageInfo<>();
        try {
            //前端展示6条
            pageIn.setPageSize(6);
            pageIn.setStationId(UserContext.getStationId());
            page = this.service.page(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general stationClientGun pageList error", e);
            return error(page);
        }
    }

    /**
     * 桩站后台首页 桩站充电枪各种状态数量查询
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站后台首页 桩站充电枪各种状态数量查询", notes = "桩站后台首页 桩站充电枪各种状态数量查询")
    @RequestMapping(value = "/station/clientGunStatus", method = RequestMethod.POST)
    public Response stationClientGunStatus(
            @RequestBody @ApiParam(required = true) StationClientGunPageIn pageIn) {

        return success(this.service.stationClientGunStatus(pageIn));
    }
}
