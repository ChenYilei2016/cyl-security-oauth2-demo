package com.chenyilei.security.core.properties.real;

import com.chenyilei.security.core.enumType.LoginTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/30- 14:50
 */
@Data
public class BrowserProperties {
    private String loginPage = "/imooc-signIn.html";
    private LoginTypeEnum loginType = LoginTypeEnum.JSON;
    private Integer tokenExpire = 3600;
    private String signUpUrl = "/imooc-signUp.html";
}
