package com.chenyilei.security.core.validate.code.sms;

import com.chenyilei.security.core.validate.code.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 18:51
 */
public interface SmsSenderInterface {
    public void send(String phone, ValidateCode code);
}
