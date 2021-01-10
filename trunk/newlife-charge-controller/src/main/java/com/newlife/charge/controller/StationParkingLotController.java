package com.newlife.charge.controller;


import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.GeneralStationParkingLotInfoOut;
import com.newlife.charge.core.dto.out.GeneralStationParkingLotPageOut;
import com.newlife.charge.core.dto.out.SelectListOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.StationParkingLotService;
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
 * 总后台 桩站车位管理
 */
@PlatLogModule(moduleId = "/api/stationParkingLot", moduleName = "桩站车位管理")
@Api(description = "总后台 桩站车位管理")
@RestController
@RequestMapping("/api/stationParkingLot")
public class StationParkingLotController extends BaseController {


    @Autowired
    private StationParkingLotService service;


    /**
     * 总后台 桩站车位分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:stationParkingLot:general:menu")
    @ApiOperation(value = "总后台分页查询数据", notes = "总后台分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<GeneralStationParkingLotPageOut>> generalPageList(
            @RequestBody @ApiParam(required = true) GeneralStationParkingLotPageIn pageIn) {
        PageInfo<GeneralStationParkingLotPageOut> page = new PageInfo<>();
        try {
            page = this.service.page(pageIn);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general stationParkingLot pageList error", e);
            return error(page);
        }
    }


    /**
     * 总后台 某桩站暂未绑定充电枪的车位列表，用于下拉框
     *
     * @return
     */
    @ApiOperation(value = "总后台 某桩站暂未绑定充电枪的车位列表，用于下拉框", notes = "总后台 某桩站暂未绑定充电枪的车位列表，用于下拉框")
    @RequestMapping(value = "/general/selectList", method = RequestMethod.POST)
    public Response<List<SelectListOut>> generalSelectList(
            @RequestBody @Valid @ApiParam(required = true) StationParkingLotSelectListIn listIn,
            BindingResult bindingResult) {
        throwBindingResultErrorMsg(bindingResult);
        List<SelectListOut> list = null;
        try {
            list = this.service.selectList(listIn.getStationId(), listIn.getStationParkingLotId());
            return success(list);
        } catch (Exception e) {
            LOGGER.error("general stationParkingLot selectList error", e);
            return error(list);
        }
    }


    /**
     * 总后台 编辑时数据回填
     *
     * @param infoIn
     * @return
     */
    @Deprecated
    @RequiresPermissions(value = "sys:stationParkingLot:general:update")
    @ApiOperation(value = "总后台查看详情", notes = "总后台根据主键ID查询详情信息")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<GeneralStationParkingLotInfoOut> generalInfo(@RequestBody @Valid @ApiParam(required = true) InfoIn infoIn,
                                                                 BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        GeneralStationParkingLotInfoOut info = this.service.getInfoById(infoIn.getId());

        return success(info);
    }


    /**
     * 总后台 新增车位
     *
     * @param saveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:stationParkingLot:general:create")
    @ApiOperation(value = "总后台新增车位", notes = "总后台新增车位")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response generalSave(@RequestBody @Valid @ApiParam(required = true) StationParkingLotSaveIn saveIn,
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
    @RequiresPermissions(value = "sys:stationParkingLot:general:update")
    @ApiOperation(value = "总后台更新", notes = "总后台更新")
    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
    public Response generalUpdate(@RequestBody @Valid @ApiParam(required = true) StationParkingLotUpdateIn updateIn,
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
    @RequiresPermissions(value = "sys:stationParkingLot:general:delete")
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
    @RequiresPermissions(value = "sys:stationParkingLot:general:delete")
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
