/**
 *
 */
package com.chenyilei.security.app.validate.code.impl;

import com.chenyilei.security.core.validate.code.ValidateCode;
import com.chenyilei.security.core.validate.code.ValidateCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 *
 * @author zhangyang
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * (non-Javadoc)
     *
     */
    @Override
    public void save(ServletWebRequest request, ValidateCode code ) {
        redisTemplate.opsForValue().set(buildKey(request), code, 30, TimeUnit.MINUTES);
    }

    /**
     * (non-Javadoc)
     *

     */
    @Override
    public ValidateCode get(ServletWebRequest request) {
        Object value = redisTemplate.opsForValue().get(buildKey(request));
        if (value == null) {
            return null;
        }
        return (ValidateCode) value;
    }

    /**
     * (non-Javadoc)
     *

     */
    @Override
    public void remove(ServletWebRequest request) {
        redisTemplate.delete(buildKey(request));
    }

    /**
     * @param request
     * @param type
     * @return
     */
    private String buildKey(ServletWebRequest request) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            throw new RuntimeException("请在请求头中携带deviceId参数");
        }
        return "code:" + ":" + deviceId;
    }

}
