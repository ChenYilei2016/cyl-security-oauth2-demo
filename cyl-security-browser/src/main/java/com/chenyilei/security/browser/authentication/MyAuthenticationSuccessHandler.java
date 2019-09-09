package com.chenyilei.security.browser.authentication;

import com.chenyilei.security.core.enumType.LoginTypeEnum;
import com.chenyilei.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
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
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        log.error("登录成功!");

        if(LoginTypeEnum.JSON.equals(securityProperties.getBrowser().getLoginType())){
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(200);
            httpServletResponse.getWriter().print(objectMapper.writeValueAsString(authentication));
        }else{
            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
        }


    }
}
