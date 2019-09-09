package com.chenyilei.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *  判断出错是否是浏览器的请求,返回页面 or json
 * {@link org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController}
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/27- 13:50
 */

@RestController

@EnableSwagger2
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.chenyilei.demo1","com.chenyilei.security"})
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class,args);
    }


//    @Bean
//    public ValidateCodeGenerator smsCodeGenerator(){
//        return new ValidateCodeGenerator() {
//            @Override
//            public ValidateCode generator() {
//                System.err.println("错误的生成");
//                return new ValidateCode("1000",100);
//            }
//        };
//    }
}
