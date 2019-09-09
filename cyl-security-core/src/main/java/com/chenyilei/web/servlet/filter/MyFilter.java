package com.chenyilei.web.servlet.filter;

import com.chenyilei.web.servlet.MyRequest;
import com.chenyilei.web.servlet.MyResponse;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/29- 17:57
 */
public interface MyFilter {
    void doFilter(MyRequest myRequest , MyResponse myResponse, MyFilterChain myFilterChain);
}
