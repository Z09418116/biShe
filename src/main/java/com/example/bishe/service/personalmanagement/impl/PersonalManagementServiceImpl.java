package com.example.bishe.service.personalmanagement.impl;

import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.config.base.RandomString;
import com.example.bishe.entity.login.RolePermission;
import com.example.bishe.entity.login.User;
import com.example.bishe.entity.login.UserRole;
import com.example.bishe.shiro.MyByteSource;
import com.google.common.collect.Lists;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import com.example.bishe.mapper.personalmanagement.PersonalManagementMapper;
import com.example.bishe.service.personalmanagement.PersonalManagementService;
import jdk.internal.org.objectweb.asm.tree.IincInsnNode;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.bishe.config.Constants.*;

@Service
public class PersonalManagementServiceImpl implements PersonalManagementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalManagementServiceImpl.class);

    private PersonalManagementMapper personalManagementMapper;

    @Autowired
    public PersonalManagementServiceImpl(PersonalManagementMapper personalManagementMapper) {
        this.personalManagementMapper = personalManagementMapper;
    }

    /**
     * 用户管理-返回工号
     *
     * @return jobNumber 工号
     */
    @Override
    public ReturnInfo getJobNumber() {
        LOGGER.info("开始根据条件查询用户详细信息-ServiceImpl");

        final ReturnInfo returnInfo = new ReturnInfo();

        //查询数据库中最大的工号
        final Integer maxJobNumber = personalManagementMapper.getMaxJobNumber();
        returnInfo.setReason(RandomString.getRandomJobNumber(maxJobNumber.toString()));
        return returnInfo;
    }

    /**
     * 人员管理-查询用户详细信息
     *
     * @param userQueryCondition 用户信息查询实体类
     * @return 用户详细信息实体类列表
     */
    @Override
    public PagedQueryResult<UserDetail> queryUserDetail(UserQueryCondition userQueryCondition) {
        LOGGER.info("开始根据条件查询用户详细信息-ServiceImpl");

        final PagedQueryResult<UserDetail> pagedQueryResult = new PagedQueryResult<>();

        //查询符合条件的数量
        final Integer count = personalManagementMapper.selectUserCount(userQueryCondition);

        //有记录则查询
        if (count != null && !NUM_ZERO.equals(count)) {
            pagedQueryResult.setTotalCount(count);
            pagedQueryResult.setResults(personalManagementMapper.queryUserDetail(userQueryCondition));
        } else {
            pagedQueryResult.setTotalCount(NUM_ZERO);
            pagedQueryResult.setResults(Lists.newArrayList());
        }
        return pagedQueryResult;
    }

    /**
     * 创建用户
     *
     * @param user 用户信息实体类
     * @return  是否成功
     */
    @Override
    public ReturnInfo createUser(User user){
        String salt = RandomString.getRandomString(6);
        LOGGER.info("s:{}",salt);
        //打印出 经过 md5hash 加密后的密码, "1" 代表密码， salt 代表给密码加的随机字符串盐,
        //hashIterations 代表散列的次数
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),salt,6);
        user.setPassword(md5Hash.toHex());
        user.setSalt(salt);
        final ReturnInfo returnInfo = new ReturnInfo();
        if (NUM_ONE.equals(personalManagementMapper.judgeUserExistence(user.getId()))){
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("人员管理-修改详细用户信息-impl,id:{}");
            }
            //修改用户详细信息
            Integer id = user.getId();
            returnInfo.setSuccess(personalManagementMapper.updateUser(user,id));
        }else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("人员管理-添加详细用户信息-impl,id:{}");
            }
            //添加用户详细信息
            Integer id = user.getId();
            returnInfo.setSuccess(personalManagementMapper.createUser(user));
        }
        return returnInfo;
    }

    /**
     * 职位分配
     *
     * @param userRole userRole实体类
     * @return  是否成功
     */
    @Override
    public ReturnInfo assignPosition(UserRole userRole){
        final ReturnInfo returnInfo = new ReturnInfo();
        if (NUM_ONE.equals(personalManagementMapper.judgePositionExistence(userRole.getUserid()))){
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("人员管理-权限修改-impl,id:{}",userRole.getUserid());
            }
            //修改权限
            returnInfo.setSuccess(personalManagementMapper.updatePosition(userRole));
        }else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("人员管理-权限分配-impl,id:{}",userRole.getUserid());
            }
            //分配权限
            returnInfo.setSuccess(personalManagementMapper.assignPosition(userRole));
        }
        return returnInfo;
    }

    /**
     * 权限分配
     *
     * @param rolePermissionList rolePermission实体类列表
     * @return  是否成功
     */
    @Override
    public ReturnInfo assignPermissions(List<RolePermission> rolePermissionList){
        final ReturnInfo returnInfo = new ReturnInfo();
        if (rolePermissionList.size()<= 0){
            returnInfo.setSuccess(false);
            returnInfo.setReason("列表为空");
            return returnInfo;
        }else {
            LOGGER.info("e:{}",personalManagementMapper.judgePermissionsExistence(rolePermissionList.get(0).getRoleid()));
            if (!NUM_ZERO.equals(personalManagementMapper.judgePermissionsExistence(rolePermissionList.get(0).getRoleid()))){
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("人员管理-权限修改-impl");
                }
                //删除权限
                returnInfo.setSuccess(personalManagementMapper.deletePermissions(rolePermissionList.get(0).getRoleid()));
                //分配权限
                returnInfo.setSuccess(personalManagementMapper.assignPermissions(rolePermissionList));
            }else {
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("人员管理-权限分配-impl");
                }
                //分配权限
                returnInfo.setSuccess(personalManagementMapper.assignPermissions(rolePermissionList));
            }
            return returnInfo;
        }

    }

    /**
     * 人员管理-新增（修改）用户详细信息
     *
     * @param userDetail 用户详细信息实体类
     * @return 是否成功新增或修改
     */
    @Override
    public ReturnInfo addUserDetail(UserDetail userDetail) {
        final ReturnInfo returnInfo = new ReturnInfo();
        if (NUM_ONE.equals(personalManagementMapper.judgeUserDetailExistence(userDetail.getUserId()))){
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("人员管理-修改详细用户信息-impl,id:{}");
            }
            //修改用户详细信息
            Long id = userDetail.getUserId();
            returnInfo.setSuccess(personalManagementMapper.updateUserDetail(userDetail,id));
        }else {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("人员管理-添加详细用户信息-impl,id:{}");
            }
            //添加用户详细信息
            returnInfo.setSuccess(personalManagementMapper.addUserDetail(userDetail));
        }
        return returnInfo;
    }

    /**
     * 人员管理-删除用户信息
     *
     * @param id 用户id
     * @return 是否成功删除
     */
    @Override
    public ReturnInfo deleteUserDetail(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("人员管理-删除用户信息-impl,id:{}");
        }
        final ReturnInfo returnInfo = new ReturnInfo();
        //删除用户信息
        if (personalManagementMapper.deleteUser(id).equals(true)) {
            //删除详细信息
            returnInfo.setSuccess(personalManagementMapper.deleteUserDetail(id));
        }
        return returnInfo;
    }

    /**
     * 人员管理-冻结用户信息
     *
     * @param id 用户id
     * @return 是否成功冻结
     */
    @Override
    public ReturnInfo freezeUserDetail(Long id) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("人员管理-删除用户信息-impl,id:{}");
        }
        final ReturnInfo returnInfo = new ReturnInfo();
        //删除用户信息
        if (personalManagementMapper.freezeUser(id)){
            //删除用户详细信息
            returnInfo.setSuccess(personalManagementMapper.freezeUserDetail(id));
        }

        return returnInfo;
    }


    /**
     * 双随机-随机抽取检查检查人员和餐馆
     *
     * restaurantInfo 餐馆信息
     */
    public UserDetail extractUser(){
        return personalManagementMapper.extractUser();
    }

}
