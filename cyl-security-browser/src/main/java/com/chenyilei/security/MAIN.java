package com.chenyilei.security;

import com.alibaba.fastjson.JSONObject;
import com.chenyilei.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.chenyilei.security.core.authentication.mobile.SmsCodeAuthenticationProvider;
import com.chenyilei.security.core.security.userservice.UserDetailServiceImpl;
import com.chenyilei.security.core.social.DIYSpringSocialConfig;
import com.chenyilei.security.core.social.SocialConfig;
import com.chenyilei.security.core.social.qq.api.QQUserInfo;
import com.chenyilei.security.core.social.qq.config.QQAutoConfig;
import com.chenyilei.security.core.social.qq.connect.QQOAuth2Template;
import com.chenyilei.security.core.validate.code.ValidateCodeFilter;
import com.chenyilei.security.core.validate.code.ValidateCodeSmsFilter;
import com.chenyilei.security.core.validate.code.sms.AbstractSmsSenderInterface;
import com.chenyilei.security.core.validate.code.sms.FakerSmsSenderInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

/**------
 * Spring Security 核心是过滤器链
 * {@link WebSecurityConfigurerAdapter}
 *
 * 前置的一些认证方式拦截:
 *  0:最优先过滤器 {@link SecurityContextPersistenceFilter} 进:[检查session 是否有信息,有就放入context] 出:[检查context中有无信息,放入session]
 *  1: {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
 *     {@link org.springframework.security.web.authentication.www.BasicAuthenticationFilter}
 *
 *  对于1 => {@link org.springframework.security.authentication.AuthenticationManager}
 *          {@link org.springframework.security.authentication.AuthenticationProvider}
 *
*   2: {@link org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter}
 *
 *  根据前置认证情况,捕获后面抛出的异常后作出响应{@link org.springframework.security.web.access.ExceptionTranslationFilter}
 *  最终守门 {@link org.springframework.security.web.access.intercept.FilterSecurityInterceptor}
 *
 * --------自定义 认证逻辑 --------{@link UserDetailServiceImpl}
 ** 1.用户信息的获取 {@link org.springframework.security.core.userdetails.UserDetailsService} 根据用户名,查找具体信息
 ** 2.用户的数据校验[冻结?过期?] {@link org.springframework.security.core.userdetails.UserDetails} 的实现接口
 ** 3.加密解密{@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder}
 *
 * --------自定义 认证流程[登录页面 等等] --------{@link com.chenyilei.security.browser.BrowserSecurityController}
 * 请求没登录,进入了返回401控制器,可以由{@link HttpSessionRequestCache} 获取之前拦截的URL 进行跳转
 * 登录成功 或失败 时候 {@link AuthenticationSuccessHandler} {@link AuthenticationFailureHandler}
 * 增加一个自定义校验 图片验证码的过滤器,进行校验!!! {@link ValidateCodeFilter} {@link org.springframework.util.AntPathMatcher} Spring的判断路径匹配
 * '记住我'功能, 保存token,在没有凭证的时候,将数据库中的信息导入至context中,{@link RememberMeServices} {@link PersistentTokenRepository} {@link RememberMeAuthenticationFilter}
 * TODO: 验证码获取type 重构
 *
 * 短信登录 {@link ValidateCodeSmsFilter} 分离的一个过滤器
 * {@link com.chenyilei.security.core.authentication.mobile.SmsCodeAuthenticationToken}自定义信息封装token
 * 登录时拦截,转发provider进行认证  {@link SmsCodeAuthenticationFilter}{@link SmsCodeAuthenticationProvider}
 * TODO: 字符串去重复, 配置去重复
 *
 * ---------- spring-social 第三方登录
 * {@link SocialAuthenticationFilter} social的过滤器
 *
 * 服务提供商{@link org.springframework.social.ServiceProvider}{@link AbstractOAuth2ServiceProvider}
 *      完成请求流程{@link org.springframework.social.oauth2.OAuth2Template}
 *      读取用户信息{@link org.springframework.social.oauth2.AbstractOAuth2ApiBinding}
 * {@link OAuth2Connection} 封装前6步获取到的用户信息 / {@link org.springframework.social.connect.ApiAdapter}转换成social标准的pojo
 * {@link org.springframework.social.connect.UsersConnectionRepository} 数据库操作[例如 openid对应你的用户关系?]
 *
 * 我的实现:
 *      1. {@link com.chenyilei.security.core.social.qq.api.QQImpl}
*       2. {@link com.chenyilei.security.core.social.qq.connect.QQAdapter}
 *      3. {@link com.chenyilei.security.core.social.qq.connect.QQConnectionFactory}
 *      4. 配置:{@link SocialConfig}
 *          4.1 数据库转换{@link JdbcUsersConnectionRepository}
 *          4.2 流程连接工厂的增加 {@link SocialConfig#addConnectionFactories} / {@link QQAutoConfig} / {@link DIYSpringSocialConfig}
 *          error: {http://www.chenyilei.test:8080/signin} 被拦截? restTemplate请求的不对 ! {@link QQOAuth2Template}
 *          error: {跳到 signup? 因为我们的应用没有注册用户} 需要能找到用户{@link JdbcUsersConnectionRepository}
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/28- 20:45
 */
