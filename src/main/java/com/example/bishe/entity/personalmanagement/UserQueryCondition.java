package com.example.bishe.entity.personalmanagement;

import com.example.bishe.config.base.AbstractPaginationQueryCondition;
import lombok.Data;

@Data
public class UserQueryCondition extends AbstractPaginationQueryCondition {
    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号码
     */
    private String mobilePhone;
}
