package com.example.bishe.mapper.login;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bishe.entity.login.Permission;
import com.example.bishe.entity.login.Role;
import com.example.bishe.entity.login.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-04-20
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<Permission> getUserPermissionsByUserId(Integer userId);

    List<Role> getUserRoleByUserId(Integer userId);

    Integer add(@Param("user") User user);

}
