package com.example.bishe.service.login.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bishe.entity.login.Permission;
import com.example.bishe.entity.login.Role;
import com.example.bishe.entity.login.User;
import com.example.bishe.mapper.login.UserMapper;
import com.example.bishe.service.login.RoleService;
import com.example.bishe.service.login.UserService;
import com.example.bishe.utils.JWTutil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author matt.lu
 * @since 2021-04-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(String username, String password, String code, HttpSession session, HttpServletResponse response, Model model) {
        //在校验登陆之前先检验验证码的正确性
        String realAuthCode = (String) session.getAttribute("code");
        if(!realAuthCode.equalsIgnoreCase(code)) {
            model.addAttribute("msg","验证码错误!");
            return "login";
        }
        //1.获取 Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装 token
        UsernamePasswordToken shiroToken = new UsernamePasswordToken(username, password);
        //3.调用 subject.login(token) 方法
        try {
            subject.login(shiroToken);
            //登录成功之后返回 token 给客户端
            Map<String,String> map = new HashMap<String, String>();
            map.put("username",username);
            String token = JWTutil.getToken(map);  //登陆成功，生成JWT token
            response.setHeader("token",token); // 将 token 设置在 header 里面
            model.addAttribute("token",token);
        }catch (UnknownAccountException e1) {
            //用户名不存在
            model.addAttribute("msg","用户名不存在!");
            return "/login";
        }catch (IncorrectCredentialsException e2) {
            model.addAttribute("msg","密码错误!");
            return "/login";
        }
        return "/index";
    }

    @Override
    public User getUserByUserName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();
        wrapper.eq("username",username);
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public List<Permission> getUserPermissionsByUserId(Integer userId) {
        List<Role> roles = getUserRoleByUserId(userId);
        List<Permission> permissions = new LinkedList<Permission>();
        roles.stream().forEach(role -> {
            permissions.addAll(roleService.getRolePermissionsByRoleId(role.getRoleid()));
        });
        return permissions;
    }

    @Override
    public List<Role> getUserRoleByUserId(Integer userId) {
        return this.baseMapper.getUserRoleByUserId(userId);
    }

    @Override
    public Integer add(User user) {
        return userMapper.add(user);
    }

    @Override
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/index";
    }

}
