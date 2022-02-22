package com.example.bishe.mapper.login;

import com.example.bishe.entity.login.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

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
