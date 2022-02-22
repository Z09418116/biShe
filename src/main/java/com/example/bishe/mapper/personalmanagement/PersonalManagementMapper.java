package com.example.bishe.mapper.personalmanagement;


import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.login.RolePermission;
import com.example.bishe.entity.login.User;
import com.example.bishe.entity.login.UserRole;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonalManagementMapper {

    /**
     * 查询数据库中最大的工号
     *
     * @return maxJobNumber 最大工号
     */
    Integer getMaxJobNumber();


    /**
     * 根据条件查询用户具体信息
     *
     * @return 用户具体信息实体类集合
     */
    List<UserDetail> queryUserDetail(@Param("userQueryCondition") UserQueryCondition userQueryCondition);

    /**
     * 根据条件查询用户具体信息数量
     *
     * @param userQueryCondition 查询条件实体类 包含 工号 用户姓名 手机号码
     * @return 符合查询条件的记录数量
     */
    Integer selectUserCount(@Param("userQueryCondition") UserQueryCondition userQueryCondition);

    /**
     * 创建用户
     *
     * @param user 用户信息实体类
     * @return  是否成功
     */
    Boolean createUser(@Param("user") User user);

    /**
     * 更新用户(修改密码或姓名)
     *
     * @param user 用户信息实体类
     * @return  是否成功
     */
    Boolean updateUser(@Param("user") User user,@Param("id") Integer id);

    /**
     * 分配职位
     *
     * @param userRole userRole实体类
     * @return  是否成功
     */
    Boolean assignPosition(@Param("userRole") UserRole userRole);

    /**
     * 修改职位
     *
     * @param userRole userRole实体类
     * @return  是否成功
     */
    Boolean updatePosition(@Param("userRole") UserRole userRole);

    /**
     * 分配权限
     *
     * @param rolePermissionList rolePermission实体类列表
     * @return  是否成功
     */
    Boolean assignPermissions(@Param("rolePermissionList") List<RolePermission> rolePermissionList);


    /**
     * 修改权限
     *
     * @param id 职位id
     * @return  是否成功
     */
    Boolean deletePermissions(Integer id);


    /**
     * 添加用户具体信息
     *
     * @param userDetail 用户具体信息实体类
     * @return 是否成功
     */
    Boolean addUserDetail(@Param("userDetail") UserDetail userDetail);

    /**
     * 更新用户具体信息
     *
     * @param userDetail 用户详细信息实体类
     * @return 是否成功
     */
    Boolean updateUserDetail(@Param("userDetail") UserDetail userDetail, @Param("id") Long id);

    /**
     * 判断用户是否存在
     *
     * @param id 用户id
     * @return 是否成功
     */
    Integer judgeUserExistence(Long id);

    /**
     * 判断用户详情信息是否存在
     *
     * @param id 用户id
     * @return 是否成功
     */
    Integer judgeUserDetailExistence(Long id);


    /**
     * 判断用户是否存在
     *
     * @param id 用户id
     * @return 是否成功
     */
    Integer judgeUserExistence(Integer id);

    /**
     * 判断用户是否拥有职位
     *
     * @param id 用户id
     * @return 是否成功
     */
    Integer judgePositionExistence(Integer id);

    /**
     * 判断用户是否拥有权限
     *
     * @param id 用户id
     * @return 是否成功
     */
    Integer judgePermissionsExistence(Integer id);

    /**
     * 删除用户信息
     *
     * @return 是否成功
     * @Param id 用户id
     */
    Boolean deleteUser(@Param("id") Long id);

    /**
     * 删除用户详情信息
     *
     * @return 是否成功
     * @Param id 用户id
     */
    Boolean deleteUserDetail(@Param("id") Long id);

    /**
     * 冻结用户
     *
     * @return 是否成功
     * @Param id 用户id
     */
    Boolean freezeUserDetail(@Param("id") Long id);

    /**
     * 冻结用户详细信息
     *
     * @return 是否成功
     * @Param id 用户id
     */
    Boolean freezeUser(@Param("id") Long id);

    /**
     * 根据条件查询用户具体信息数量
     *
     * @param
     * @return 符合查询条件的记录数量
     */
    Integer selectUserCountTest();

    /**
     * 双随机-随机抽取检查检查人员和餐馆
     *
     * restaurantInfo 餐馆信息
     */
    UserDetail extractUser();

}
