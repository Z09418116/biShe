package com.example.bishe.controller.login;

import com.example.bishe.config.Constants;
import com.example.bishe.controller.caseinfo.CaseInfoController;
import com.example.bishe.entity.login.Menu;
import com.example.bishe.service.caseinfo.impl.CaseInfoServiceImpl;
import com.example.bishe.service.login.MenuService;
import com.example.bishe.service.login.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/system/cfg")
public class MenuController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }



    @ApiOperation(value = "通过用户id查询菜单列表")
    @RequestMapping("menu")
    public List<Menu> getMenusByAdminId(Integer id, HttpSession session){
         String token = session.getId();
        LOGGER.info("level1 获得的token为：{}", token);
//        Integer userId = TokenUtil.getUserIdByToken(token);
//        LOGGER.info("userId={}", userId);


        return menuService.getMenusByAdminId(id);
    }
}
