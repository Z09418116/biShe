package com.example.bishe.mapper.restaurant;


import com.example.bishe.entity.restaurant.RestaurantInfo;
import com.example.bishe.entity.restaurant.RestaurantQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RestaurantMapper {

    /**
     * 根据条件查询餐馆信息数量
     *
     * @param restaurantQueryCondition 查询条件实体类 包含 工号 用户姓名 手机号码
     * @return 符合查询条件的记录数量
     */
    Integer queryRestaurantCount(@Param("restaurantQueryCondition") RestaurantQueryCondition restaurantQueryCondition);

    /**
     * 根据条件查询餐馆信息
     *
     * @param restaurantQueryCondition 查询条件实体类 包含
     * @return  餐馆信息实体类集合
     */
    List<RestaurantInfo> queryRestaurant(@Param("restaurantQueryCondition") RestaurantQueryCondition restaurantQueryCondition);

    /**
     * 判断餐馆是否存在
     *
     * @param id 用户id
     * @return 是否成功
     */
    Integer judgeRestaurantExistence(Long id);

    /**
     * 添加(修改)餐馆信息
     *
     * @param restaurantInfo 餐馆信息实体类
     * @return  是否成功
     */
    Boolean addRestaurant(@Param("restaurantInfo") RestaurantInfo restaurantInfo);

    /**
     * 删除餐馆信息
     *
     * @param id 餐馆id
     * @return  是否成功
     */
    Boolean deleteRestaurant(Long id);

    /**
     * 冻结餐馆信息
     *
     * @param id 餐馆id
     * @return  是否成功
     */
    Boolean freezeRestaurant(Long id);

    /**
     * 修改餐馆信息
     *
     * @param id 餐馆id,restaurantInfo 餐馆信息实体类
     * @return  是否成功
     */
    Boolean updateRestaurant(@Param("restaurantInfo") RestaurantInfo restaurantInfo, @Param("id") Long id);

    /**
     * 双随机-随机抽取检查检查人员和餐馆
     *
     * restaurantInfo 餐馆信息
     */
    RestaurantInfo extractRestaurant();


}
