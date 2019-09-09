package com.chenyilei.security.app.server;

import com.chenyilei.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.chenyilei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 注释
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/09/08 10:01
 */
@Configuration
public class WebMy extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(
//                "/code/**",
//                "/login/**"
//        );

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic();

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
