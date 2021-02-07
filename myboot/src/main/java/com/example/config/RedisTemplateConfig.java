package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisTemplateConfig {
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		// 创建一个json的序列化对象
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
		// 设置key的序列化方式为String
		redisTemplate.setKeySerializer(new GenericToStringSerializer<>(Object.class));
		// 设置value的序列化方式为json
		redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
		// 设置hashKey的序列化方式为String
		redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
		// 设置hashValue的序列化方式为json
		redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
		// 设置支持事务
		redisTemplate.setEnableTransactionSupport(true);
		// 初始化redisTemplate参数
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
