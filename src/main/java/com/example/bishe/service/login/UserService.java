package com.example.bishe.service.login;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.bishe.entity.login.Permission;
import com.example.bishe.entity.login.Role;
import com.example.bishe.entity.login.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-20
 */
public interface UserService extends IService<User> {

    String login(String username, String password, String code, HttpSession session, HttpServletResponse response, Model model);

    User getUserByUserName(String username);

    List<Permission> getUserPermissionsByUserId(Integer userId);

    List<Role> getUserRoleByUserId(Integer userId);

    Integer add(User user);
    String logout();
}
