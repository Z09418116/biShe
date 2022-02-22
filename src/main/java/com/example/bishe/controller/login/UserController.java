package com.example.bishe.controller.login;


import com.example.bishe.config.base.JsonResponseVO;
import com.example.bishe.entity.login.LoginForm;
import com.example.bishe.entity.login.User;
import com.example.bishe.service.login.UserService;
import com.example.bishe.service.login.impl.UserServiceImpl;
import com.example.bishe.shiro.MyByteSource;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/add")
    public Integer toUserAdd(@RequestBody User user){
//        return "user/add";
        MyByteSource myByteSource = new MyByteSource("ainjee");
        System.out.println(myByteSource.toHex());
        //打印出 经过 md5hash 加密后的密码, "1" 代表密码， salt 代表给密码加的随机字符串盐,
        //hashIterations 代表散列的次数
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),"ainjee",6);
        System.out.println(md5Hash.toHex());
        user.setPassword(md5Hash.toHex());
        user.setSalt("ainjee");
        return userServiceImpl.add(user);
    }


    @GetMapping("/update")
    public String toUserUpdate(){
        return "user/update";
    }

    @GetMapping("/delete")
    public String toUserDelete(){
        return "user/delete";
    }

    @PostMapping("/login")
    @ResponseBody
    public JsonResponseVO login(@RequestBody LoginForm loginForm, String code,
                                HttpSession session, HttpServletResponse response, Model model) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("url",userService.login(loginForm.getJobNumber(),loginForm.getPassword(),code,session,response,model));
        map.put("token",response.getHeader("token"));
        JsonResponseVO jsonResponseVO = new JsonResponseVO();
        jsonResponseVO.setCode(200);
        jsonResponseVO.setMessage("登录成功");
        jsonResponseVO.setObj(map);
        return jsonResponseVO;
    }

}
