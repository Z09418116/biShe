package com.example.bishe.config;

import com.example.bishe.entity.restaurant.RestaurantInfo;
import lombok.Getter;

import java.util.ArrayList;

/**
 * @ClassName ImportArrayException
 * @Description 自定义导入异常(数组)
 * @Author Matt.Lu
 * @DATE 2022/2/15
 */
@Getter
public final class RestaurantException extends RuntimeException {

    private final ArrayList<RestaurantInfo> list;

    public RestaurantException(Throwable throwable) {
        super(throwable);
        list = null;
    }

    public RestaurantException(String message, ArrayList<RestaurantInfo> list) {
        super(message);
        this.list = list;
    }

    public RestaurantException(String message) {
        super(message);
        list = null;
    }
}
