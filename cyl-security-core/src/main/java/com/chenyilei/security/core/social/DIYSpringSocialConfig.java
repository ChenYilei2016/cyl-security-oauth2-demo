package com.chenyilei.security.core.social;

import lombok.Data;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/05- 19:54
 */
@Data
public class DIYSpringSocialConfig extends SpringSocialConfigurer {

    private String filterProcessesUrl;

    public DIYSpringSocialConfig(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;

    }

    /**
     * 对spring的过滤器进行一个修改
     * @param object
     * @param <T>
     * @return
     */
    @Override
    protected <T> T postProcess(T object) {
        if(object instanceof SocialAuthenticationFilter){
            SocialAuthenticationFilter filter = (SocialAuthenticationFilter)super.postProcess(object);;
            filter.setFilterProcessesUrl(filterProcessesUrl);
            return (T)filter;
        }
        return super.postProcess(object);
    }
}
