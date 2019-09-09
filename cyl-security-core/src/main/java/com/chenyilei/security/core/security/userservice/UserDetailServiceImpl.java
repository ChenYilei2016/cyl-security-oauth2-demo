package com.chenyilei.security.core.security.userservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 普通账号密码登录数据库查询
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/29- 21:00
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService, SocialUserDetailsService, Serializable {
    @Autowired
    PasswordEncoder passwordEncoder;
//    @Autowired
//    JdbcUsersConnectionRepository jdbcUsersConnectionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //通过username 找到一个用户 ,返回这个用户 说明登录成功
        //查出来的密码是加密的
        User user = new User(username,"root!", AuthorityUtils.createAuthorityList("ROLE_USER"));  //代表权限
        return new User(username,passwordEncoder.encode("root"),AuthorityUtils.createAuthorityList("ROLE_USER")){
            @Override
            public boolean isAccountNonExpired() {
                return super.isAccountNonExpired();
            }

            @Override
            public boolean isAccountNonLocked() {
                return super.isAccountNonLocked();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return super.isCredentialsNonExpired();
            }

            @Override
            public boolean isEnabled() {
                return true;//已经失效
            }
        };
    }


    @Override
    public SocialUser loadUserByUserId(String userId) {
        return new SocialUser(userId,passwordEncoder.encode("123456"),
                true,true,true,true,
                AuthorityUtils.createAuthorityList("admin"));
    }
}
