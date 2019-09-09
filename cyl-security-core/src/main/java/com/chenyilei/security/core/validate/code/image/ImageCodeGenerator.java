package com.chenyilei.security.core.validate.code.image;

import cn.hutool.captcha.CaptchaUtil;
import com.chenyilei.security.core.properties.SecurityProperties;
import com.chenyilei.security.core.validate.code.ValidateCode;
import com.chenyilei.security.core.validate.code.ValidateCodeGenerator;
import com.chenyilei.security.core.validate.code.image.ImageCode;
import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 10:38
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {
    SecurityProperties securityProperties;

    public ImageCodeGenerator(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public ValidateCode generator() {
        String randomNumeric = RandomStringUtils.randomNumeric(securityProperties.getValiadate().getImage().getLength());
        Image image = CaptchaUtil.createLineCaptcha(
                securityProperties.getValiadate().getImage().getWidth(),
                securityProperties.getValiadate().getImage().getHeignt(),
                securityProperties.getValiadate().getImage().getLength(),
                securityProperties.getValiadate().getImage().getFlxCount()).createImage(randomNumeric);
        ImageCode imageCode = new ImageCode(image,randomNumeric,securityProperties.getValiadate().getImage().getExpiretime());
        return imageCode;
    }
}
