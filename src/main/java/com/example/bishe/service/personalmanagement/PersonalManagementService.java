package com.example.bishe.service.personalmanagement;

import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.RandomString;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.login.RolePermission;
import com.example.bishe.entity.login.User;
import com.example.bishe.entity.login.UserRole;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PersonalManagementService {

    /**
     * 用户管理-返回工号
     *
     * @return jobNumber 工号
     */
    ReturnInfo getJobNumber();

    /**
     * 根据条件查询查询用户具体信息
     *
     * @param userQueryCondition 查询条件实体类 包含 工号 性别 手机号码
     * @return  用户具体信息实体类集合
     */
    PagedQueryResult<UserDetail> queryUserDetail(UserQueryCondition userQueryCondition);

    /**
     * 创建用户
     *
     * @param user 用户信息实体类
     * @return  是否成功
     */
    ReturnInfo createUser(User user);

    /**
     * 分配权限
     *
     * @param userRole userRole实体类
     * @return  是否成功
     */
    ReturnInfo assignPosition(UserRole userRole);

    /**
     * 分配权限
     *
     * @param rolePermissionList rolePermission实体类
     * @return  是否成功
     */
    ReturnInfo assignPermissions(List<RolePermission> rolePermissionList);

    /**
     * 添加(修改)用户具体信息
     *
     * @param userDetail 用户详细信息实体类
     * @return  是否成功
     */
    ReturnInfo addUserDetail(UserDetail userDetail);

    /**
     * 删除用户具体信息
     *
     * @param id 用户id
     * @return  是否成功
     */
    ReturnInfo deleteUserDetail(@Param("id") Long id);

    /**
     * 冻结用户具体信息
     *
     * @param id 用户id
     * @return  是否成功
     */
    ReturnInfo freezeUserDetail(@Param("id") Long id);


    /**
     * 双随机-随机抽取检查检查人员和餐馆
     *
     * restaurantInfo 餐馆信息
     */
    UserDetail extractUser();

}
