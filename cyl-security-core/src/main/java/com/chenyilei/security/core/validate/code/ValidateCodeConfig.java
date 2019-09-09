package com.chenyilei.security.core.validate.code;

import com.chenyilei.security.core.properties.SecurityProperties;
import com.chenyilei.security.core.validate.code.image.ImageCodeGenerator;
import com.chenyilei.security.core.validate.code.processor.ImageCodeProcessor;
import com.chenyilei.security.core.validate.code.processor.SmsCodeProcessor;
import com.chenyilei.security.core.validate.code.sms.FakerSmsSenderInterface;
import com.chenyilei.security.core.validate.code.sms.SmsCodeGenerator;
import com.chenyilei.security.core.validate.code.sms.SmsSenderInterface;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 10:41
 */
@Configuration
public class ValidateCodeConfig {

    @Configuration
    class ImageCodeConfiguration{
        @Bean
        @ConditionalOnMissingBean(name = "imageCodeGenerator")
        public ValidateCodeGenerator imageCodeGenerator (SecurityProperties securityProperties){
            return new ImageCodeGenerator(securityProperties);
        }
        @Bean
        @ConditionalOnMissingBean(ImageCodeProcessor.class)
        public ImageCodeProcessor imageCodeProcessor (SecurityProperties securityProperties){
            return new ImageCodeProcessor();
        }
    }

    @Configuration
    class SmsCodeConfiguration{
        @Bean
        @ConditionalOnMissingBean(SmsCodeGenerator.class)
        public ValidateCodeGenerator smsCodeGenerator (SecurityProperties securityProperties){
            return new SmsCodeGenerator(securityProperties);
        }
        //短信发送器
        @Bean
        @ConditionalOnMissingBean(SmsSenderInterface.class)
        public  SmsSenderInterface smsSenderInterface(SecurityProperties securityProperties){
            return new FakerSmsSenderInterface();
        }

        //短信验证码的执行器
        @Bean
        @ConditionalOnMissingBean(SmsCodeProcessor.class)
        public SmsCodeProcessor smsCodeProcessor(SecurityProperties securityProperties){
            return new SmsCodeProcessor();
        }
    }

}
