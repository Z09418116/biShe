package com.example.bishe.entity.login;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
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
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 职位id
     */
    @TableId(value = "roleid", type = IdType.AUTO)
    private Integer roleid;

    /**
     * 职位
     */
    private String role;

    /**
     * 职位名称描述
     */
    private  String roleDescription;
}
