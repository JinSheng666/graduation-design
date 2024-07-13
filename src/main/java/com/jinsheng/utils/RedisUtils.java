package com.jinsheng.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */

    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public void hset(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public void hset(String key, String item, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time, timeUnit);
        }
    }

    /**
     * @param key 键
     * @param map 多个field-value
     */
    public void hmset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * @param key      键
     * @param map      多个field-value
     * @param time     过期时间
     * @param timeUnit 时间单位
     */
    public void hmset(String key, Map<String, Object> map, long time, TimeUnit timeUnit) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time, timeUnit);
        }
    }

    /**
     * @param key  键
     * @param item Field
     * @return 获得的值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * @param key 键
     * @return hash表中key对应的map
     */
    public Map<Object, Object> hEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @param key   键
     * @param value 值
     */
    public void sadd(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * @param key      键
     * @param value    值
     * @param time     过期时间
     * @param timeUnit 时间单位
     */
    public void sadd(String key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForSet().add(key, value);
        if (time > 0) {
            expire(key, time, timeUnit);
        }
    }

    /**
     * @param key 键
     * @return 数据
     */
    public Set<Object> smembers(String key) {
        return redisTemplate.opsForSet().members(key);

    }

    public void expire(String key, long time, TimeUnit timeUnit) {
        if (time > 0) {
            redisTemplate.expire(key, time, timeUnit);
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
}

