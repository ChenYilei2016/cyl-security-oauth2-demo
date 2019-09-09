package com.chenyilei.security.core.validate.code;

import com.chenyilei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 10:41
 */
@Configuration
public class ValidateCodeFilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter(securityProperties, authenticationFailureHandler);
        validateCodeFilter.afterPropertiesSet();

        ValidateCodeSmsFilter validateCodeSmsFilter = new ValidateCodeSmsFilter(securityProperties,authenticationFailureHandler);
        validateCodeSmsFilter.afterPropertiesSet();

        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeSmsFilter,UsernamePasswordAuthenticationFilter.class);

    }
}
