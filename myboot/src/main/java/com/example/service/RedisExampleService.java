package com.example.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.create.entity.TblTeacherInf;
import com.create.entity.TblTeacherInfMapper;
import com.example.base.Constants;
import com.example.domain.CartCookie;
import com.example.domain.CartUser;
import com.example.domain.CartUserPage;
import com.example.domain.Product;
import com.example.utils.RedPacketUtil;
import com.example.utils.ShortUrlGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisExampleService {

	@Autowired
	TblTeacherInfMapper teacherMapper;

	@Autowired
	RedisTemplate<Object, Object> redisTemplate; // 初始化泛型防止警告提示
	
	public void incr(Integer id) {
		String key = "article:" + id;
		//调用redis的increment命令
		redisTemplate.opsForValue().increment(key);
	}
	
	public void hash(TblTeacherInf teacher) {
		//先存入db
		teacherMapper.insert(teacher);
		// 再查询出插入的对象
		teacher = teacherMapper.selectByPrimaryKey(teacher.getId());
		if (null != teacher) {
			String key = "redis-cache-hash:" + teacher.getId(); 
			Map<String, Object> map = objectToMap(teacher);
			//putall等于hmset命令
			redisTemplate.opsForHash().putAll(key, map);
		}
	}
	
	/**
	 * 将object对象转为map
	 * @param obj
	 * @return
	 */
	public Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> map = new HashMap<>();
		Class<?> clazz = obj.getClass();
		for(Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put(fieldName, value);
		}
		return map;
	}
	
	/**
	 * 长链接转短链接
	 * @param url
	 * @return
	 */
	public String urlEncode(String url) {
		String key = "short-url";
		//生成短链接
		String shortUrl = ShortUrlGenerator.shortUrl(url)[0];
		//存入redis
		redisTemplate.opsForHash().put(key, shortUrl, url);
		return "http://127.0.0.1:8088/redisExample/" + shortUrl;
	}
	
	/**
	 * 短链接跳转原url
	 * @param url
	 */
	public String urlDecode(String url) {
		String key = "short-url";
		//从redis中取出原始url
		String oriUrl = (String)redisTemplate.opsForHash().get(key, url);
		log.info("原始url: {}", oriUrl);
		return oriUrl;
	}
	
	/**
	 * 登录状态下添加购物车
	 * @param cart
	 */
	public void userAddCart(CartUser cart) {
		String key = "cart:user:" + cart.getUserId();
		Boolean hasKey = redisTemplate.opsForHash().getOperations().hasKey(key);
		if (hasKey) {
			//在购物车页面添加商品（包含之前数量）
			redisTemplate.opsForHash().put(key, cart.getProductId(), cart.getNumber());
		} else {
			//添加商品同时设置有效期
			redisTemplate.opsForHash().put(key, cart.getProductId(), cart.getNumber());
			redisTemplate.expire(key, 30, TimeUnit.DAYS);
		}
		//发送MQ异步修改数据库
	}
	
	/**
	 * 登录状态下修改购物车
	 * @param cart
	 */
	public void userUpdateCart(CartUser cart) {
		String key = "cart:user:" + cart.getUserId();
		redisTemplate.opsForHash().put(key, cart.getProductId(), cart.getNumber());
		//发送MQ异步修改数据库
	}
	
	/**
	 * 登录状态下删除购物车商品
	 * @param cart
	 */
	public void userDelCart(long userId, long productId) {
		String key = "cart:user:" + userId;
		redisTemplate.opsForHash().delete(key, productId);
		//发送MQ异步修改数据库
	}
	
	/**
	 * 查询购物车所有商品
	 * @param userId
	 * @return
	 */
	public CartUserPage<CartUser> findAll(long userId) {
		String key = "cart:user:" + userId;
		CartUserPage<CartUser> cartUserPage = new CartUserPage<CartUser>();
		//查询购物车总数
		long size = redisTemplate.opsForHash().size(key);
		cartUserPage.setCount((int)size);
		//查询购物车的所有商品
		//entries等于hgetall命令
		Map<Object, Object> map = redisTemplate.opsForHash().entries(key); //redis取出来默认是String
		//取出来的map里，key都是string，value根据传入的类型是String、Integer、Long
		List<CartUser> cartUserList = new ArrayList<>();
		for(Map.Entry<Object, Object> entry : map.entrySet()) {
			CartUser cart = new CartUser();
			cart.setUserId(userId);
			cart.setProductId(Long.parseLong((String)entry.getKey()));
			cart.setNumber(((Integer)entry.getValue()).intValue());
			cartUserList.add(cart);
		}
		cartUserPage.setCartList(cartUserList);
		return cartUserPage;
	}
	
	/**
	 * 未登录状态下添加购物车
	 * @param cart
	 */
	public void cookieAddCart(CartCookie cart, String cookieId) {
		String key = "cart:cookie:" + cookieId;
		Boolean hasKey = redisTemplate.opsForHash().getOperations().hasKey(key);
		if (hasKey) {
			//在购物车页面添加商品（包含之前数量）
			redisTemplate.opsForHash().put(key, cart.getProductId(), cart.getNumber());
		} else {
			//添加商品同时设置有效期
			redisTemplate.opsForHash().put(key, cart.getProductId(), cart.getNumber());
			redisTemplate.expire(key, 30, TimeUnit.DAYS);
		}
		//发送MQ异步修改数据库
	}
	
	/**
	 * 未登录状态下修改购物车
	 * @param cart
	 */
	public void cookieUpdateCart(CartCookie cart, String cookieId) {
		String key = "cart:cookie:" + cookieId;
		redisTemplate.opsForHash().put(key, cart.getProductId(), cart.getNumber());
		//发送MQ异步修改数据库
	}
	
	/**
	 * 未登录状态下删除购物车商品
	 * @param cart
	 */
	public void cookieDelCart(String productId, String cookieId) {
		String key = "cart:cookie:" + cookieId;
		redisTemplate.opsForHash().delete(key, productId);
		//发送MQ异步修改数据库
	}
	
	/**
	 * 未登录状态下查询购物车所有商品
	 * @param userId
	 * @return
	 */
	public CartUserPage<CartCookie> cookieFindAll(String cookieId) {
		String key = "cart:cookie:" + cookieId;
		CartUserPage<CartCookie> cartUserPage = new CartUserPage<CartCookie>();
		//查询购物车总数
		long size = redisTemplate.opsForHash().size(key);
		cartUserPage.setCount((int)size);
		//查询购物车的所有商品
		//entries等于hgetall命令
		Map<Object, Object> map = redisTemplate.opsForHash().entries(key); //redis取出来默认是String
		//取出来的map里，key都是String类型，value根据传入的类型是String、Integer、Long
		List<CartCookie> cartUserList = new ArrayList<>();
		for(Map.Entry<Object, Object> entry : map.entrySet()) {
			CartCookie cart = new CartCookie();
			cart.setProductId(Long.parseLong((String)entry.getKey()));
			cart.setNumber(((Integer)entry.getValue()).intValue());
			cartUserList.add(cart);
		}
		cartUserPage.setCartList(cartUserList);
		return cartUserPage;
	}
	
	/**
	 * 合并购物车
	 * @param userId
	 * @param cookieId
	 */
	public void cookieMergeCart(long userId, String cookieId) {
		String key = "cart:cookie:" + cookieId;
		//第一步：获取未登录cookie的购物车数据
		Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
		//第二步：把cookie的购物车合并到登录用户下
		String userKey = "cart:user:" + userId;
		redisTemplate.opsForHash().putAll(userKey, map);
		//第三步：删除未登录cookie的购物车
		redisTemplate.delete(key);
	}
	
	/**
	 * 查询抢购热门商品
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Product> findHotProduct (int page, int size) {
		//从redis中分页查询，高并发情况下走db会把db打垮
		List<Object> lists = null;
		int start = (page - 1) * size; //page下标从1开始，size是每页大小
		int end = start + size - 1;
		
		//防止缓存击穿，使用下面两块缓存
		//lists = redisTemplate.opsForList().range(Constants.HOT_PRODUCT, start, end);
		
		//查询缓存A
		lists = redisTemplate.opsForList().range(Constants.HOT_PRODUCT_A, start, end);
		if (lists == null) {
			//查询缓存B
			lists = redisTemplate.opsForList().range(Constants.HOT_PRODUCT_B, start, end);
		}
		
		if (lists == null) {
			//查询db
		}
		
		List<Product> products = new ArrayList<Product>();
		for(Object obj : lists) {
			Product p = (Product)obj;
			products.add(p);
		}
		
		return products;
	}
	
	/**
	 * 包红包接口
	 * @param total
	 * @param count
	 * @return
	 */
	public String setRedPacket(double total, int count) {
		//红包大key的前缀
		String red_packet_key = "redpacket:";
		//拆解红包
		Double[] packet = RedPacketUtil.splitRedPacket(total, count);
		String redPacketId = RedPacketUtil.incrementId();
		String key = red_packet_key + redPacketId;
		//存入redis
		redisTemplate.opsForList().leftPushAll(key, packet);
		//设置3天过期
		redisTemplate.expire(key, 3, TimeUnit.DAYS);
		log.info("拆解红包{}={}", key, packet);
		return redPacketId;
	}
	
	/**
	 * 抢红包接口
	 * @param redPacketId
	 * @param userId
	 * @return
	 */
	public String robRedPacket(String redPacketId, String userId) {
		//红包大key的前缀
		String red_packet_key = "redpacket:";
		//红包消费大key的前缀
		String red_packet_consume_key = "redpacket:consume:";
		//第一步：验证用户是否抢过
		Object packet = redisTemplate.opsForHash().get(red_packet_consume_key + redPacketId, userId);
		if (packet == null) {
			//第二步：从list队列弹出一个红包
			Object obj = redisTemplate.opsForList().leftPop(red_packet_key + redPacketId);
			if (obj == null) {
				return "红包已抢完";
			} else {
				//第三步：把用户抢红包的记录存起来
				redisTemplate.opsForHash().put(red_packet_consume_key + redPacketId, userId, obj);
				log.info("用户{}抢到{}", userId, obj);
				//TODO: 异步把数据落地到数据库上
				return String.valueOf((Double)obj);
			}
		} else {
			return "用户已抢过红包";
		}
	}
	
	/**
	 * 返回文章浏览量
	 * @param id
	 * @return
	 */
	public String viewPV(Integer id) {
		String key = Constants.CACHE_PV_ARTICLE + id;
		String num = (String)redisTemplate.opsForValue().get(key);
		log.info("key={},阅读量为={}", key, num);
		return num;
	}
}
