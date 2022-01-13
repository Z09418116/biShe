package com.example.bishe.mapper.personalmanagement;


import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonalManagementMapper {

    /**
     * 根据条件查询查询用户具体信息
     *
     * @return 用户具体信息实体类集合
     */
    List<UserDetail> queryUserDetail(@Param("userQueryCondition") UserQueryCondition userQueryCondition);

    /**
     * 根据条件查询查询用户具体信息数量
     *
     * @param userQueryCondition 查询条件实体类 包含 工号 性别 手机号码
     * @return 符合查询条件的记录数量
     */
    Integer selectUserCount(@Param("userQueryCondition") UserQueryCondition userQueryCondition);

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
     * 删除用户
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

}
