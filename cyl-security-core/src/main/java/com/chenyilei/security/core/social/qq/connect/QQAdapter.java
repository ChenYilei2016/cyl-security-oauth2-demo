package com.chenyilei.security.core.social.qq.connect;

import com.chenyilei.security.core.social.qq.api.QQ;
import com.chenyilei.security.core.social.qq.api.QQUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/03- 14:59
 */
public class QQAdapter implements ApiAdapter<QQ> {
    //测试api是否可用
    @Override
    public boolean test(QQ api) {
        return true;
    }

    //适配数据
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();
        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        values.setProviderUserId(userInfo.getOpenId());
        // 个人主页，qq没有
        values.setProfileUrl(null);
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

    @Override
    public void updateStatus(QQ api, String message) {
        //更新微博消息
    }
}
