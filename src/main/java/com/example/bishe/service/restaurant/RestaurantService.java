package com.example.bishe.service.restaurant;

import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import com.example.bishe.entity.restaurant.RestaurantInfo;
import com.example.bishe.entity.restaurant.RestaurantQueryCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface RestaurantService {
    /**
     * 餐馆信息-根据条件查询查询餐馆信息
     *
     * @param restaurantQueryCondition 查询条件实体类 包含 餐铺名称 地址 次数
     * @return  餐馆具体信息实体类集合
     */
    PagedQueryResult<RestaurantInfo> queryRestaurant(RestaurantQueryCondition restaurantQueryCondition);

    /**
     * 餐馆信息-添加(修改)餐馆信息
     *
     * @param restaurantInfo 用户详细信息实体类
     * @return  是否成功
     */
    ReturnInfo addRestaurant(RestaurantInfo restaurantInfo);

    /**
     * 餐馆信息-删除餐馆信息
     *
     * @param id 餐馆id
     * @return  是否成功
     */
    ReturnInfo deleteRestaurant(Long id);

    /**
     * 餐馆信息-冻结餐馆信息
     *
     * @param id 餐馆id
     * @return  是否成功
     */
    ReturnInfo freezeRestaurant(Long id);

    /**
     * 餐馆信息-修改餐馆状态
     *
     * @param id 餐馆id state 状态
     * @return  是否成功
     */
    Boolean modifyRestaurantState(Long id,Integer state);

    /**
     * 餐馆信息-导入餐馆信息
     *
     * @param file 餐馆信息excel文件
     * @return  是否成功
     */
    JsonResponseVO importRestaurant(MultipartFile file, String userName);

    /**
     * 餐馆信息-下载导入模板
     *
     * @param response the response
     */
    void exportTemplate(HttpServletResponse response);

    /**
     * 双随机-随机抽取检查检查人员和餐馆
     *
     * restaurantInfo 餐馆信息
     */
    RestaurantInfo extractRestaurant();

}
