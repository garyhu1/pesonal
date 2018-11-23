package com.garyhu.controller;

import com.garyhu.entity.User;
import com.garyhu.pojo.*;
import com.garyhu.service.UserService;
import com.garyhu.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : garyhu
 * @Since : 2018/10/27
 * @Decription : 用户接口
 */
@Controller
@RequestMapping("/user")
public class UserController  {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    @ResponseBody
    public Result addUser(@RequestBody User user){
        try {
            User user1 = userService.addUser(user);
            return ResponseUtils.success(user1);
        }catch (Exception e){
            return ResponseUtils.warn("添加用户失败",3012);
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterBean bean){
        String username = bean.getUsername();
        ResultCode resultCode;
        if(username == null || "".equals(username)){
            resultCode = ResultCode.USERNAME_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }
        String password = bean.getPassword();
        if(password  == null || "".equals(password)){
            resultCode  = ResultCode.PASSWORD_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }

        String sureword = bean.getSureword();
        if(sureword  == null || "".equals(sureword)){
            resultCode  = ResultCode.SUREWORD_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }

        if(!password.equals(sureword)){
            resultCode  = ResultCode.PASSWORD_NOMATCH;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }

        return ResponseUtils.success(bean);
    }

    @PostMapping("/login")
    public @ResponseBody Result login(@RequestBody LoginBean bean){
        String username = bean.getUsername();
        ResultCode resultCode;
        if(username == null || "".equals(username)){
            resultCode = ResultCode.USERNAME_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }
        String password = bean.getPassword();
        if(password  == null || "".equals(password)){
            resultCode  = ResultCode.PASSWORD_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }

        AccessTokenBean accessTokenBean = new AccessTokenBean();
        return ResponseUtils.success(accessTokenBean);
    }

    @GetMapping("/signin")
    @ResponseBody
    public Result signIn(HttpServletRequest request, Model model){
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        // 校验
        Map<String,String> map = new HashMap<>();
        map.put("username",name);
        map.put("password",password);

        User user = null;
        try{
            user = userService.login(map);
        }catch (Exception e){
            LOGGER.error("user signin is error {}",e);
        }

        if(user != null){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return ResponseUtils.success(user);
        }else {
            return ResponseUtils.warn("登录失败，请重新登录",3011);
        }
    }

    @GetMapping("/getsession")
    @ResponseBody
    public String getSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        LOGGER.info("session class : {}",session.getClass());
        LOGGER.info("session id : {}",session.getId());
        User user = (User) session.getAttribute("user");

        if(user != null){
            String name = user.getName();
            String password = user.getPassword();

            return "用户名 ： "+name+"， 密码 ： "+password;
        }

        return "没有登录成功！";
    }

    @GetMapping("/login.html")
    public String loginPage(){
        return "user/login";
    }
}
