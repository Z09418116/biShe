package com.example.bishe.controller.restaurant;

import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.controller.personalmanagement.PersonalManagementController;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import com.example.bishe.entity.restaurant.RestaurantInfo;
import com.example.bishe.entity.restaurant.RestaurantQueryCondition;
import com.example.bishe.service.personalmanagement.impl.PersonalManagementServiceImpl;
import com.example.bishe.service.restaurant.impl.RestaurantServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantController.class);

    private RestaurantServiceImpl restaurantServiceImpl;

    @Autowired
    public RestaurantController(RestaurantServiceImpl restaurantServiceImpl) {
        this.restaurantServiceImpl = restaurantServiceImpl;
    }

    /**
     * 餐馆信息-根据条件查询餐馆信息
     *
     * @param restaurantQueryCondition 餐馆查询条件实体类
     * @return JsonResponseVO
     */
    @PostMapping("/query")
    @ResponseBody
    public PagedQueryResult<RestaurantInfo> queryUserDetail(@RequestBody RestaurantQueryCondition restaurantQueryCondition) {
        LOGGER.info("开始根据条件查询餐馆信息-ServiceImpl");
        return restaurantServiceImpl.queryRestaurant(restaurantQueryCondition);
    }

    /**
     * 餐馆信息-新增（修改）餐馆信息
     *
     * @param restaurantInfo 餐馆信息实体类
     * @param session       session
     * @return JsonResponseVO
     */
    @RequestMapping("/userDetail/add")
    @ResponseBody
    public JsonResponseVO addVisitGuidanceEnclosure(@RequestBody RestaurantInfo restaurantInfo, HttpSession session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("餐馆信息-新增");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //新增或修改用户详细信息
        ReturnInfo returnInfo = restaurantServiceImpl.addRestaurant(restaurantInfo);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 餐馆信息-删除餐馆信息
     *
     * @param id 餐馆id
     * @return JsonResponseVO实体类
     */
    @RequestMapping("/delete")
    public JsonResponseVO deleteUserDetail(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("餐馆信息-删除-impl,id:{}",id);
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //删除用户信息
        ReturnInfo returnInfo = restaurantServiceImpl.deleteRestaurant(id);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 餐馆信息-冻结餐馆信息
     *
     * @param id 餐馆id
     * @return JsonResponseVO实体类
     */
    @RequestMapping("/freeze")
    public JsonResponseVO freezeUserDetail(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("人员管理-冻结用户信息-impl,id:{}",id);
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //冻结用户信息
        ReturnInfo returnInfo = restaurantServiceImpl.freezeRestaurant(id);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 餐馆信息-下载导入模板
     */
    @RequestMapping(value = "/export")
    public void exportTemplate(HttpServletResponse response) {
        restaurantServiceImpl.exportTemplate(response);
    }

    /**
     * 餐馆信息-导入月报
     */
    @PostMapping(value = "/import")
    public JsonResponseVO wasteSortingImport(@RequestPart("writtenScoreFile") MultipartFile writtenScoreFile, HttpSession session) {
        return restaurantServiceImpl.importRestaurant(writtenScoreFile,session.getId());
    }
}
