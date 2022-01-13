package com.example.bishe.service.login;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bishe.entity.login.Permission;
import com.example.bishe.entity.login.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-20
 */
public interface RoleService extends IService<Role> {
    List<Permission> getRolePermissionsByRoleId(Integer roleId);
}
