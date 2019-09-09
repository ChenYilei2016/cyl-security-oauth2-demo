package com.chenyilei.security.core.social.qq.connect;

import com.chenyilei.security.core.social.qq.api.QQ;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/03- 15:17
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * Create a {@link OAuth2ConnectionFactory}.
     */
    public QQConnectionFactory(String providerId,String appId,String secrect) {
        /**
         * @param providerId      the provider id e.g. "facebook"
         * @param serviceProvider the ServiceProvider model for conducting the authorization flow and obtaining a native service API instance.
         * @param apiAdapter      the ApiAdapter for mapping the provider-specific service API model to the uniform {@link Connection} interface.
         */
        super(providerId, new QQServiceProvider(appId,secrect), new QQAdapter());
    }
}
