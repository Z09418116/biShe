package com.example.bishe.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.example.bishe.entity.login.Permission;
import com.example.bishe.service.login.PermissionService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private PermissionService permissionService;

    //1.配置ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /** 设置Shiro 内置过滤器
         * anon: 无需认证（登陆）可以访问
         * authc: 必须认证才可以访问
         * user: 如果使用 rememberMe 的功能可以直接访问
         * perms: 该资源必须得到资源权限才可以访问
         * role: 该资源必须得到角色权限才可以访问
        */
        Map<String ,String> filterMap = new LinkedHashMap<String ,String>();
        //配置页面请求拦截
        filterMap.put("/index","anon");
        filterMap.put("/login","anon");
        filterMap.put("/getAuthCode","anon");
        filterMap.put("/js","anon");
        //配置动态授权
        List<Permission> perms = permissionService.list();
        for (Permission permission : perms) {
            filterMap.put(permission.getUrl(),"perms["+permission.getPerm()+"]");
        }
        filterMap.put("/*","authc");
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    //2.配置DefaultSecurityManager
    @Bean(name = "securityManager")
    public DefaultSecurityManager defaultSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(userRealm);
        return defaultSecurityManager;
    }

    //3.配置Realm
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        //1. 创建自定义的 userRealm 对象
        UserRealm userRealm = new UserRealm();
        //2. 设置 userRealm 的 CredentialsMatcher密码校验器
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //2.1 设置加密算法
        matcher.setHashAlgorithmName("MD5");
        //2.2 设置散列次数
        matcher.setHashIterations(6);
        userRealm.setCredentialsMatcher(matcher);
        userRealm.setCacheManager(new RedisCacheManager());
        userRealm.setAuthenticationCachingEnabled(true); //认证
        userRealm.setAuthenticationCacheName("authenticationCache");
        userRealm.setAuthorizationCachingEnabled(true); //授权
        userRealm.setAuthorizationCacheName("authorizationCache");
        return userRealm;
    }

    //4.配置Thymleaf的Shiro扩展标签
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
