package com.example.bishe.shiro;

import com.example.bishe.entity.login.Permission;
import com.example.bishe.entity.login.Role;
import com.example.bishe.entity.login.User;
import com.example.bishe.service.login.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String jobNumber = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.getUserByUserName(jobNumber);
        //1.动态分配角色
        List<Role> roles = userService.getUserRoleByUserId(user.getId());
        roles.stream().forEach(role -> {authorizationInfo.addRole(role.getRole());});
        //2.动态授权
        List<Permission> perms = userService.getUserPermissionsByUserId(user.getId());
        perms.stream().forEach(permission -> {authorizationInfo.addStringPermission(permission.getPerm());});
        return authorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String jobNumber = token.getUsername();
        User user = userService.getUserByUserName(jobNumber);
        if(ObjectUtils.isEmpty(user)) {
            return null;
        }
        return new SimpleAuthenticationInfo(user.getJobNumber(),user.getPassword(), new MyByteSource(user.getSalt()),this.getName());
    }

}
