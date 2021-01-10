package com.newlife.charge.service;


import com.newlife.charge.core.domain.RoleInfo;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.RoleInfoQuery;
import com.newlife.charge.core.dto.in.RoleSaveOrUpdate;
import com.newlife.charge.core.dto.out.RoleInfoOut;
import com.newlife.charge.core.dto.out.RoleOut;
import com.newlife.charge.core.enums.ProjectTypeEnum;

import java.util.List;

/**
 * 角色 service 类
 * <p>
 */
public interface RoleService {



    /**
     * 保存角色权限关联信息
     *
     * @param permissionArr 权限
     * @param roleId 角色ID
     */
    void saveRolePermissionRefAll(Integer[] permissionArr, Integer roleId);


    /**
     * 分页查询
     *
     * @param pageNo      页码
     * @param pageSize    页容量
     * @param projectTypeEnum 所属系统
     * @return
     */
    PageInfo<RoleOut> page(int pageNo, int pageSize, ProjectTypeEnum projectTypeEnum, Integer stationId);

    /**
     * 列表查询
     *
     * @param projectTypeEnum 所属系统
     * @return
     */
    List<RoleOut> list(ProjectTypeEnum projectTypeEnum, Integer stationId);


    /**
     * 详情
     *
     * @param roleId
     * @return
     */
    RoleInfoOut info(Integer roleId);


    /**
     * 创建 角色并绑定其权限
     *
     * @param roleSaveOrUpdate
     */
    void save(RoleSaveOrUpdate roleSaveOrUpdate);

    /**
     * 更新 角色及其权限
     *
     * @param roleSaveOrUpdate
     */
    void update(RoleSaveOrUpdate roleSaveOrUpdate);

    /**
     * 单条删除
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 批量删除
     *
     * @param ids
     */
    void deletes(int[] ids);

    /**
     * @Description: 按条件查询角色
     * @Author: Linzq
     * @CreateDate:  2019/5/14 0014 16:17
     */
    List<RoleInfo> getByQuery(RoleInfoQuery query);

    /**
     * @Description: 查找对应桩站角色
     * @Author: Linzq
     * @CreateDate:  2019/5/14 0014 16:17
     */
    RoleInfo getByUserId(RoleInfoQuery query);
}
