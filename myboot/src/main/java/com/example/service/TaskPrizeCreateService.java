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
 * 生成奖品信息
 * @author user
 *
 */
@Service
@Slf4j
public class TaskPrizeCreateService {

	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 把数据刷新到redis里面
	 */
	@PostConstruct
	public void initPrizeCreateTask() {
		log.info("生成奖品信息 启动初始化..........");
		new Thread(()->runTask()).start();
	}
	
	public void runTask() {
		List<String> prizes = prize();
		prizes.forEach(t->redisTemplate.opsForSet().add(Constants.PRIZE_KEY, t));
	}
	
	/**
	 * 生成奖品
	 * @return
	 */
	public List<String> prize() {
		List<String> list = new ArrayList<>();
		//10个金币，概率为10%
		for (int i = 0; i < 10; i++) {
			list.add("10-"+i);
		}
		//5个金币，概率为20%
		for (int i = 0; i < 20; i++) {
			list.add("5-"+i);
		}
		//1个金币，概率为60%
		for (int i = 0; i < 60; i++) {
			list.add("1-"+i);
		}
		//0个金币，概率为10%
		for (int i = 0; i < 10; i++) {
			list.add("0-"+i);
		}
		return list;
	}
}
