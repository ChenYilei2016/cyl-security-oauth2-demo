package com.chenyilei.security.app.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.social.oauth2.GrantType;

import java.io.Serializable;

/**
 * 注释
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/09/06 15:59
 */
@Configuration
@EnableAuthorizationServer
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter implements Serializable {


    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    TokenStore tokenStore;
    @Autowired(required = false)
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("123!")
                .scopes("all")
                .authorizedGrantTypes("authorization_code")
                .redirectUris("https://www.baidu.com")
                .accessTokenValiditySeconds(722000)
        ;

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .userDetailsService(userDetailsService)
        ;
        if(jwtAccessTokenConverter != null){
            endpoints.accessTokenConverter(jwtAccessTokenConverter);
        }
    }


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

    }


    //  设置假的 加密
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



    //存放token的数据
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
////        jdbcTokenRepository.setCreateTableOnStartup(true);
//        return jdbcTokenRepository;
        return new InMemoryTokenRepositoryImpl();
    }
}
