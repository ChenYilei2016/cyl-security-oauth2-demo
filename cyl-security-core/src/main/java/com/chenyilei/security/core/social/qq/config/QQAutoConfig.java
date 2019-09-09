package com.chenyilei.security.core.social.qq.config;

import com.chenyilei.security.core.properties.SecurityProperties;
import com.chenyilei.security.core.social.qq.connect.QQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/04- 20:13
 */
@Configuration
@ConditionalOnProperty(prefix = "com.chenyilei.social.QQ",name = "appId", matchIfMissing = false)
public class QQAutoConfig {
    @Autowired
    SecurityProperties securityProperties;

    @Bean
    public ConnectionFactory qqConnectionFactory(){
        QQConnectionFactory qqConnectionFactory = new QQConnectionFactory(
                securityProperties.getSocial().getQQ().getProviderId(),
                securityProperties.getSocial().getQQ().getAppId(),
                securityProperties.getSocial().getQQ().getAppSecret()
        );

        return qqConnectionFactory;
    }
}
