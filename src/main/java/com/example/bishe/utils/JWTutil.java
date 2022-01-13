package com.example.bishe.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTutil {

    private static final String SIGN = "jiachengren"; //JWT 签名
    private static final int DEFAULT_JWT_EXPIRE_DAYS = 7; //默认JWT过期天数

    public static String getToken(Map<String,String> map) {
        JWTCreator.Builder builder = JWT.create();
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,DEFAULT_JWT_EXPIRE_DAYS);
        //1.header 默认
        //2.payload 遍历 map
       map.forEach((k,v) -> {
            builder.withClaim(k,v);
        });
        //3.设置过期时间和签名后生成 token
        String token = builder
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGN));
        return token;
    }

    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

}
