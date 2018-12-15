package com.garyhu.controller;

import com.garyhu.entity.User;
import com.garyhu.pojo.*;
import com.garyhu.service.UserService;
import com.garyhu.utils.ResponseUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : garyhu
 * @Since : 2018/10/27
 * @Decription : 用户接口
 */
@Controller
@RequestMapping("/api/user")
public class UserOldController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserOldController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    @ResponseBody
    @ApiOperation(value = "添加用户",notes = "把用户添加到数据库中")
    @ApiImplicitParam(name = "user",value = "需要添加的用户实例user",required = true, dataType = "User")
    public Result addUser(@RequestBody User user){
        try {
            User user1 = userService.addUser(user);
            return ResponseUtils.success(user1);
        }catch (Exception e){
            return ResponseUtils.warn("添加用户失败",3012);
        }
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册用户",notes = "")
    @ApiImplicitParam(name = "registerBean" ,value = "注册的用户实例",required = true,dataType = "RegisterBean")
    public Result register(@RequestBody RegisterBean registerBean){
        String username = registerBean.getUsername();
        ResultCode resultCode;
        if(username == null || "".equals(username)){
            resultCode = ResultCode.USERNAME_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }
        String password = registerBean.getPassword();
        if(password  == null || "".equals(password)){
            resultCode  = ResultCode.PASSWORD_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }

        String sureword = registerBean.getSureword();
        if(sureword  == null || "".equals(sureword)){
            resultCode  = ResultCode.SUREWORD_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }

        if(!password.equals(sureword)){
            resultCode  = ResultCode.PASSWORD_NOMATCH;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }

        return ResponseUtils.success(registerBean);
    }

    @PostMapping("/login")
    @ApiOperation(value = "用户登录",notes = "暂时废弃")
    @ApiImplicitParam(name = "loginBean",value = "用户登录实例",required = true,dataType = "LoginBean")
    public @ResponseBody Result login(@RequestBody LoginBean loginBean){
        String username = loginBean.getUsername();
        ResultCode resultCode;
        if(username == null || "".equals(username)){
            resultCode = ResultCode.USERNAME_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }
        String password = loginBean.getPassword();
        if(password  == null || "".equals(password)){
            resultCode  = ResultCode.PASSWORD_NULL;

            return ResponseUtils.warn(resultCode.getMsg(),resultCode.getCode());
        }

        AccessTokenBean accessTokenBean = new AccessTokenBean();
        return ResponseUtils.success(accessTokenBean);
    }

    @GetMapping("/signin")
    @ApiOperation(value = "用户登录",notes = "登录成功后会把登录信息保存在redis中，通过redis实现session共享")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "request",value = "请求",dataType = "HttpServletRequest"),
            @ApiImplicitParam(name = "model",value = "用来存放数据，在view层展示",dataType = "Model")
    })
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
    @ApiOperation(value = "获取session",notes = "")
    @ApiImplicitParam(name = "request",value = "请求",dataType = "HttpServletRequest")
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

    @GetMapping("/getUsers")
    @ApiOperation(value = "根据role获取所有用户")
    @ApiImplicitParam(name = "role",value = "用户角色",dataType = "String")
    @ResponseBody
    public Result getUsersByRole(@RequestParam String role){
        List<User> users = userService.getUsersByRole(role);

        return ResponseUtils.success(users);
    }


    @GetMapping("/login.html")
    @ApiOperation(value = "跳转到登录界面",notes = "")
    public String loginPage(){
        return "user/login";
    }
}
