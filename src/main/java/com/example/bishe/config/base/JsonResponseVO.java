package com.example.bishe.config.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 *
 * @author Matt.Lu
 * @date 2022/1/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponseVO {
    private int code;
    private String message;
    private Object obj;

    /**
     * 成功返回
     * @param message
     * @return
     */
    public static JsonResponseVO success(String message){

        return new JsonResponseVO(200,message,null);
    }

    /**
     * 成功返回
     * @param message
     * @param obj
     * @return
     */
    public static JsonResponseVO success(String message,Object obj){
        return new JsonResponseVO(200,message,obj);
    }

    /**
     * 失败返回
     * @param message
     * @return
     */
    public static JsonResponseVO error(String message){

        return new JsonResponseVO(500,message,null);
    }

    /**
     * 失败结果
     * @param message
     * @param obj
     * @return
     */
    public static JsonResponseVO error(String message,Object obj){
        return new JsonResponseVO(400,message,obj);
    }


}
