package com.chenyilei.security.browser;

import com.chenyilei.security.core.properties.SecurityProperties;
import com.chenyilei.security.core.support.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/30- 14:03
 */
@RestController
public class BrowserSecurityController {

    private RequestCache requestCache = new HttpSessionRequestCache(); //拦截的请求信息

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); //跳转的方法

    @Autowired
    private SecurityProperties securityProperties;

    //没有登录的信息返回
    @RequestMapping("/authentication/require")
    public Object requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

//        request.getHeader(XML-REQUEST)
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(null != savedRequest){
            String redirectUrl = savedRequest.getRedirectUrl();
            System.err.println("拦截的URL : "+ redirectUrl);
            if(redirectUrl.endsWith(".html")){
                //如果是请求的页面,那么就返回登录页面
                //登录的页面由自己来定
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
                return null;
            }
        }
        //不是页面,是Ajax 之类的 返回json
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SimpleResponse("没有权限"));
    }

}
