package com.example.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.base.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 生产黑名单信息
 * @author user
 *
 */
@Service
@Slf4j
public class TaskBlackListCreateService {

	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 把数据刷新到redis里面
	 */
	@PostConstruct
	public void initBlackListCreateTask() {
		log.info("创建黑名单 启动初始化..........");
		List<Integer> blackList = blackList();
		blackList.forEach(t->redisTemplate.opsForSet().add(Constants.BLACKLIST_KEY, t));
	}
	
	/**
	 * 生成黑名单
	 * @return
	 */
	public List<Integer> blackList() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0 ; i < 100; i++) {
			list.add(i);
		}
		return list;
	}
}
