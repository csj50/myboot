package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.base.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 热度排行榜刷新
 * @author user
 *
 */
@Service
@Slf4j
public class TaskWeiBoDayHourService {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@PostConstruct
	public void initWeiBoDayHourTask() {
		log.info("热度刷新任务 启动初始化..........");
		//定时5秒刷新
		new Thread(()->refresh5second()).start();
		//定时1个小时合并
		new Thread(()->refreshData()).start();
	}
	
	/**
	 * 按小时统计
	 */
	public void refreshHour() {
		Random random = new Random();
		long hour = System.currentTimeMillis() / (1000 * 60 * 60);
		for (int i = 1; i <= 26; i++) {
			//小写字母a是97
			redisTemplate.opsForZSet().incrementScore(Constants.HOUR_KEY + hour, (char)(96 + i), random.nextInt(10));
		}
	}
	
	/*
	 * 定时5秒
	 */
	public void refresh5second() {
		while (true) {
			refreshHour();
			//TODO: 在分布式系统中，建议用xxljob来实现定时
			try {
				Thread.sleep(1000 * 5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 合并数据
	 */
	public void refreshData() {
		while (true) {
			refreshDay();
			refreshWeek();
			refreshMonth();
			//TODO: 在分布式系统中，建议用xxljob来实现定时
			try {
				Thread.sleep(1000 * 60 * 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 合并一天的统计数据
	 */
	public void refreshDay() {
		long hour = System.currentTimeMillis() / (1000 * 60 * 60);
		List<String> otherKeys = new ArrayList<>();
		//算出近24小时内的key
		for (int i = 1; i <= 23; i++) {
			String key = Constants.HOUR_KEY + (hour - i);
			otherKeys.add(key);
		}
		//把当前的时间key，并且把后推23个小时，共计近24个小时，求出并集存入Constants.DAY_KEY中
		redisTemplate.opsForZSet().unionAndStore(Constants.HOUR_KEY + hour , otherKeys, Constants.DAY_KEY);
		
		//设置当前的key 40天过期，不然历史数据浪费内存
		for (int i = 0; i < 24; i++) {
			String key = Constants.HOUR_KEY + (hour - i);
			redisTemplate.expire(key, 40, TimeUnit.DAYS);
		}
		log.info("天刷新完成..........");
	}
	
	/**
	 * 合并一周
	 */
	public void refreshWeek() {
		long hour = System.currentTimeMillis() / (1000 * 60 * 60);
		List<String> otherKeys = new ArrayList<>();
		//算出近7天内的key
		for (int i = 1; i <= 7*24-1; i++) {
			String key = Constants.HOUR_KEY + (hour - i);
			otherKeys.add(key);
		}
		//把当前的时间key，并且把后推24*7-1个小时，共计近24*7个小时，求出并集存入Constants.WEEK_KEY中
		redisTemplate.opsForZSet().unionAndStore(Constants.HOUR_KEY + hour , otherKeys, Constants.WEEK_KEY);
		log.info("周刷新完成..........");
	}
	
	/**
	 * 合并一月
	 */
	public void refreshMonth() {
		long hour = System.currentTimeMillis() / (1000 * 60 * 60);
		List<String> otherKeys = new ArrayList<>();
		//算出近30天内的key
		for (int i = 1; i <= 30*24-1; i++) {
			String key = Constants.HOUR_KEY + (hour - i);
			otherKeys.add(key);
		}
		//把当前的时间key，并且把后推24*30-1个小时，共计近24*30个小时，求出并集存入Constants.MONTH_KEY中
		redisTemplate.opsForZSet().unionAndStore(Constants.HOUR_KEY + hour , otherKeys, Constants.MONTH_KEY);
		log.info("月刷新完成..........");
	}
}
