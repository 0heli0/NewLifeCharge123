/**
 * Author: zhengyou
 * Date:   2018/12/11 15:48
 * Descripition:地区
 */
package com.newlife.charge.controller;

import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.dto.in.AreaIn;
import com.newlife.charge.core.dto.out.AreaOut;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author admin
 */
@PlatLogModule(moduleId = "/api/noToken/area", moduleName = "城市区域模块")
@Api(description = "城市区域，不需要token")
@RestController
@RequestMapping("/api/noToken/area")
public class AreaController extends BaseController {


    @Autowired
    private AreaService areaService;

    /**
     * 区域列表
     *
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "查询区域列表", notes = "根据父节点parentId查询子节点")
    @RequestMapping(value = "/selectList", method = RequestMethod.POST)
    public Response<List<AreaOut>> selectList(@RequestBody @ApiParam AreaIn areaIn) {

        List<AreaOut> list = new ArrayList<>();
        try {
            list = this.areaService.list(areaIn.getParentId());
        } catch (Exception e) {
            LOGGER.error("area selectList error ", e);
            return error(list);
        }
        return success(list);
    }
}
