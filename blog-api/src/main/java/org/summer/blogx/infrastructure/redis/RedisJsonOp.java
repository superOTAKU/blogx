package org.summer.blogx.infrastructure.redis;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.summer.blogx.infrastructure.jackson.JsonHelper;

import java.util.concurrent.TimeUnit;

/**
 * 封装使用redis存取json逻辑，避免使用fastjson
 */
@Component
public class RedisJsonOp {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private JsonHelper jsonHelper;

    public void setJson(String key, Object obj) {
        redisTemplate.opsForValue().set(key, jsonHelper.toJson(obj));
    }

    public void setJson(String key, Object obj, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, jsonHelper.toJson(obj), time, timeUnit);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return jsonHelper.toObject(value.toString(), clazz);
    }

    public <T> T getObject(String key, TypeReference<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return jsonHelper.toObject(value.toString(), type);
    }

}
