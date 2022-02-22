package com.example.bishe.controller.personalmanagement;


import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.login.RolePermission;
import com.example.bishe.entity.login.User;
import com.example.bishe.entity.login.UserRole;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import com.example.bishe.service.personalmanagement.impl.PersonalManagementServiceImpl;
import com.example.bishe.shiro.MyByteSource;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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
     * 用户管理-返回工号
     *
     * @return jobNumber 工号
     */
    @RequestMapping("/getJobNumber")
    @ResponseBody
    public JsonResponseVO getJobNumber() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("用户管理-返回工号-Controller");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //获取工号
        ReturnInfo returnInfo = personalManagementServiceImpl.getJobNumber();
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 用户管理-根据条件查询用户信息管理记录
     */
    @PostMapping("/query")
    @ResponseBody
    public JsonResponseVO queryUserDetail(@RequestBody UserQueryCondition userQueryCondition) {
        LOGGER.info("开始根据条件查询用户详细信息-ServiceImpl");

        JsonResponseVO jsonResponseVO = new JsonResponseVO();
        jsonResponseVO.setObj(personalManagementServiceImpl.queryUserDetail(userQueryCondition));
        jsonResponseVO.setCode(200);
        jsonResponseVO.setMessage("成功");
        return jsonResponseVO;

    }

    /**
     * 用户管理-创建(修改)新用户 （修改用户名密码）
     *
     * @param user 用户信息实体类
     * @param session       session
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public JsonResponseVO createUser(@RequestBody User user, HttpSession session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("用户管理-创建新用户-Controller,user:{}",user);
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //新增或修改用户详细信息
        ReturnInfo returnInfo = personalManagementServiceImpl.createUser(user);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }


    /**
     * 权限管理-分配（修改）职位
     *
     * @param userRole userRole实体类
     * @param session       session
     * @return
     */
    @RequestMapping("/assignPosition")
    @ResponseBody
    public JsonResponseVO assignPosition(@RequestBody UserRole userRole, HttpSession session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("权限管理-分配（修改）职位-Controller,id:{},visitDateStr:{},content:{}");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //新增或修改权限
        ReturnInfo returnInfo = personalManagementServiceImpl.assignPosition(userRole);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 权限管理-分配（修改）权限
     *
     * @param rolePermissionList rolePermission实体类列表
     * @param session       session
     * @return
     */
    @RequestMapping("/assignPermissions")
    @ResponseBody
    public JsonResponseVO assignPermissions(@RequestBody List<RolePermission> rolePermissionList, HttpSession session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("权限管理-分配（修改）职位-Controller,id:{},visitDateStr:{},content:{}");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        //新增或修改权限
        ReturnInfo returnInfo = personalManagementServiceImpl.assignPermissions(rolePermissionList);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 用户管理-新增（修改）用户详细信息
     *
     * @param userDetail 用户详细信息实体类
     * @param session       session
     * @return
     */
    @RequestMapping("/userDetail/add")
    @ResponseBody
    public JsonResponseVO addVisitGuidanceEnclosure(@RequestBody UserDetail userDetail, HttpSession session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("用户管理-新增（修改）用户详细信息");
        }
        final JsonResponseVO vo = new JsonResponseVO();
        userDetail.setUserId(userDetail.getId());
        //新增或修改用户详细信息
        ReturnInfo returnInfo = personalManagementServiceImpl.addUserDetail(userDetail);
        BeanUtils.copyProperties(returnInfo,vo);
        return vo;
    }

    /**
     * 用户管理-删除用户信息
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
     * 用户管理-冻结用户信息
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


}
