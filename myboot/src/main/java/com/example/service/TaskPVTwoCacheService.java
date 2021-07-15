package com.example.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.base.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 二级缓存定时器消费
 * @author user
 *
 */
@Service
@Slf4j
public class TaskPVTwoCacheService {

	@Autowired
	private RedisTemplate redisTemplate; //不指定类型，自动转换
	
	@PostConstruct
	public void initPVTwoCacheTask() {
		log.info("启动定时器 PV二级缓存消费..........");
		new Thread(()->runTask()).start();
	}
	
	/**
	 * 二级缓存消费
	 */
	public void runTask() {
		boolean flag;
		while(true) {
			flag = true;
			while(flag) {
				flag = pop();
			}
			try {
				//2分钟执行一次
				Thread.sleep(1000*60*2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("消费一级缓存，定时刷新..........");
		}
	}
	
	public boolean pop() {
		//从redis的list数据结构pop弹出Map<文章id,访问量PV>
		ListOperations<String, Map<Integer, Integer>> listOperations = redisTemplate.opsForList();
		Map<Integer, Integer> map = listOperations.rightPop(Constants.CACHE_PV_LIST);
		log.info("弹出pop={}", map);
		if (null == map) {
			return false;
		}
		//第一步：先存入数据库
		//TODO: 插入数据库
		
		//第二步：同步redis缓存
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			log.info("key={},value={}", entry.getKey(), entry.getValue());
			String key = Constants.CACHE_PV_ARTICLE + entry.getKey();
			//调用redis的increment命令
			redisTemplate.opsForValue().increment(key, entry.getValue());
		}
		return true;
	}
}
