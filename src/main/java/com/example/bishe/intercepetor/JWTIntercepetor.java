package com.example.bishe.intercepetor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.bishe.utils.JWTutil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTIntercepetor implements HandlerInterceptor  {
    private final String TOKEN_NAME = "token";

    //JWT拦截器,所有请求都被这个拦截器拦截,校验header中的token,token校验通过再放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.token一般存放在 header 中,所以从 request.getHeader()中获取 token
        String token = request.getHeader(TOKEN_NAME);
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            JWTutil.verify(token);
            map.put("state",true);
            return true;
        } catch (AlgorithmMismatchException e) {
            map.put("msg","JWT算法不匹配!");
        } catch (SignatureVerificationException e) {
            map.put("msg","JWT签名不匹配!");
        } catch (TokenExpiredException e) {
            map.put("msg","token(用户信息)已经过期,请重新登录!");
        } catch (Exception e) {
            map.put("msg","token无效!");
        }
        map.put("state",false);
        response.setContentType("application/json");
        String msg = new ObjectMapper().writeValueAsString(map);
        response.getWriter().println(msg);
        return false;
    }
}
