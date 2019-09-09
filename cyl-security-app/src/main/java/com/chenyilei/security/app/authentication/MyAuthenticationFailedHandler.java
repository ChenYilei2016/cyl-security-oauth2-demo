package com.chenyilei.security.app.authentication;

import com.chenyilei.security.core.enumType.LoginTypeEnum;
import com.chenyilei.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/30- 15:17
 */
@Component
@Slf4j
public class MyAuthenticationFailedHandler implements AuthenticationFailureHandler {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    SecurityProperties securityProperties;

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        AuthenticationException e) throws IOException, ServletException {

        if(LoginTypeEnum.JSON.equals(securityProperties.getBrowser().getLoginType())){
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(500);
            httpServletResponse.getWriter().print(objectMapper.writeValueAsString(e.getMessage()));
        }else{
            redirectStrategy.sendRedirect(httpServletRequest,httpServletResponse,securityProperties.getBrowser().getLoginPage()+"?msg="+e.getMessage());
        }
    }
}
