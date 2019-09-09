package com.chenyilei.security.core.social.qq.connect;

import com.chenyilei.security.core.social.qq.api.QQ;
import com.chenyilei.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * 使用authorization_code获取access_token
 * http://wiki.connect.qq.com/%E4%BD%BF%E7%94%A8authorization_code%E8%8E%B7%E5%8F%96access_token
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/02- 14:40
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private String appId ;
    /**
     * 流程中的第一步：导向认证服务器的url
     */
    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    /**
     * 第四步：申请令牌的url
     */
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";

    public QQServiceProvider(String appId,String key) {
        /**
         * Constructs an OAuth2Template for a given set of client credentials.
         * Assumes that the authorization URL is the same as the authentication URL.
         * @param clientId the client ID
         * @param clientSecret the client secret
         * @param authorizeUrl the base URL to redirect to when doing authorization code or implicit grant authorization
         * @param accessTokenUrl the URL at which an authorization code, refresh token, or user credentials may be exchanged for an access token.
         */
        super(new QQOAuth2Template(appId,key,URL_AUTHORIZE,URL_ACCESS_TOKEN));
        this.appId = appId;
    }


    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken,appId);
    }
}
