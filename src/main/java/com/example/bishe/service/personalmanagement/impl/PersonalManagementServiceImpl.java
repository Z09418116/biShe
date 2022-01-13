package com.example.bishe.service.personalmanagement.impl;

import com.google.common.collect.Lists;
import com.example.bishe.config.base.PagedQueryResult;
import com.example.bishe.config.base.ReturnInfo;
import com.example.bishe.entity.personalmanagement.UserDetail;
import com.example.bishe.entity.personalmanagement.UserQueryCondition;
import com.example.bishe.mapper.personalmanagement.PersonalManagementMapper;
import com.example.bishe.service.personalmanagement.PersonalManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 人员管理-查询用户详细信息
     *
     * @param userQueryCondition 用户信息查询实体类
     * @return 用户详细信息实体类列表
     */
    @Override
    public PagedQueryResult<UserDetail> queryUserDetail(UserQueryCondition userQueryCondition) {
        LOGGER.info("开始根据条件查询用户详细信息-ServiceImpl");

        final PagedQueryResult<UserDetail> pagedQueryResult = new PagedQueryResult<>();

        //查询符合条件的暂扣物品管理记录数量
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
     * 人员管理-新增（修改）用户详细信息
     *
     * @param userDetail 用户详细信息实体类
     * @return 是否成功新增或修改
     */
    @Override
    public ReturnInfo addUserDetail(UserDetail userDetail) {
        final ReturnInfo returnInfo = new ReturnInfo();

        System.out.println("LONG_ZERO"+NUM_ONE);
        System.out.println(personalManagementMapper.judgeUserExistence(userDetail.getUserId()));
        System.out.println("tauur:  "+NUM_ONE.equals(personalManagementMapper.judgeUserExistence(userDetail.getUserId())));
        if (NUM_ONE.equals(personalManagementMapper.judgeUserExistence(userDetail.getUserId()))){
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("人员管理-修改详细用户信息-impl,id:{}");
            }
            //修改用户详细信息
            Long id = userDetail.getUserId();
            returnInfo.setSuccess(personalManagementMapper.updateUserDetail(userDetail,id));

        }else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("人员管理-添加详细用户信息-impl,id:{}");
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
        returnInfo.setSuccess(personalManagementMapper.deleteUserDetail(id));
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
        returnInfo.setSuccess(personalManagementMapper.freezeUserDetail(id));
        return returnInfo;
    }



}
