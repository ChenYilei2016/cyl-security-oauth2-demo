package com.chenyilei.security.core.properties;

import com.chenyilei.security.core.properties.real.QQProperties;
import lombok.Data;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/03- 17:23
 */
@Data
public class SocialProperties {
    private QQProperties QQ = new QQProperties();
    private String filterProcessesUrl = "/auth" ;

}
