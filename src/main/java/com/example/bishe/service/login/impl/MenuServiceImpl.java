package com.example.bishe.service.login.impl;

import com.example.bishe.config.Constants;
import com.example.bishe.entity.login.Menu;
import com.example.bishe.mapper.caseinfo.CaseInfoMapper;
import com.example.bishe.mapper.login.MenuMapper;
import com.example.bishe.service.caseinfo.CaseInfoService;
import com.example.bishe.service.caseinfo.impl.CaseInfoServiceImpl;
import com.example.bishe.service.login.MenuService;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

    private MenuMapper menuMapper;
    private RedisTemplate redisTemplate;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper,RedisTemplate<String,Object> redisTemplate) {
        this.menuMapper = menuMapper;
        this.redisTemplate = redisTemplate;

    }

    /**
     * 根据用户名获取菜单列表
     *
     * @return List<Menu>
     */
    @Override
    public List<Menu> getMenusByAdminId(Integer id) {
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();
        //从redis获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_"+id);
        //如果为空，从数据库获取并放入redis中
        if(CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByAdminId(id);
            //将数据放入redis中
            valueOperations.set("menu_"+id,menus);
        }
        return menus;
    }

    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }
}
