package com.chenyilei.security.core.properties;

import com.chenyilei.security.core.properties.real.BrowserProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/30- 14:49
 */
@Data
@ConfigurationProperties(prefix = "com.chenyilei")
public class SecurityProperties {
    
    @NestedConfigurationProperty
    private BrowserProperties browser = new BrowserProperties();
    @NestedConfigurationProperty
    private ValiadateProperties valiadate = new ValiadateProperties();
    @NestedConfigurationProperty
    private SocialProperties social = new SocialProperties();
}
