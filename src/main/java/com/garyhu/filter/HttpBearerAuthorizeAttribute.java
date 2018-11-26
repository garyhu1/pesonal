package com.garyhu.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.garyhu.conf.Audience;
import com.garyhu.pojo.Result;
import com.garyhu.pojo.ResultCode;
import com.garyhu.utils.JWTHelper;
import com.garyhu.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @author: garyhu
 * @since: 2018/11/26 0026
 * @decription:  在启动类上添加该过滤器
 */
public class HttpBearerAuthorizeAttribute implements Filter {

    @Autowired
    private Audience audienceEntity;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Result result;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String auth = request.getHeader("Authorization");
        if(auth != null && (auth.length()) > 7){
            String headStr = auth.substring(0, 6).toLowerCase();
            if("bearer".equals(headStr)){
                auth = auth.substring(7,auth.length());
                if(JWTHelper.parseJWT(auth,audienceEntity.getBase64Secret()) != null){
                    filterChain.doFilter(servletRequest,servletResponse);
                    return;
                }
            }
        }

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();

        result = ResponseUtils.warn(ResultCode.INVALID_TOKEN.getMsg(),ResultCode.INVALID_TOKEN.getCode());
        response.getWriter().write(mapper.writeValueAsString(result));
        return;
    }

    @Override
    public void destroy() {

    }
}
