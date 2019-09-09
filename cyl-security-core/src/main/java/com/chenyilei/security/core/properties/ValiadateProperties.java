package com.chenyilei.security.core.properties;

import com.chenyilei.security.core.properties.real.ImageCodeProperties;
import com.chenyilei.security.core.properties.real.SmsCodeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 08:57
 */
@Data
public class ValiadateProperties {
    private ImageCodeProperties image = new ImageCodeProperties();
    private SmsCodeProperties sms = new SmsCodeProperties();
}
