package com.chenyilei.demo1.controller;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/28- 13:17
 */
public class MyException extends RuntimeException{

    public String id;

    public MyException (String id ){
        this.id = id;
    }
}
