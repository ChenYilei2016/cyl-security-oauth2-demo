package com.chenyilei.security.core.properties.real;

import lombok.Data;

/**
 * 短信相关属性设置
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 18:42
 */
@Data
public class SmsCodeProperties {
    private int length = 6;
    private int expireTime = 3600;
    private String filterUrl = "/authentication/mobile"; //图片验证码拦截器的拦截地址,用,隔开
}
