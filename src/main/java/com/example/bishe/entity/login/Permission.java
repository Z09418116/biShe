package com.example.bishe.entity.login;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "permissionid", type = IdType.AUTO)
    private Integer permissionid;

    /**
     * 接口路径
     */
    private String url;

    /**
     * 权限字符串
     */
    private String perm;

}
