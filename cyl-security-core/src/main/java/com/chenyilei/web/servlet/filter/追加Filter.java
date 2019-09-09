package com.chenyilei.web.servlet.filter;

import com.chenyilei.web.servlet.MyRequest;
import com.chenyilei.web.servlet.MyResponse;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/29- 18:03
 */
public class 追加Filter implements MyFilter {
    @Override
    public void doFilter(MyRequest myRequest, MyResponse myResponse, MyFilterChain myFilterChain) {
        myResponse.setResMsg(myResponse.getResMsg()+" 这里是追加的内容!");
        myFilterChain.doFilter(myRequest,myResponse);
    }
}
