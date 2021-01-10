package com.newlife.charge.controller;

import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.controller.vo.Response;
import com.newlife.charge.core.constant.UserContext;
import com.newlife.charge.core.domain.RoleInfo;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.domain.page.Pageable;
import com.newlife.charge.core.dto.in.*;
import com.newlife.charge.core.dto.out.PermissionInfoOut;
import com.newlife.charge.core.dto.out.RoleInfoOut;
import com.newlife.charge.core.dto.out.RoleOut;
import com.newlife.charge.core.enums.ProjectTypeEnum;
import com.newlife.charge.core.enums.YesNoEnum;
import com.newlife.charge.core.enums.log.PlatLogMethodType;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.interceptor.common.annotation.PlatLogMethod;
import com.newlife.charge.interceptor.common.annotation.PlatLogModule;
import com.newlife.charge.interceptor.common.annotation.RequiresPermissions;
import com.newlife.charge.service.PermissionInfoService;
import com.newlife.charge.service.RoleService;
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
import java.util.stream.Collectors;

/**
 * 角色管理 controller
 * <p>
 */
@PlatLogModule(moduleId = "/api/role", moduleName = "角色管理")
@Api(description = "角色管理")
@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionInfoService permissionInfoService;


    /**
     * 总后台 分页查询数据
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:role:general:menu")
    @ApiOperation(value = "总后台 分页查询数据", notes = "总后台 分页查询数据")
    @RequestMapping(value = "/general/pageList", method = RequestMethod.POST)
    public Response<PageInfo<RoleOut>> generalPageList(@RequestBody @ApiParam(required = true) Pageable pageable) {
        PageInfo<RoleOut> page = new PageInfo<>();
        try {
            page = this.roleService.page(pageable.getPageNo(), pageable.getPageSize(), ProjectTypeEnum.PRO_GENERAL, null);
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general role pageList error", e);
            return error(page);
        }
    }

    /**
     * 总后台 角色列表查询
     *
     * @return
     */
    @ApiOperation(value = "总后台 角色列表查询", notes = "总后台 角色列表查询")
    @RequestMapping(value = "/general/selectList", method = RequestMethod.POST)
    public Response<List<RoleOut>> generalSelectList() {
        List<RoleOut> list = new ArrayList<>();
        try {
            list = this.roleService.list(ProjectTypeEnum.PRO_GENERAL, null);
            return success(list);
        } catch (Exception e) {
            LOGGER.error("general role selectList", e);
            return error(list);
        }
    }


    /**
     * 总后台 角色详情
     *
     * @param generalRoleInfoIn
     * @return
     */
    @ApiOperation(value = "总后台 角色详情", notes = "根据角色ID查询角色信息,包括其权限信息，如果不传角色ID，则可用于获取总后台所有权限")
    @RequestMapping(value = "/general/info", method = RequestMethod.POST)
    public Response<RoleInfoOut> generalInfo(@RequestBody @ApiParam(required = true) GeneralRoleInfoIn generalRoleInfoIn) {

        RoleInfoOut roleInfoOut = null;
        Integer id = generalRoleInfoIn.getId();
        if (id != null) {
            roleInfoOut = this.roleService.info(id);
        } else {
            id = -1;
            roleInfoOut = new RoleInfoOut();
        }

        PermissionInfoIn permissionInfoIn = new PermissionInfoIn();
        permissionInfoIn.setRoleId(id);
        permissionInfoIn.setProjectType(ProjectTypeEnum.PRO_GENERAL.getValue());
        List<PermissionInfoOut> permissionOuts = this.permissionInfoService.list(permissionInfoIn);
        roleInfoOut.setParents(permissionOuts);

        return success(roleInfoOut);
    }



    /**
     * 新增角色信息
     *
     * @param generalRoleInfoSaveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:role:general:create")
    @ApiOperation(value = "新增角色信息", notes = "新增角色信息")
    @RequestMapping(value = "/general/save", method = RequestMethod.POST)
    public Response save(
            @RequestBody @Valid @ApiParam(required = true) GeneralRoleInfoSaveIn generalRoleInfoSaveIn,
            BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);


        //检测角色名是否存在
        if (checkRoleNameGeneral(generalRoleInfoSaveIn.getRoleName(),null)){
            return error(ERROR.ROLE_NAME_EXIST);
        }


        RoleSaveOrUpdate roleSaveOrUpdate = new RoleSaveOrUpdate();
        roleSaveOrUpdate.setId(null);
        roleSaveOrUpdate.setStationId(YesNoEnum.NO.getValue());
        roleSaveOrUpdate.setRoleName(generalRoleInfoSaveIn.getRoleName());
        roleSaveOrUpdate.setProject(ProjectTypeEnum.PRO_GENERAL.getValue());
        roleSaveOrUpdate.setRemark(generalRoleInfoSaveIn.getRemark());
        roleSaveOrUpdate.setPermissionArr(generalRoleInfoSaveIn.getPermissionArr());

        this.roleService.save(roleSaveOrUpdate);

        return success().setMessage("保存成功");
    }


    /**
     * 总后台 角色信息更新
     *
     * @param generalRoleInfoUpdateIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @RequiresPermissions(value = "sys:role:general:update")
    @ApiOperation(value = " 总后台 角色信息更新", notes = " 总后台 角色信息更新")
    @RequestMapping(value = "/general/update", method = RequestMethod.POST)
    public Response update(@RequestBody @Valid @ApiParam(required = true) GeneralRoleInfoUpdateIn generalRoleInfoUpdateIn,
                           BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        //检测角色名是否存在
        if (checkRoleNameGeneral(generalRoleInfoUpdateIn.getRoleName(),generalRoleInfoUpdateIn.getId())){
            return error(ERROR.ROLE_NAME_EXIST);
        }

        //更新
        RoleSaveOrUpdate roleSaveOrUpdate = new RoleSaveOrUpdate();
        roleSaveOrUpdate.setId(generalRoleInfoUpdateIn.getId());
        roleSaveOrUpdate.setStationId(YesNoEnum.NO.getValue());
        roleSaveOrUpdate.setRoleName(generalRoleInfoUpdateIn.getRoleName());
        roleSaveOrUpdate.setProject(ProjectTypeEnum.PRO_GENERAL.getValue());
        roleSaveOrUpdate.setRemark(generalRoleInfoUpdateIn.getRemark());
        roleSaveOrUpdate.setPermissionArr(generalRoleInfoUpdateIn.getPermissionArr());

        this.roleService.update(roleSaveOrUpdate);

        return success().setMessage("更新成功");
    }

    /**
     * 总后台 角色删除
     *
     * @param delIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:role:general:delete")
    @ApiOperation(value = "总后台 角色删除", notes = "根据角色主键ID删除单条角色信息")
    @RequestMapping(value = "/general/del", method = RequestMethod.POST)
    public Response generalDel(@RequestBody @Valid @ApiParam(required = true) DelIn delIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        Integer id = delIn.getId();
        if (id != null) {
            this.roleService.delete(id);

            return success().setMessage("删除成功");
        } else {
            return error("需要删除的角色主键ID不能为空");
        }
    }

    /**
     * 总后台 角色批量删除
     *
     * @param delsIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:role:general:delete")
    @ApiOperation(value = "总后台 角色批量删除", notes = "根据角色主键ID数组，批量删除角色信息")
    @RequestMapping(value = "/general/dels", method = RequestMethod.POST)
    public Response generalDels(@RequestBody @Valid @ApiParam(required = true) DelsIn delsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (delsIn != null && delsIn.getIds() != null) {
            this.roleService.deletes(delsIn.getIds());

            return success().setMessage("批量删除成功");
        } else {
            return error("批量删除的角色主键不能为空");
        }
    }

    /**
     * 桩站 新增角色信息
     *
     * @param stationRoleInfoSaveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.INSERT)
    @RequiresPermissions(value = "sys:role:station:create")
    @ApiOperation(value = "桩站 新增角色信息", notes = "桩站 新增角色信息")
    @RequestMapping(value = "/station/save", method = RequestMethod.POST)
    public Response stationSave(
            @RequestBody @Valid @ApiParam(required = true) StationRoleInfoSaveIn stationRoleInfoSaveIn,
            BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        //检测角色是否存在
        if (checkRoleName(stationRoleInfoSaveIn)) {
            return error("角色已存在");
        }

        RoleSaveOrUpdate roleSaveOrUpdate = new RoleSaveOrUpdate();
        roleSaveOrUpdate.setId(null);
        roleSaveOrUpdate.setStationId(UserContext.getStationId());
        roleSaveOrUpdate.setRoleName(stationRoleInfoSaveIn.getRoleName());
        roleSaveOrUpdate.setProject(ProjectTypeEnum.PRO_STATION.getValue());
        roleSaveOrUpdate.setRemark(stationRoleInfoSaveIn.getRemark());
        roleSaveOrUpdate.setPermissionArr(stationRoleInfoSaveIn.getPermissionArr());

        this.roleService.save(roleSaveOrUpdate);

        return success().setMessage("保存成功");
    }


    /**
     * 总后台判断角色名是否已存在
     * @param roleName
     * @param roleId
     * @return
     */
    private boolean checkRoleNameGeneral(String roleName,Integer roleId) {
        //判断角色名字是否存在
        RoleInfoQuery query = new RoleInfoQuery();
        query.setProject(ProjectTypeEnum.PRO_GENERAL.getValue());
        query.setRoleName(roleName);
        List<RoleInfo> role = roleService.getByQuery(query);
        if(Collections3.isNotEmpty(role)) {
            if(roleId != null) {
                //更新 过滤自身
                List<RoleInfo> roleInfos = role.stream().filter(r -> r.getId().equals(roleId)).collect(Collectors.toList());
                if(Collections3.isNotEmpty(roleInfos)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean checkRoleName(@Valid @RequestBody @ApiParam(required = true) StationRoleInfoSaveIn stationRoleInfoSaveIn) {
        //判断角色名字是否存在
        RoleInfoQuery query = new RoleInfoQuery();
        query.setStationId(UserContext.getStationId());
        query.setProject(ProjectTypeEnum.PRO_STATION.getValue());
        query.setRoleName(stationRoleInfoSaveIn.getRoleName());
        List<RoleInfo> role = roleService.getByQuery(query);
        if(Collections3.isNotEmpty(role)) {
            if(stationRoleInfoSaveIn.getId() != null) {
                //更新 过滤自身
                List<RoleInfo> roleInfos = role.stream().filter(r -> r.getId().equals(stationRoleInfoSaveIn.getId())).collect(Collectors.toList());
                if(Collections3.isNotEmpty(roleInfos)) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 桩站 修改角色信息
     *
     * @param stationRoleInfoSaveIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.UPDATE)
    @RequiresPermissions(value = "sys:role:station:update")
    @ApiOperation(value = "桩站 修改角色信息", notes = "桩站 修改角色信息")
    @RequestMapping(value = "/station/update", method = RequestMethod.POST)
    public Response stationUpdate(
            @RequestBody @Valid @ApiParam(required = true) StationRoleInfoSaveIn stationRoleInfoSaveIn,
            BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (stationRoleInfoSaveIn.getId() == null) {
            return error("角色ID不能为空");
        }

        //检测角色是否存在
        if (checkRoleName(stationRoleInfoSaveIn)) {
            return error("角色已存在");
        }

        //更新
        RoleSaveOrUpdate roleSaveOrUpdate = new RoleSaveOrUpdate();
        roleSaveOrUpdate.setId(stationRoleInfoSaveIn.getId());
        roleSaveOrUpdate.setStationId(UserContext.getStationId());
        roleSaveOrUpdate.setRoleName(stationRoleInfoSaveIn.getRoleName());
        roleSaveOrUpdate.setProject(ProjectTypeEnum.PRO_STATION.getValue());
        roleSaveOrUpdate.setRemark(stationRoleInfoSaveIn.getRemark());
        roleSaveOrUpdate.setPermissionArr(stationRoleInfoSaveIn.getPermissionArr());

        this.roleService.update(roleSaveOrUpdate);

        return success().setMessage("更新成功");
    }

    /**
     * 桩站后台 角色批量删除
     *
     * @param delsIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.DELETE)
    @RequiresPermissions(value = "sys:role:station:delete")
    @ApiOperation(value = "桩站后台 角色批量删除", notes = "根据角色主键ID数组，批量删除角色信息")
    @RequestMapping(value = "/station/dels", method = RequestMethod.POST)
    public Response stationDels(@RequestBody @Valid @ApiParam(required = true) DelsIn delsIn, BindingResult bindingResult) {

        throwBindingResultErrorMsg(bindingResult);

        if (delsIn != null && delsIn.getIds() != null) {
            this.roleService.deletes(delsIn.getIds());

            return success().setMessage("删除成功");
        } else {
            return error("删除的角色主键不能为空");
        }
    }

    /**
     * 桩站后台 角色详情
     *
     * @param generalRoleInfoIn
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = {"sys:role:station:info","sys:role:station:update"})
    @ApiOperation(value = "桩站后台 角色详情", notes = "根据角色ID查询角色信息,包括其权限信息")
    @RequestMapping(value = "/station/info", method = RequestMethod.POST)
    public Response<RoleInfoOut> stationInfo(@RequestBody @ApiParam(required = true) GeneralRoleInfoIn generalRoleInfoIn) {

        if(generalRoleInfoIn.getId() == null) {
            return error(ERROR.INVALID_PARAM.message());
        }

        RoleInfoOut roleInfoOut = this.roleService.info(generalRoleInfoIn.getId());

        PermissionInfoIn permissionInfoIn = new PermissionInfoIn();
        permissionInfoIn.setRoleId(generalRoleInfoIn.getId());
        permissionInfoIn.setProjectType(ProjectTypeEnum.PRO_STATION.getValue());
        List<PermissionInfoOut> permissionOuts = this.permissionInfoService.getByRoleId(permissionInfoIn);
        roleInfoOut.setParents(permissionOuts);

        return success(roleInfoOut);
    }

    /**
     * 桩站后台 查询权限列表
     *
     * @param
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站后台 查询权限列表", notes = "桩站后台 查询权限列表")
    @RequestMapping(value = "/station/permissionList", method = RequestMethod.POST)
    public Response permissionList() {


        PermissionInfoIn permissionInfoIn = new PermissionInfoIn();
        permissionInfoIn.setProjectType(ProjectTypeEnum.PRO_STATION.getValue());
        List<PermissionInfoOut> permissionOuts = this.permissionInfoService.list(permissionInfoIn);

        return success(permissionOuts).setMessage("查询成功");
    }

    /**
     * 桩站后台 角色列表查询
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @RequiresPermissions(value = "sys:role:station:menu")
    @ApiOperation(value = "桩站后台 角色列表查询", notes = "桩站后台 角色列表查询")
    @RequestMapping(value = "/station/page", method = RequestMethod.POST)
    public Response<PageInfo<RoleOut>> page(@RequestBody @ApiParam(required = true) Pageable pageable) {
        PageInfo<RoleOut> page = new PageInfo<>();
        try {
            page = this.roleService.page(pageable.getPageNo(), pageable.getPageSize(), ProjectTypeEnum.PRO_STATION, UserContext.getStationId());
            return success(page);
        } catch (Exception e) {
            LOGGER.error("general role selectList", e);
            return error(page);
        }
    }

    /**
     * 桩站后台 角色下拉查询
     *
     * @return
     */
    @PlatLogMethod(operateName = PlatLogMethodType.SEARCH)
    @ApiOperation(value = "桩站后台 角色下拉查询", notes = "桩站后台 角色下拉查询")
    @RequestMapping(value = "/station/selectList", method = RequestMethod.POST)
    public Response<List<RoleOut>> stationSelectList() {
        List<RoleOut> list = new ArrayList<>();
        try {
            RoleInfoQuery query = new RoleInfoQuery();
            query.setProject(ProjectTypeEnum.PRO_STATION.getValue());
            query.setStationId(UserContext.getStationId());
            List<RoleInfo> roleInfos = this.roleService.getByQuery(query);
            return success(BeanMapper.mapList(roleInfos, RoleOut.class));
        } catch (Exception e) {
            LOGGER.error("general role selectList", e);
            return error(list);
        }
    }
}