public class MAIN {
    public static void main(String[] args) throws IOException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher.match("/**","/gad"));
        System.out.println(Arrays.asList(StringUtils.split(",,/user,/fdaf",",")));

        AbstractSmsSenderInterface abstractSmsSenderInterface = new FakerSmsSenderInterface();
        System.out.println(abstractSmsSenderInterface.getClass().getSimpleName());

        Pattern pattern = Pattern.compile("\\w{0,10}");
        System.out.println(pattern.matcher("f51").find());
        String temp =  "{\n" +
                "    \"ret\": 0,\n" +
                "    \"msg\": \"\",\n" +
                "    \"is_lost\":0,\n" +
                "    \"nickname\": \"靑い石\",\n" +
                "    \"gender\": \"男\",\n" +
                "    \"province\": \"香港\",\n" +
                "    \"city\": \"荃湾区\",\n" +
                "    \"year\": \"1998\",\n" +
                "    \"constellation\": \"\",\n" +
                "    \"figureurl\": \"http:\\/\\/qzapp.qlogo.cn\\/qzapp\\/101585782\\/3D78C319282EC60822EBF2975DE783FF\\/30\",\n" +
                "    \"figureurl_1\": \"http:\\/\\/qzapp.qlogo.cn\\/qzapp\\/101585782\\/3D78C319282EC60822EBF2975DE783FF\\/50\",\n" +
                "    \"figureurl_2\": \"http:\\/\\/qzapp.qlogo.cn\\/qzapp\\/101585782\\/3D78C319282EC60822EBF2975DE783FF\\/100\",\n" +
                "    \"figureurl_qq_1\": \"http://thirdqq.qlogo.cn/g?b=oidb&k=dbtZjUbuJIrhxKBECKJGnA&s=40\",\n" +
                "    \"figureurl_qq_2\": \"http://thirdqq.qlogo.cn/g?b=oidb&k=dbtZjUbuJIrhxKBECKJGnA&s=100\",\n" +
                "    \"figureurl_qq\": \"http://thirdqq.qlogo.cn/g?b=oidb&k=dbtZjUbuJIrhxKBECKJGnA&s=140\",\n" +
                "    \"figureurl_type\": \"1\",\n" +
                "    \"is_yellow_vip\": \"0\",\n" +
                "    \"vip\": \"0\",\n" +
                "    \"yellow_vip_level\": \"0\",\n" +
                "    \"level\": \"0\",\n" +
                "    \"is_yellow_year_vip\": \"0\"\n" +
                "}";
        ObjectMapper objectMapper  = new ObjectMapper();
        ;
        System.out.println(JSONObject.parseObject(temp,QQUserInfo.class));
        System.err.println(objectMapper.readValue(temp,QQUserInfo.class));
    }
}
