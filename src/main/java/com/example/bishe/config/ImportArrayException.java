package com.example.bishe.config;

import com.example.bishe.entity.restaurant.RestaurantInfo;
import lombok.Getter;

import java.util.ArrayList;

/**
 * @ClassName ImportArrayException
 * @Description 自定义导入异常(数组)
 * @Author Chris.Liu
 * @DATE 2021/9/28
 */
@Getter
public final class ImportArrayException extends RuntimeException {

    private final ArrayList<RestaurantInfo> list;

    public ImportArrayException(Throwable throwable) {
        super(throwable);
        list = null;
    }

    public ImportArrayException(String message, ArrayList<RestaurantInfo> list) {
        super(message);
        this.list = list;
    }

    public ImportArrayException(String message) {
        super(message);
        list = null;
    }
}

