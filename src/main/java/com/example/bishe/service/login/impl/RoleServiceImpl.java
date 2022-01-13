package com.example.bishe.service.login.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bishe.entity.login.Permission;
import com.example.bishe.entity.login.Role;
import com.example.bishe.mapper.login.RoleMapper;
import com.example.bishe.service.login.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Permission> getRolePermissionsByRoleId(Integer roleId) {
        return this.baseMapper.getRolePermissionsByRoleId(roleId);
    }
}
