package com.chenyilei.web.servlet.filter;

import com.chenyilei.web.servlet.MyRequest;
import com.chenyilei.web.servlet.MyResponse;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/29- 17:59
 */

public class MyFilterChain  {
    volatile CopyOnWriteArrayList<MyFilter> filterList = new CopyOnWriteArrayList();
    volatile AtomicInteger currentIndex = new AtomicInteger(0);
    volatile int all ;

    public MyFilterChain(){
        filterList.add(new 赋值Filter());
        filterList.add(new 追加Filter());
        all = filterList.size();
    }

    public void doFilter(MyRequest myRequest , MyResponse myResponse){
        int andIncrement = currentIndex.getAndIncrement();
        if(andIncrement == all){
            return ;
        }
        MyFilter myFilter = filterList.get(andIncrement);
        myFilter.doFilter(myRequest,myResponse,this);
    }
}
