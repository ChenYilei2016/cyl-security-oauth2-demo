package com.chenyilei.security.core.social;

import com.chenyilei.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.List;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/03- 15:34
 */

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    private DataSource dataSource;

    /**
     * 不同的第三方流程
     * @see com.chenyilei.security.core.social.qq.config.QQAutoConfig
     */
    @Autowired
    private List<ConnectionFactory> connectionFactoryList;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository jdbcUsersConnectionRepository
                = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, new TextEncryptor() {
            @Override
            public String encrypt(String text) {
                return text;
            }

            @Override
            public String decrypt(String encryptedText) {
                return encryptedText;
            }
        });
        jdbcUsersConnectionRepository.setTablePrefix("tbl_");
        return jdbcUsersConnectionRepository;
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        System.err.println(environment.getProperty("com.chenyilei.social.QQ.appId"));

        connectionFactoryList.forEach(connectionFactory -> {
            connectionFactoryConfigurer.addConnectionFactory(connectionFactory);
        });
    }


    @Override
    public UserIdSource getUserIdSource() {
        return new AuthenticationNameUserIdSource();
    }

    @Bean
    public SpringSocialConfigurer dIYSpringSocialConfig(){
        DIYSpringSocialConfig socialConfig = new DIYSpringSocialConfig(securityProperties.getSocial().getFilterProcessesUrl());
        return socialConfig;
    }

}
