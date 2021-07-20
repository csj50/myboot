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
 * 天天抽奖
 * @author user
 *
 */
@Service
@Slf4j
public class TaskTTPrizeCreateService {
	
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 把数据刷新到redis里面
	 */
	@PostConstruct
	public void initTTPrizeCreateTask() {
		log.info("天天抽奖 启动初始化..........");
		new Thread(()->runTask()).start();
	}
	
	public void runTask() {
		List<Integer> prizes = prize();
		prizes.forEach(t->redisTemplate.opsForSet().add(Constants.TT_PRIZE_KEY, t));
	}
	
	/**
	 * 模拟10个用户来抽奖
	 * 用户参与抽奖，就把用户id加入到set集合中
	 */
	public List<Integer> prize() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		return list;
	}
}
