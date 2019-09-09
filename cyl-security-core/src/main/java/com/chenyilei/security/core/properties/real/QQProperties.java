package com.chenyilei.security.core.properties.real;

import lombok.Data;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/03- 17:21
 */
@Data
public class QQProperties {
    private String appId;
    private String appSecret;
    private String providerId = "qq"; //QQ提供商
}
