package com.newlife.charge.service.impl;

import com.google.common.collect.Lists;
import com.newlife.charge.common.BeanMapper;
import com.newlife.charge.common.Collections3;
import com.newlife.charge.core.domain.exModel.PermissionInfoEx;
import com.newlife.charge.core.domain.exModel.RolePermissionEx;
import com.newlife.charge.core.dto.in.PermissionInfoIn;
import com.newlife.charge.core.dto.out.PermissionInfoOut;
import com.newlife.charge.dao.PermissionInfoMapper;
import com.newlife.charge.dao.RolePermissionRefMapper;
import com.newlife.charge.service.PermissionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionInfoServiceImpl implements PermissionInfoService {

    @Autowired
    private PermissionInfoMapper permissionInfoMapper;

    @Autowired
    private RolePermissionRefMapper rolePermissionRefMapper;

    @Override
    public List<PermissionInfoOut> list(PermissionInfoIn in) {
        List<PermissionInfoEx> list = this.permissionInfoMapper.page(in.getRoleId(),in.getProjectType());

        return sort(BeanMapper.mapList(list, PermissionInfoOut.class));
    }



    private List<PermissionInfoOut> sort(List<PermissionInfoOut> permissionInfoOuts) {
        List<PermissionInfoOut> parents = Lists.newArrayList();

        //处理排序权限数据
        if (Collections3.isNotEmpty(permissionInfoOuts)) {
            //父级权限
            for (PermissionInfoOut permissionInfoOut : permissionInfoOuts) {
                if (permissionInfoOut.getParentId().equals(0)) {
                    parents.add(permissionInfoOut);
                }

            }

            //子级权限
            for (PermissionInfoOut parent : parents) {
                for (PermissionInfoOut permissionInfoOut : permissionInfoOuts) {
                    if (permissionInfoOut.getParentId().equals(parent.getId())) {
                        parent.getChildren().add(permissionInfoOut);

                        //桩站后台 有三级权限 在获取子级权限
                        List<PermissionInfoOut> list = permissionInfoOuts.stream().filter(p -> permissionInfoOut.getId().equals(p.getParentId())).collect(Collectors.toList());
                        if(Collections3.isNotEmpty(list)) {
                            permissionInfoOut.getChildren().addAll(list);
                        }
                    }
                }
            }

        }
        return parents;
    }

    @Override
    public List<PermissionInfoOut> getByRoleId(PermissionInfoIn permissionInfoIn) {

        List<PermissionInfoEx> list = this.permissionInfoMapper.getByRoleId(permissionInfoIn);

        return sort(BeanMapper.mapList(list, PermissionInfoOut.class));
    }

    @Override
    public List<RolePermissionEx> permissionSname(Integer roleId) {
        List<Integer> roleIdList = new ArrayList<>();
        roleIdList.add(roleId);
        List<RolePermissionEx> rolePermissionExes = rolePermissionRefMapper.getByRoleIdList(roleIdList);
        return rolePermissionExes;
    }
}
