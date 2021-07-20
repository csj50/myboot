package com.example.service;

import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.base.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 热度排行榜初始化
 * @author user
 *
 */
@Service
@Slf4j
public class TaskWeiBoInitService {

	@Autowired
	private RedisTemplate redisTemplate;
	
	//@PostConstruct //注释不运行，防止访问redis太频繁
	public void initWeiboTask() {
		new Thread(()->init30day()).start();
	}
	
	/**
	 * 初始化1个月的历史数据
	 */
	public void init30day() {
		//计算当前的小时key
		long hour = System.currentTimeMillis() / (1000 * 60 * 60);
		//初始化近30天，每天24个key
		for(int i = 0; i < 24 * 30; i++) {
			String key = Constants.HOUR_KEY + (hour - i);
			initMember(key);
			System.out.println(key);
		}
	}
	
	/**
	 * 初始化某个小时的key
	 * @param key
	 */
	public void initMember(String key) {
		Random random = new Random();
		//采用26个字母来实现排行，随机为每个字母生成一个随机数作为score
		for (int i = 1; i <= 26; i++) {
			//小写字母a是97
			redisTemplate.opsForZSet().add(key, (char)(96 + i), random.nextInt(10));
		}
	}
	
}
