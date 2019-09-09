package com.chenyilei.security.core.authentication.mobile;

import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;

import java.lang.annotation.*;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/01- 16:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SmsCodeAuthenticationSecurityConfig.class)
public @interface EnableSmsConfig {
}
