package com.example.bishe.config;

import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.entity.restaurant.RestaurantInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 统一处理异常
 * @Author Chris.Liu
 * @DATE 2021/9/23
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 自定义导入异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ImportException.class)
    public JsonResponseVO handleImportException(ImportException e) {
        LOGGER.error(e.getMessage(), e);
        final JsonResponseVO result = new JsonResponseVO();
        result.setSuccess(Boolean.FALSE);
        result.setReason(e.getMessage());
        return result;
    }

    /**
     * 自定义餐馆信息表导入异常
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(RestaurantException.class)
    public JsonResponseVO handleImportException(RestaurantException e) {
        LOGGER.error(e.getMessage(), e);
        final JsonResponseVO result = new JsonResponseVO();
        result.setSuccess(Boolean.FALSE);
        result.setSuccess(Boolean.TRUE);
        result.setReason(e.getMessage());
        return result;
    }
}
