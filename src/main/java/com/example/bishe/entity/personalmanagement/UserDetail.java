package com.example.bishe.entity.personalmanagement;

import lombok.Data;

import java.util.Date;

/**
 * ** 用户具体信息实体类
 *
 * @author Matt.Lu
 * @date 2022/1/8
 */
@Data
public class UserDetail {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 生日
     */
    private String dateOfBirth;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证号码
     */
    private String identityCard;

    /**
     * 微信
     */
    private String wechat;

    /**
     * 户籍所在地
     */
    private String registeredResidence;

    /**
     * 手机号码
     */
    private String mobilePhone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 家庭居住地
     */
    private String homeAddress;

    /**
     * 紧急联系人
     */
    private String emergencyContactPerson;

    /**
     * 紧急联系人号码
     */
    private Integer emergencyContactPhone;

    /**
     * 入职日期
     */
    private String entryDate;

    /**
     * 离职日期
     */
    private String dateOfResignation;

    /**
     * 是否冻结该用户   1-正常使用   0-冻结
     */
    private String freezedFlag;

    /**
     * 是否删除该用户   1-正常使用   0-删除
     */
    private String deletedFlag;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateName;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关联用户id
     */
    private Long userId;
}
