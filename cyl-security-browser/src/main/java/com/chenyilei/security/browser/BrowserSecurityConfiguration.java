package com.chenyilei.security.browser;


import com.chenyilei.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.chenyilei.security.core.properties.SecurityProperties;
import com.chenyilei.security.core.validate.code.ValidateCodeFilterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/28- 19:54
 */
@Configuration
public class BrowserSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    SessionStrategy sessionStrategy;

    @Autowired
    PersistentTokenRepository persistentTokenRepository;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    ValidateCodeFilterConfig validateCodeFilterConfig;
    /**
     * 进行social的配置
     */
    @Autowired
    SpringSocialConfigurer dIYSpringSocialConfig;

    @Override
    public void configure(WebSecurity web) throws Exception {

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .rememberMe()
            .tokenRepository(persistentTokenRepository)
            .tokenValiditySeconds(securityProperties.getBrowser().getTokenExpire())
            .userDetailsService(userDetailsService)
        .and()
        .formLogin()
//              .httpBasic()
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailureHandler)
            .loginPage("/authentication/require")// 决定没登录时的跳转页面 或者 自定义信息返回前端
            .loginProcessingUrl("/authentication/form")//决定验证账号登录的url
            
        .and()
            .authorizeRequests()
            //不需要权限的列表
            .antMatchers("/authentication/require"
                    ,"/code/**"
                    ,"/login/**"
                    ,securityProperties.getBrowser().getLoginPage())
            .permitAll()

            .anyRequest().authenticated()
        .and()
            .csrf().disable()

        .apply(smsCodeAuthenticationSecurityConfig)
        .and().apply(validateCodeFilterConfig);

        //对于Social的配置
        http.apply(dIYSpringSocialConfig);


    }

    //存放token的数据
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }



//  设置假的 加密
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence+"!";
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return encode(charSequence).equals(s);
            }
        };
    }

    @Bean
    public SessionStrategy sessionStrategy(){
        return new HttpSessionSessionStrategy();
    }

}
