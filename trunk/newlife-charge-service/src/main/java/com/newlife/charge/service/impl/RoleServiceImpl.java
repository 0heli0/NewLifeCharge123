package com.newlife.charge.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.core.domain.RoleInfo;
import com.newlife.charge.core.domain.RolePermissionRef;
import com.newlife.charge.core.domain.RoleUserRef;
import com.newlife.charge.core.domain.page.PageInfo;
import com.newlife.charge.core.dto.in.RoleInfoQuery;
import com.newlife.charge.core.dto.in.RoleSaveOrUpdate;
import com.newlife.charge.core.dto.out.RoleInfoOut;
import com.newlife.charge.core.dto.out.RoleOut;
import com.newlife.charge.core.enums.ProjectTypeEnum;
import com.newlife.charge.core.enums.YesNoEnum;
import com.newlife.charge.core.exception.BizException;
import com.newlife.charge.core.exception.ERROR;
import com.newlife.charge.dao.RoleInfoMapper;
import com.newlife.charge.dao.RolePermissionRefMapper;
import com.newlife.charge.dao.RoleUserRefMapper;
import com.newlife.charge.service.RoleService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;
    @Autowired
    private RoleUserRefMapper roleUserRefMapper;

    @Autowired
    private RolePermissionRefMapper rolePermissionRefMapper;

    @Autowired
    private Mapper dozer;

    @Override
    public void saveRolePermissionRefAll(Integer[] permissionArr, Integer roleId) {
        if (permissionArr != null && permissionArr.length > 0) {
            int length = permissionArr.length;
            Date now = new Date();
            for (int i = 0; i < length; i++) {
                Integer permissionId = permissionArr[i];
                RolePermissionRef rolePermissionRef = new RolePermissionRef();
                rolePermissionRef.setRoleId(roleId);
                rolePermissionRef.setPermissionId(permissionId);
                rolePermissionRef.setCreateTime(now);
                this.rolePermissionRefMapper.insert(rolePermissionRef);
            }
        }
    }

    @Override
    public PageInfo<RoleOut> page(int pageNo, int pageSize, ProjectTypeEnum projectTypeEnum, Integer stationId) {
        PageHelper.startPage(pageNo, pageSize);

        Page<RoleInfo> page = this.roleInfoMapper.page(projectTypeEnum.getValue(), stationId);
        List<RoleInfo> list = page.getResult();
        List<RoleOut> roleOutList = BeanMapper.mapList(list, RoleOut.class);
        return new PageInfo<>(pageNo,pageSize,page.getTotal(),roleOutList);
    }

    @Override
    public List<RoleOut> list(ProjectTypeEnum projectTypeEnum, Integer stationId) {
        List<RoleInfo> list = this.roleInfoMapper.page(projectTypeEnum.getValue(), stationId);
        return BeanMapper.mapList(list, RoleOut.class);
    }

    @Override
    public RoleInfoOut info(Integer roleId) {
        RoleInfo roleInfo = this.roleInfoMapper.get(roleId);
        return this.dozer.map(roleInfo, RoleInfoOut.class);
    }

    @Transactional
    @Override
    public void save(RoleSaveOrUpdate roleSaveOrUpdate) {
        //保存角色
        RoleInfo roleInfo = this.dozer.map(roleSaveOrUpdate, RoleInfo.class);
        roleInfo.setId(null);
        roleInfo.setCreateTime(new Date());
        roleInfo.setCustom(YesNoEnum.YES.getValue());
        this.roleInfoMapper.insert(roleInfo);

        //保存 角色权限
        this.saveRolePermissionRefAll(roleSaveOrUpdate.getPermissionArr(), roleInfo.getId());
    }

    @Transactional
    @Override
    public void update(RoleSaveOrUpdate roleSaveOrUpdate) {
        //更新
        RoleInfo roleInfo = this.dozer.map(roleSaveOrUpdate, RoleInfo.class);
        this.roleInfoMapper.update(roleInfo);

        //删除 角色对应的所有权限
        this.rolePermissionRefMapper.deleteByRoleId(roleInfo.getId());

        //保存 角色权限
        this.saveRolePermissionRefAll(roleSaveOrUpdate.getPermissionArr(), roleInfo.getId());
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        //已使用的角色不能删除
        if(this.hasUsed(id)){
            throw new BizException(ERROR.ROLE_HAS_USED_NOT_DELETE);
        }
        this.deletes(new int[]{id});
    }

    /**
     * 判断角色是否被使用
     * @param id
     * @return
     */
    private boolean hasUsed(Integer id){
        boolean isUsed = false;
        //查询角色是否有关联用户
        List<RoleUserRef> roleUserRefList = roleUserRefMapper.getByRoleId(id);
        if(roleUserRefList!=null&&roleUserRefList.size()>0){
            isUsed=true;
        }
        return isUsed;
    }

    @Transactional
    @Override
    public void deletes(int[] ids) {

        //删除角色权限关联表信息
        this.rolePermissionRefMapper.deleteByRoleIds(ids);
        //删除角色信息
        this.roleInfoMapper.deleteByIds(ids);
    }

    @Override
    public List<RoleInfo>  getByQuery(RoleInfoQuery query) {

        return roleInfoMapper.getByQuery(query);
    }

    @Override
    public RoleInfo getByUserId(RoleInfoQuery query) {
        return roleInfoMapper.getByUserId(query);
    }
}
