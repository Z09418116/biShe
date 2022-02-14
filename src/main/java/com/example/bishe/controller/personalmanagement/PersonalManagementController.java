package com.example.bishe.controller.personalmanagement;


import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import com.example.bishe.service.personalmanagement.impl.PersonalManagementServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class PersonalManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalManagementController.class);

    private PersonalManagementServiceImpl personalManagementServiceImpl;

    @Autowired
    public PersonalManagementController(PersonalManagementServiceImpl personalManagementServiceImpl) {
        this.personalManagementServiceImpl = personalManagementServiceImpl;
    }

    /**
     * 根据条件查询用户信息管理记录
     */
    @PostMapping("/query")
    @ResponseBody
    public PagedQueryResult<UserDetail> queryUserDetail(@RequestBody UserQueryCondition userQueryCondition) {
        LOGGER.info("开始根据条件查询用户详细信息-ServiceImpl");

        return personalManagementServiceImpl.queryUserDetail(userQueryCondition);

    }

    /**
     * 新增（修改）用户详细信息
     *
     * @param userDetail 参观指导管理实体类
     * @param session       session
     * @return
     */
    @RequestMapping("/userDetail/add")
    @ResponseBody
    public JsonResponseVO addVisitGuidanceEnclosure(@RequestBody UserDetail userDetail, HttpSession session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("参观指导-新增-Controller,id:{},visitDateStr:{},content:{}");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //新增或修改用户详细信息
        ReturnInfo returnInfo = personalManagementServiceImpl.addUserDetail(userDetail);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 删除用户信息
     *
     * @param id 用户id
     * @return JsonResponseVO实体类
     */
    @RequestMapping("/personalManagement/delete")
    public JsonResponseVO deleteUserDetail(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("人员管理-删除用户信息-impl,id:{}");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //删除用户信息
        ReturnInfo returnInfo = personalManagementServiceImpl.deleteUserDetail(id);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 冻结用户信息
     *
     * @param id 用户id
     * @return JsonResponseVO实体类
     */
    @RequestMapping("/personalManagement/freeze")
    public JsonResponseVO freezeUserDetail(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("人员管理-删除用户信息-impl,id:{}");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //冻结用户信息
        ReturnInfo returnInfo = personalManagementServiceImpl.freezeUserDetail(id);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 根据条件查询用户信息管理记录
     */
    @PostMapping("/queryTest")
    @ResponseBody
    public PagedQueryResult<UserDetail> queryUserTest() {
        LOGGER.info("开始根据条件查询用户详细信息-ServiceImpl");

        return personalManagementServiceImpl.queryUserTest();

    }

}
