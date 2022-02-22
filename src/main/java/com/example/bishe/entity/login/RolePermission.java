package com.example.bishe.entity.login;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    private Integer permissionid;

    /**
     * 职位id
     */
    private Integer roleid;

    /**
     * 权限id列表
     */
    private Integer permissionidList;



}
