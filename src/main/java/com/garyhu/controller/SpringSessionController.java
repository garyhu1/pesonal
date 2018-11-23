package com.garyhu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author: garyhu
 * @since: 2018/11/23 0023
 * @decription: 测试session
 */
@Controller
public class SpringSessionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringSessionController.class);

    @GetMapping("/putsession")
    @ResponseBody
    public String putSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        LOGGER.info("session class : {}",session.getClass());
        LOGGER.info("session id : {}",session.getId());
        String name = "flyer";
        session.setAttribute("user",name);

        return "hey  "+name ;
    }
}
