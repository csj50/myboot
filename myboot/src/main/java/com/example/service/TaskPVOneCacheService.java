package com.example.service;

import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.base.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 一级缓存定时器消费
 * @author user
 *
 */
@Service
@Slf4j
public class TaskPVOneCacheService {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	/**
	 * 初始化启动线程
	 */
	@PostConstruct
	public void initPVOneCacheTask() {
		log.info("启动定时器 PV一级缓存消费..........");
		new Thread(()->runTask()).start();
	}
	
	/**
	 * 一级缓存定时器消费
	 */
	public void runTask() {
		while(true) {
			this.consumePV();
			try {
				//1.5分钟执行一次
				Thread.sleep(1000*90);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info("消费一级缓存，定时刷新..........");
		}
	}
	
	public void consumePV() {
		//取1分钟的时间块
		Long min1 = System.currentTimeMillis() / (1000*60*1);
		Iterator<Long> iterator = Constants.PV_MAP.keySet().iterator();
		while(iterator.hasNext()) {
			Long key = iterator.next();
			//小于当前的分钟时间块key，就消费
			if (key < min1) {
				Map<Integer, Integer> map = Constants.PV_MAP.get(key);
				//先push
				redisTemplate.opsForList().leftPush(Constants.CACHE_PV_LIST, map);
				//再remove
				Constants.PV_MAP.remove(key);
				log.info("push进{}",map);
			}
		}
	}
}
