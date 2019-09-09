package com.chenyilei.security.core.validate.code.sms;

import com.chenyilei.security.core.validate.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 19:00
 */
public class FakerSmsSenderInterface extends AbstractSmsSenderInterface {

    @Override
    protected void sendInternal(String phone, ValidateCode code) {
        System.err.println("模拟发短信中----");
        System.err.println("接收用户:"+phone+"; 验证码:"+code.getCode());
        System.err.println("发短信结束----");
    }
}
