package com.example.bishe.controller.constant;

import com.example.bishe.config.Constants;
import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.controller.caseinfo.CaseInfoController;
import com.example.bishe.entity.caseinfo.CaseInfo;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.restaurant.RestaurantInfo;
import com.example.bishe.service.caseinfo.impl.CaseInfoServiceImpl;
import com.example.bishe.service.personalmanagement.PersonalManagementService;
import com.example.bishe.service.personalmanagement.impl.PersonalManagementServiceImpl;
import com.example.bishe.service.restaurant.impl.RestaurantServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/constant")
public class ConstantController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstantController.class);

    private PersonalManagementServiceImpl personalManagementServiceImpl;
    private RestaurantServiceImpl restaurantServiceImpl;
    @Autowired
    public ConstantController(PersonalManagementServiceImpl personalManagementServiceImpl,RestaurantServiceImpl restaurantServiceImpl) {
        this.personalManagementServiceImpl = personalManagementServiceImpl;
        this.restaurantServiceImpl = restaurantServiceImpl;
    }


    /**
     * 双随机-随机抽取检查检查人员和餐馆
     *
     * @return JsonResponseVO实体类
     */
    @RequestMapping("/doubleRandom")
    public UserDetail freezeUserDetail() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("双随机-随机抽取检查检查人员和餐馆");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //
        RestaurantInfo restaurantInfo = restaurantServiceImpl.extractRestaurant();
        UserDetail userDetail = personalManagementServiceImpl.extractUser();
//        CaseInfo caseInfo = new CaseInfo();
//        caseInfo.setRestaurantName(restaurantInfo.getRestaurantName());
//        caseInfo.setLegalPersonName(restaurantInfo.getLegalPersonName());
//        caseInfo.setLegalPersonPhone(restaurantInfo.getLegalPersonPhone());
//        caseInfo.setAddress(restaurantInfo.getAddress());
//        caseInfo.
        BeanUtils.copyProperties(restaurantInfo,vo);
        return userDetail;
    }
}
