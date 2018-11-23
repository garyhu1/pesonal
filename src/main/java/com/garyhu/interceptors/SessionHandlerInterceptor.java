package com.garyhu.interceptors;

import com.garyhu.entity.Student;
import com.garyhu.entity.User;
import com.garyhu.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionHandlerInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String url = request.getServletPath(); // 获取相对路径
        LOGGER.info("request url : {}",url);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user != null){
        }else {
            response.sendRedirect("/user/login.html");
            return false;
        }
        response401(response);
        return false;
    }

    private void response401(HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        try{
            response.getWriter().print(ResponseUtils.warn("用户未登录!",401));
        }catch(IOException e){}
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        // Controller方法执行完成调用该方法
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        // 页面渲染完执行该方法，通常用来清除某些资源，类似java的finally
    }
}
