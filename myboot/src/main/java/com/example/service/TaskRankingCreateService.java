package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.base.Constants;
import com.example.domain.Ranking;

import lombok.extern.slf4j.Slf4j;

/**
 * 生成榜单初始信息
 * @author user
 *
 */
@Service
@Slf4j
public class TaskRankingCreateService {

	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 把数据刷新到redis里面
	 */
	@PostConstruct
	public void rankingCreateTask() {
		log.info("生成榜单 启动初始化..........");
		new Thread(()->runTask()).start();
	}
	
	public void runTask() {
		List<Ranking> rankingLists = ranking();
		rankingLists.forEach(t->redisTemplate.opsForSet().add(Constants.RANKING_LIST, t));
	}
	
	/**
	 * 生成榜单
	 * @return
	 */
	public List<Ranking> ranking() {
		List<Ranking> lists = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Random random = new Random();
			Ranking ranking = new Ranking();
			ranking.setId(i);
			ranking.setName("榜单" + i);
			List<String> strs = new ArrayList<>();
			for (int j = 0; j < 3; j++) {
				String temp = "user" + random.nextInt(1000);
				strs.add(temp);
			}
			ranking.setContents(strs);
			lists.add(ranking);
		}
		return lists;
	}
}
