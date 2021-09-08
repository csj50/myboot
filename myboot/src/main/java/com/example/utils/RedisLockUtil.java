package com.example.utils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * redis分布式锁
 * 
 * @author user
 *
 */
@Component
@Slf4j
public class RedisLockUtil {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 加锁
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 * @param timeUnit
	 * @return
	 */
	public boolean lock(String key, String value, int expireTime, TimeUnit timeUnit) {
		// key不存在则创建存储key，key存在则返回null
		Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, value, expireTime, timeUnit);
		if (flag == null || flag == false) {
			log.info("锁({},{})失败,该锁已存在或加锁失败", key, value);
			return false;
		} else {
			log.info("锁({},{})成功", key, value);
			return true;
		}
	}

	/**
	 * 释放锁
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean unlock(String key, String value) {
		// 使用lua脚本保证原子性
		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
		Long result = (Long) stringRedisTemplate.execute(redisScript, Arrays.asList(key), value);
		if (result == null || result == 0) {
			log.info("释放锁({},{})失败,该锁不存在或锁已经过期", key, value);
			return false;
		} else {
			log.info("释放锁({},{})成功", key, value);
			return true;
		}
	}
}
