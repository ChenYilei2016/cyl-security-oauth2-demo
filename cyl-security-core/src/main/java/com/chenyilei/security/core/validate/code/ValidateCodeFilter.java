package com.chenyilei.security.core.validate.code;

import cn.hutool.core.util.ObjectUtil;
import com.chenyilei.security.core.properties.SecurityProperties;
import com.chenyilei.security.core.support.SecurityConstants;
import com.chenyilei.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 对验证码的校验拦截器
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/30- 21:25
 */
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
    private final SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final SecurityProperties securityProperties;
    private final Set<String> filterUrls  = new HashSet<>();
    private final AntPathMatcher antPathMatcher =new AntPathMatcher();

    public ValidateCodeFilter(SecurityProperties securityProperties, AuthenticationFailureHandler authenticationFailureHandler) {
        this.securityProperties = securityProperties;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        boolean needFilter = false;

        for(String pattern : filterUrls){
            if(antPathMatcher.match(pattern,requestURI)){
                needFilter = true;
                break;
            }
        }

        if( needFilter
//         && StringUtils.equalsIgnoreCase(request.getMethod(),"POST")
        ) {
            //校验验证码 和 session中的值
            try {
                validate(request);
            }catch (AuthenticationException e){
                authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return ;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validate(HttpServletRequest request) throws ValidateCodeException, ServletRequestBindingException {

        //用户传入验证码
        String checkCode = ServletRequestUtils.getStringParameter(request, "imageCode");
        //Session
        ImageCode sessionImageCode = (ImageCode)sessionStrategy.getAttribute(new ServletWebRequest(request), ValidateCodeController.CODE_SESSION_KEY+"image");
        if(StringUtils.isBlank(checkCode)){
            throw new ValidateCodeException("验证码不能为空!");
        }
        if(ObjectUtil.isNull(sessionImageCode)){
            throw new ValidateCodeException("Session中没有验证码!");
        }

        if(sessionImageCode.getExpireTime().isBefore(LocalDateTime.now())){
            throw new ValidateCodeException("验证码已经过期!");
        }
        if(!StringUtils.equalsIgnoreCase(sessionImageCode.getCode(),checkCode)){
            throw new ValidateCodeException("验证码输入错误!");
        }

        sessionStrategy.removeAttribute(new ServletWebRequest(request),ValidateCodeController.CODE_SESSION_KEY+"image");
    }

    @Override
    public void afterPropertiesSet() throws ServletException {
        String filterUrl = securityProperties.getValiadate().getImage().getFilterUrl();
        String[] urls = StringUtils.split(filterUrl, ",");
        for (int i = 0; i < urls.length; i++) {
            filterUrls.add(urls[i]);
        }
        filterUrls.add("/authentication/form");
    }
}
