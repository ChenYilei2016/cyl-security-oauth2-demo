package com.chenyilei.security.app.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.PostConstruct;

/**
 * 注释
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/09/08 14:08
 */
@Configuration
public class TokenConfig {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

//    @Bean
//    TokenStore tokenStore(){
////        return new RedisTokenStore(redisConnectionFactory);
////        JdbcTokenStore
//        return new InMemoryTokenStore();
//    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        return new JwtAccessTokenConverter();
    }
    @Bean
    public JwtTokenStore jwtTokenStore(JwtAccessTokenConverter jwtAccessTokenConverter){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }


}
