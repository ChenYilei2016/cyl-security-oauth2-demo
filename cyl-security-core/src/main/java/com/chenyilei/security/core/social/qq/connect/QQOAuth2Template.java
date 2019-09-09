package com.chenyilei.security.core.social.qq.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/06- 22:34
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        /**
         * 发请求的时候携带参数
         */
        setUseParametersForClientAuthentication(true);
    }

    /**
     * 处理QQ特殊的返回成功信息,不是json的
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {

        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        log.info("获取accessToke的响应："+responseStr);
        /**
         * 截取认证成功后的信息
         */
        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
        /**
         *
         * accessToken
         */
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        /**
         * expiresIn
         */
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        /**
         * 刷新令牌
         */
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }


    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        /**
         * 在原有的实现之上添加了一个能够处理text/html信息的converter
         */
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
