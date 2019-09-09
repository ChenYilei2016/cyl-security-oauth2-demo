package com.chenyilei.security.core.validate.code.sms;

import com.chenyilei.security.core.validate.code.ValidateCode;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/31- 19:07
 */
public abstract class AbstractSmsSenderInterface implements SmsSenderInterface {
    @Override
    public final void send(String phone, ValidateCode code) {
        boolean check = check(phone, code);
        if( ! check ){
            throw new RuntimeException("电话号码有错...");
        }
        sendInternal(phone,code);
    }
    protected  boolean check(String phone, ValidateCode code){
        return true;
    }
    protected abstract void sendInternal(String phone, ValidateCode code);
}
