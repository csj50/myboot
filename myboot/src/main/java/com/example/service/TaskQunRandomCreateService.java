package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.base.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * QQ群推荐
 * @author user
 *
 */
@Service
@Slf4j
public class TaskQunRandomCreateService {

	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 把数据刷新到redis里面
	 */
	@PostConstruct
	public void qunRandomCreateTask() {
		log.info("随机展示QQ群 启动初始化..........");
		new Thread(()->runTask()).start();
	}
	
	public void runTask() {
		List<String> lists = qunRandom();
		lists.forEach(t->redisTemplate.opsForSet().add(Constants.QUN_RANDOM_KEY, t));
	}
	
	/**
	 * 模拟生成100个热门群，用于推荐
	 * @return
	 */
	public List<String> qunRandom() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Random rand = new Random();
			int id = rand.nextInt(10000);
			list.add("群" + id);
		}
		return list;
	}
}
