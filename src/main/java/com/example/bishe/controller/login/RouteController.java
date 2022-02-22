package com.example.bishe.controller.login;


import com.example.bishe.service.login.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class RouteController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @GetMapping("/toUserAdd")
    @ResponseBody
    @RequiresPermissions("user:add")
    public String toUserAdd() {
        return "user/add";
    }

    @GetMapping("/toUserUpdate")
    @ResponseBody
    @RequiresPermissions("user:update")
    public String toUserUpdate() {
        return "user/update";
    }

    @GetMapping("/toUserDelete")
    @ResponseBody
    @RequiresPermissions("user:delete")
    public String toUserDelete() {
        return "user/delete";
    }

    @GetMapping("/unauthorized")
    public String toUnauthorized() {
        return "unauthorized";
    }

    @GetMapping("/logout")
    public String logout(){
        return userService.logout();
    }



}
