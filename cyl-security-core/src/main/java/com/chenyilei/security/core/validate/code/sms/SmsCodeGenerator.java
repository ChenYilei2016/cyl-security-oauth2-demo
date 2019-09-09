package com.chenyilei.security.core.validate.code.sms;

import com.chenyilei.security.core.properties.SecurityProperties;
import com.chenyilei.security.core.validate.code.ValidateCode;
import com.chenyilei.security.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * 生成短信验证码
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 18:40
 */
public class SmsCodeGenerator implements ValidateCodeGenerator {
    private SecurityProperties securityProperties;

    public SmsCodeGenerator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ValidateCode generator() {
        ValidateCode validateCode = new ValidateCode(
                RandomStringUtils.randomNumeric(securityProperties.getValiadate().getSms().getLength()),
                securityProperties.getValiadate().getSms().getExpireTime());
        return validateCode;
    }
}
