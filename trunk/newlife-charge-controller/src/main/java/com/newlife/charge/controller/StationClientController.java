package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.SelectListOut;
import com.newlife.charge.core.dto.out.StationClientInfoOut;
import com.newlife.charge.core.dto.out.StationClientPageOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.StationClientService;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 总后台 充电桩管理
 */
@PlatLogModule(moduleId = "/api/stationClient", moduleName = "充电桩管理")
@Api(description = "总后台 桩站充电桩管理")
@RestController
@RequestMapping("/api/stationClient")
public class StationClientController extends BaseController {


    @Autowired
    private StationClientService service;


    /**
     * 总后台 桩站充电桩分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:stationClient:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<StationClientPageOut>> generalPageList(
            @RequestBody @ApiParam(required = true) StationClientPageIn pageIn) {
        PageInfo<StationClientPageOut> page = new PageInfo<>();
        try {
            page = this.service.page(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general stationClient pageList error", e);
            return error(page);
        }
    }


    /**
     * 总后台查看详情
     *
     * @param infoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.VIEW)
    @RequiresPermissions(value = "sys:stationClient:general:info")
    @ApiOperation(value = "总后台查看详情", notes = "总后台根据主键ID查询详情信息")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<StationClientInfoOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
                                                      BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);
        StationClientInfoOut info = this.service.getInfoById(infoIn.getId());
        return success(info);
    }


    /**
     * 总后台 某桩站绑定充电枪未达到上限的充电桩列表查询，用于下拉框
     *
     * @return
     */
    @ApiOperation(value = "总后台 某桩站绑定充电枪未达到上限的充电桩列表查询，用于下拉框", notes = "总后台 某桩站绑定充电枪未达到上限的充电桩列表查询，用于下拉框")
    @RequestMapping(value = "/general/selectList", method = RequestMethod.POST)
    public Response<List<SelectListOut>> generalSelectList(
            @RequestBody @Valid @ApiParam(required = true) StationClientSelectListIn listIn,
            BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);

        List<SelectListOut> list = new ArrayList<>();
        try {
            list = this.service.selectList(listIn.getStationId(), listIn.getStationClientId());
            return success(list);
        } catch (Exception e) {
            LOGGER.error("general stationClient selectList error", e);
            return error(list);
        }
    }


    /**
     * 总后台 新增充电桩
     *
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:stationClient:general:create")
    @ApiOperation(value = "总后台新增充电桩", notes = "总后台新增充电桩")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response generalSave(@RequestBody @Valid @ApiParam(required = true) StationClientSaveIn saveIn,
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
    @RequiresPermissions(value = "sys:stationClient:general:update")
    @ApiOperation(value = "总后台更新", notes = "总后台更新")
    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
    public Response generalUpdate(@RequestBody @Valid @ApiParam(required = true) StationClientUpdateIn updateIn,
                                  BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        this.service.update(updateIn);

        return success().setMessage("更新成功");
    }


    /**
     * 总后台 编辑时数据回填接口
     *
     * @param infoIn
     * @return
     */
    @RequiresPermissions(value = "sys:stationClient:general:update")
    @ApiOperation(value = "总后台编辑时数据回填接口", notes = "总后台编辑时数据回填接口")
    @RequestMapping(value = "/general/updateInfo", method = RequestMethod.POST)
    public Response<StationClientInfoOut> generalUpdateInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
                                                            BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);
        StationClientInfoOut info = this.service.getInfoById(infoIn.getId());
        return success(info);
    }


    /**
     * 总后台删除
     *
     * @param delIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:stationClient:general:delete")
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
    @RequiresPermissions(value = "sys:stationClient:general:delete")
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
}
