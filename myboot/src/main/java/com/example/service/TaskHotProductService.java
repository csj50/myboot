package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.base.Constants;
import com.example.domain.Product;

import lombok.extern.slf4j.Slf4j;

/**
 * 简单的定时任务
 * @author User
 *
 */
@Service
@Slf4j
public class TaskHotProductService {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	/**
	 * 初始化启动线程
	 */
	@PostConstruct
	public void initTask() {
		log.info("定时器启动......");
		new Thread(()->runTask()).start();
	}
	
	/**
	 * 定时更新redis缓存
	 */
	public void runTask() {
		while(true) {
			//获取热门商品
			List<Product> lists = this.getProducts();
			//更新redis缓存
			redisTemplate.delete(Constants.HOT_PRODUCT);
			
			//这样是把list对象存进去了，只有一条记录
			//redisTemplate.opsForList().leftPushAll(Constants.HOT_PRODUCT, lists);
			
			//要以Object数组保存才是100条记录
			//lpush命令
			redisTemplate.opsForList().leftPushAll(Constants.HOT_PRODUCT, lists.toArray());
			log.info("刷新缓存完成");
			
			//更新缓存A
			redisTemplate.delete(Constants.HOT_PRODUCT_A);
			redisTemplate.opsForList().leftPushAll(Constants.HOT_PRODUCT_A, lists.toArray());
			log.info("刷新缓存A完成");
			
			//更新缓存B
			redisTemplate.delete(Constants.HOT_PRODUCT_B);
			redisTemplate.opsForList().leftPushAll(Constants.HOT_PRODUCT_B, lists.toArray());
			log.info("刷新缓存B完成");
			
			log.info("product定时刷新完成......");
			//等待60秒
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取热门商品
	 * @return
	 */
	public List<Product> getProducts() {
		//模拟从数据库读取100件商品
		List<Product> products = new ArrayList<Product>();
		for (int i = 0; i< 100; i++) {
			Random rand = new Random();
			int id = rand.nextInt(10000);
			Product obj = new Product(id, "product"+id, id, "detail"+id);
			products.add(obj);
		}
		return products;
	}
}
