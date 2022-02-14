package com.example.bishe.entity.restaurant;

import lombok.Data;

@Data
public class RestaurantInfo {
    /**
     * 餐馆id
     */
    private Long id;

    /**
     * 餐馆名称
     */
    private String restaurantName;

    /**
     * 餐馆主人名称
     */
    private String legalPersonName;

    /**
     * 餐馆主人联系电话
     */
    private String legalPersonPhone;


    /**
     * 餐馆地址
     */
    private String address;

    /**
     * 本年度检查次数
     */
    private String yearCheckCount;

    /**
     * 本年度检查通过次数
     */
    private String yearPassCount;

    /**
     * 需关注程度(1-正常, 2-严重,3-非常严重)
     */
    private String attention;

    /**
     * 最近检查时间
     */
    private String recentCheckTime;

    /**
     * 是否删除该用店铺  1-不删除   0-删除
     */
    private String deletedFlag;

    /**
     * 是否冻结该店铺   1-正常使用   0-冻结
     */
    private String freezedFlag;


}
