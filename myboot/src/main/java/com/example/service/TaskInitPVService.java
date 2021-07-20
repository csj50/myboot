package com.example.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.base.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * 启动线程模拟PV请求
 * @author user
 *
 */
@Service
@Slf4j
public class TaskInitPVService {
	
	/**
	 * 初始化启动线程
	 */
	//@PostConstruct //注释不运行，防止访问redis太频繁
	public void initPVTask() {
		log.info("启动模拟大量PV请求 定时器..........");
		new Thread(()->runTask()).start();
	}
	
	/**
	 * 模拟PV请求
	 */
	public void runTask() {
		while(true) {
			this.batchAddArticle();
			try {
				//5秒执行一次
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 对1000篇文章，进行模拟请求
	 */
	public void batchAddArticle() {
		for (int i=0; i < 100; i++) { //改成100篇
			this.addPV(new Integer(i));
		}
	}
	
	/**
	 * 增加PV数量
	 * @param id
	 */
	public void addPV(Integer id) {
		//生产环境：时间块为5分钟
		//long min5 = System.currentTimeMillis() / (1000*60*5); //时间块
		//为了方便测试，改为1分钟
		long min1 = System.currentTimeMillis() / (1000*60*1);
		
		Map<Integer, Integer> map = Constants.PV_MAP.get(min1); //获取一级缓存map
		if (null == map) {
			map = new ConcurrentHashMap<Integer, Integer>();
			map.put(id, new Integer(1));
			Constants.PV_MAP.put(min1, map); //put进去 <1分钟时间块, Map<文章id, 访问量>>
		} else {
			//通过文章id，取出浏览量
			Integer value = map.get(id);
			if (null == value) {
				map.put(id, new Integer(1));
			} else {
				map.put(id, value+1);
			}
		}
	}
}
