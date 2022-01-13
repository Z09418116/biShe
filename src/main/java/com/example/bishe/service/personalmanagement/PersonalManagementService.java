package com.example.bishe.service.personalmanagement;

import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import org.apache.ibatis.annotations.Param;


public interface PersonalManagementService {

    /**
     * 根据条件查询查询用户具体信息
     *
     * @param userQueryCondition 查询条件实体类 包含 工号 性别 手机号码
     * @return  用户具体信息实体类集合
     */
    PagedQueryResult<UserDetail> queryUserDetail(UserQueryCondition userQueryCondition);

    /**
     * 添加用户具体信息
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
     * 删除用户具体信息
     *
     * @param id 用户id
     * @return  是否成功
     */
    ReturnInfo freezeUserDetail(@Param("id") Long id);


}
