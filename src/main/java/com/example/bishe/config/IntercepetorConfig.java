package com.example.bishe.config;

import com.example.bishe.intercepetor.JWTIntercepetor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercepetorConfig implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTIntercepetor())
                .addPathPatterns("/**") //拦截所有请求
                .excludePathPatterns("/logout","/js/**","/index","/getAuthCode","/login","/toLogin","/user/**"); // 放行 登陆请求 下面的所有请求
    }
}
