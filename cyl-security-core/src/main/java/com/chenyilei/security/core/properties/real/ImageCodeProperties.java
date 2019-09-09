package com.chenyilei.security.core.properties.real;

import lombok.Data;

/**
 * 图片验证码
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 08:57
 */
@Data
public class ImageCodeProperties {
    private int width = 100;
    private int heignt = 43;
    private int length = 3;
    private int expiretime = 3600;
    private int flxCount = 10; //干扰线数量

    private String filterUrl = "/authentication/form"; //图片验证码拦截器的拦截地址,用,隔开
}
