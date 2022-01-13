package com.example.bishe.mapper.login;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bishe.entity.login.Permission;
import com.example.bishe.entity.login.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-20
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Permission> getRolePermissionsByRoleId(Integer roleId);
}
