package com.example.service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.create.entity.TblTeacherInf;
import com.create.entity.TblTeacherInfMapper;
import com.example.utils.ShortUrlGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisExampleService {

	@Autowired
	TblTeacherInfMapper teacherMapper;

	@Autowired
	RedisTemplate<Object, Object> redisTemplate; // 初始化泛型防止警告提示
	
	public void incr(Integer id) {
		String key = "article:" + id;
		//调用redis的increment命令
		redisTemplate.opsForValue().increment(key);
	}
	
	public void hash(TblTeacherInf teacher) {
		//先存入db
		teacherMapper.insert(teacher);
		// 再查询出插入的对象
		teacher = teacherMapper.selectByPrimaryKey(teacher.getId());
		if (null != teacher) {
			String key = "redis-cache-hash:" + teacher.getId(); 
			Map<String, Object> map = objectToMap(teacher);
			//putall等于hmset命令
			redisTemplate.opsForHash().putAll(key, map);
		}
	}
	
	/**
	 * 将object对象转为map
	 * @param obj
	 * @return
	 */
	public Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> map = new HashMap<>();
		Class<?> clazz = obj.getClass();
		for(Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put(fieldName, value);
		}
		return map;
	}
	
	/**
	 * 长链接转短链接
	 * @param url
	 * @return
	 */
	public String urlEncode(String url) {
		String key = "short-url";
		//生成短链接
		String shortUrl = ShortUrlGenerator.shortUrl(url)[0];
		//存入redis
		redisTemplate.opsForHash().put(key, shortUrl, url);
		return "http://127.0.0.1:8088/redisExample/" + shortUrl;
	}
	
	/**
	 * 短链接跳转原url
	 * @param url
	 */
	public String urlDecode(String url) {
		String key = "short-url";
		//从redis中取出原始url
		String oriUrl = (String)redisTemplate.opsForHash().get(key, url);
		log.info("原始url: {}", oriUrl);
		return oriUrl;
	}
}
