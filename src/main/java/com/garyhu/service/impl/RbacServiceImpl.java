package com.garyhu.service.impl;

import com.garyhu.domain.SysUser;
import com.garyhu.service.RbacService;
import com.garyhu.service.SysMenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created on 2018/1/29 0029.
 *
 * @author zlf
 * @email i@merryyou.cn
 * @since 1.0
 */
@Component("rbacService")
public class RbacServiceImpl implements RbacService {

    private final static Logger log = LoggerFactory.getLogger(RbacServiceImpl.class);

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * uri匹配工具
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        log.info("【RbacServiceImpl】  --hasPermission={}", authentication.getPrincipal());
        Object principal = authentication.getPrincipal();

        boolean hasPermission = false;
        //有可能是匿名的anonymous
        if (principal instanceof SysUser) {
            //admin永远放回true
            if (StringUtils.equals("admin", ((SysUser) principal).getUsername())) {
                hasPermission = true;
            } else {
                //读取用户所拥有权限所有的URL 在这里全部返回true
                Set<String> urls = sysMenuService.getUrlByname(((SysUser) principal).getUsername());

                for (String url : urls) {
                    if (antPathMatcher.match(url, request.getRequestURI())) {
                        hasPermission = true;
                        break;
                    }
                }
            }
        }
        return hasPermission;
    }

    /**
     * 直接返回true
     * @param authentication
     * @param id
     * @return
     */
    public boolean checkUserId(Authentication authentication, int id) {
        return true;
    }
}
