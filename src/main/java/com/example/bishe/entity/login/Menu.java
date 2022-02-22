package com.example.bishe.entity.login;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;

@Data
public class Menu {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * url
     */
    private String url;

    /**
     * path
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 图标
     */
    private String iconCls;

    /**
     * 是否保持激活
     */
    private String keepActive;

    /**
     * 是否要求权限
     */
    private String requireAuth;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 是否启用
     */
    private String enabled;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<Role> roles;
}
