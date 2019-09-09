package com.chenyilei.security.core.validate.code;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.img.ImgUtil;
import com.chenyilei.security.core.properties.SecurityProperties;
import com.chenyilei.security.core.properties.ValiadateProperties;
import com.chenyilei.security.core.validate.code.image.ImageCode;
import com.chenyilei.security.core.validate.code.processor.ImageCodeProcessor;
import com.chenyilei.security.core.validate.code.processor.SmsCodeProcessor;
import com.chenyilei.security.core.validate.code.processor.ValidateCodeProcessor;
import com.chenyilei.security.core.validate.code.sms.SmsSenderInterface;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

/**
 * 生成验证码的控制器
 * sms
 * image
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/30- 18:17
 */
@RestController
public class ValidateCodeController {

    @Autowired
    SecurityProperties securityProperties;

//    @Autowired
//    ValidateCodeProcessor smsCodeProcessor;
//
//    @Autowired
//    ValidateCodeProcessor imageCodeProcessor;

    @Autowired
    Map<String, ValidateCodeProcessor> validateCodeProcessorMap;

    public static final String CODE_SESSION_KEY="cyl_CODE_SESSION_KEY_";

    @GetMapping("/code/{type:\\w{0,10}}")
    @PreAuthorize("permitAll()")
    public void code(HttpServletRequest request, HttpServletResponse response,
                     @PathVariable("type") String type) throws Exception {
        //根据类型选择自己的执行器
        String objectName = type + "CodeProcessor" ;
        ValidateCodeProcessor validateCodeProcessor = validateCodeProcessorMap.get(objectName);
        validateCodeProcessor.create(new ServletWebRequest(request,response));
    }

//    @GetMapping("/code/image")
//    public void codeImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
////        ImageCode imageCode = (ImageCode) imageCodeGenerator.generator();
////        sessionStrategy.setAttribute(new ServletWebRequest(request),CODE_SESSION_KEY,imageCode);
////        //返回图片
////        ImgUtil.writeJpg(imageCode.getImage(),response.getOutputStream());
////        response.flushBuffer();

//        imageCodeProcessor.create(new ServletWebRequest(request,response));
//    }
//
//    @GetMapping("/code/sms")
//    public void codeSms(HttpServletRequest request, HttpServletResponse response) throws Exception {
////        ValidateCode validateCode = smsCodeGenerator.generator();
////        sessionStrategy.setAttribute(new ServletWebRequest(request,response),CODE_SESSION_KEY,validateCode);
////        //发送短信
////        String phone = ServletRequestUtils.getStringParameter(request, "mobile","1771234567");
////        fakerSmsSenderInterface.send(phone,validateCode);

//        smsCodeProcessor.create(new ServletWebRequest(request,response));
//    }

}
