package com.chenyilei.security.core.social.qq.api;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

/**
 * 前提: 已经得到了认证令牌:accessToken
 * 获得用户信息
 * AbstractOAuth2ApiBinding
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/02- 13:46
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    public static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    /**
     * 获取用户的基本信息URL，可以查看 QQ互联开放平台 看到，本来有三个参数，其中一个access_token会交给AbstractOAuth2ApiBinding处理，
     * AbstractOAuth2ApiBinding会把access_token
     * 	 这个参数加进去，所以这里就没有
     */
    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;

    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    //已有accessToken
    public QQImpl(String accessToken, String appId) {
// 默认情况下，也就是AbstractOAuth2ApiBinding中只有一个参数的构造函数，使用的策略是TokenStrategy.AUTHORIZATION_HEADER
        // 即将access_token放到请求头中，而qq要求将access_token放到参数中，所以需要使用TokenStrategy.ACCESS_TOKEN_PARAMETER策略
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);
        // 发送get请求获取openId
        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
        log.error("openid:"+openId);
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(URL_GET_USERINFO, appId, openId);
        // 获取用户信息
        String result = getRestTemplate().getForObject(url, String.class);

        System.out.println(result);

        QQUserInfo userInfo = null;
        try {
            // TODO : 这里错了
            // 将返回的JSON String读到QQUserInfo对象中
            userInfo = JSONObject.parseObject(result,QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }
}
