package com.chenyilei.security.core.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 短信登录认证的逻辑处理
 *
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/06/01- 14:21
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;

    /**
     * 将验证短信码分离,进入这里即是验证通过
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken)authentication;

        Object principal = smsCodeAuthenticationToken.getPrincipal();
        //1 根据电话号码找到用户名
        String username = "root";
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(null == userDetails){
            throw new InternalAuthenticationServiceException("无法获得用户信息");
        }

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationResult.setDetails(authentication.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setsUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
