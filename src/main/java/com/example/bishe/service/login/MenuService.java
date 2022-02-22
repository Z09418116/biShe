package com.example.bishe.service.login;

import com.example.bishe.entity.login.Menu;

import java.util.List;

public interface MenuService {

    /**
     * 根据用户名获取菜单列表
     *
     * @return List<Menu>
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 根据角色获取菜单列表
     *
     * @return
     */
    List<Menu> getMenusWithRole();
}
