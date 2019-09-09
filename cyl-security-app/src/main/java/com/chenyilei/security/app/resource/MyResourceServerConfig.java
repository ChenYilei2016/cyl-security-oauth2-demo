package com.chenyilei.security.app.resource;

import com.chenyilei.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.chenyilei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 注释
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/09/06 18:20
 */
@Configuration
@EnableResourceServer
@Order(Ordered.HIGHEST_PRECEDENCE+100)
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .loginPage("/authentication/require")// 决定没登录时的跳转页面 或者 自定义信息返回前端
                .loginProcessingUrl("/authentication/form")//决定验证账号登录的url
        ;
        http
                .authorizeRequests()
                //不需要权限的列表
                .antMatchers(
                        "/code/**",
                        "/login/**"
                        ,securityProperties.getBrowser().getLoginPage())
                .permitAll()
                .anyRequest().authenticated()
        ;
        http.csrf().disable();

        http.apply(smsCodeAuthenticationSecurityConfig);

    }

}
