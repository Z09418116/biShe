package com.example.bishe.entity.restaurant;

import com.example.bishe.config.base.AbstractPaginationQueryCondition;
import lombok.Data;

@Data
public class RestaurantQueryCondition extends AbstractPaginationQueryCondition {
    /**
     * 餐馆名称
     */
    private String restaurantName;

    /**
     * 餐馆地址
     */
    private String address;

    /**
     * 最小检查次数
     */
    private String minCheckCount;

    /**
     * 最大检查次数
     */
    private String maxCheckCount;
}
