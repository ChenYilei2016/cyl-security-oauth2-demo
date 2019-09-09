package com.chenyilei.web.servlet;

import cn.hutool.captcha.LineCaptcha;
import com.chenyilei.web.servlet.filter.MyFilterChain;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/29- 18:00
 */
public class MAIN {

    public static void main(String[] args) {
        MyRequest myRequest = new MyRequest();
        myRequest.setReqMsg("this is a request");
        MyResponse myResponse = new MyResponse();

        MyFilterChain myFilterChain = new MyFilterChain();
        myFilterChain.doFilter(myRequest,myResponse);

        System.out.println(myResponse);

        LineCaptcha lineCaptcha  = new LineCaptcha(100,60);
        lineCaptcha.createImage("3");
    }
}
