package com.example.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.create.entity.TblTeacherInf;
import com.example.domain.Product;
import com.example.domain.Ranking;
import com.example.service.RedisExampleService;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "redis应用场景实例")
@RestController
@RequestMapping("/redisExample")
public class RedisExampleController {
	
	@Autowired
	RedisExampleService redisExampleService;
	
	@ApiOperation("incr命令统计访问量")
	@GetMapping("/incr")
	public void incr(Integer id) {
		redisExampleService.incr(id);
	}
	
	@ApiOperation("hash使用测试")
	@GetMapping("/hash")
	public void hash(TblTeacherInf teacher) {
		redisExampleService.hash(teacher);
	}
	
	@ApiOperation("长链接转换短链接")
	@GetMapping("/urlEncode")
	public String urlEncode(String url) {
		return redisExampleService.urlEncode(url);
	}
	
	@ApiOperation("短链接跳转原url")
	@GetMapping("/{url}")
	public void urlDncode(@PathVariable String url, HttpServletResponse response) {
		String oriUrl = redisExampleService.urlDecode(url);
		try {
			response.sendRedirect(oriUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@ApiOperation("查询抢购热门商品")
	@GetMapping("/findHotProduct")
	public List<Product> findHotProduct(int page, int size) {
		return redisExampleService.findHotProduct(page, size);
	}
	
	@ApiOperation("包红包接口")
	@GetMapping("/setRedPacket")
	public String setRedPacket(double total, int count) {
		return redisExampleService.setRedPacket(total, count);
	}
	
	@ApiOperation("抢红包接口")
	@GetMapping("/robRedPacket")
	public String robRedPacket(String redPacketId, String userId) {
		return redisExampleService.robRedPacket(redPacketId, userId);
	}
	
	@ApiOperation("查看文章浏览量")
	@GetMapping("/viewPV")
	public String viewPV(Integer id) {
		return redisExampleService.viewPV(id);
	}
	
	@ApiOperation("黑名单校验器")
	@GetMapping("/isBlackList")
	public boolean isBlackList(Integer userId) {
		return redisExampleService.isBlackList(userId);
	}
	
	@ApiOperation("抽奖")
	@GetMapping("/prize")
	public String prize() {
		return redisExampleService.prize();
	}
	
	@ApiOperation("天天抽奖")
	@GetMapping("/ttPrize")
	public List<Integer> ttPrize(int num) {
		return redisExampleService.ttPrize(num);
	}
	
	@ApiOperation("随机推荐群")
	@GetMapping("/qunRandom")
	public List<String> qunRandom() {
		return redisExampleService.qunRandom();
	}
	
	@ApiOperation("随机推荐榜单")
	@GetMapping("/ranking")
	public Ranking ranking() {
		return redisExampleService.ranking();
	}
	
	@ApiOperation("点赞功能")
	@GetMapping("/doLike")
	public String doLike(int postId, int userId) {
		return redisExampleService.doLike(postId, userId);
	}
	
	@ApiOperation("取消点赞功能")
	@GetMapping("/undoLike")
	public String undoLike(int postId, int userId) {
		return redisExampleService.undoLike(postId, userId);
	}
	
	@ApiOperation("根据postId，userId查看点赞")
	@GetMapping("/getPostLikeInfo")
	public Map getPostLikeInfo(int postId, int userId) {
		return redisExampleService.getPostLikeInfo(postId, userId);
	}
	
	@ApiOperation("查看点赞明细")
	@GetMapping("/getLikeDetail")
	public Set getLikeDetail(int postId) {
		return redisExampleService.getLikeDetail(postId);
	}
	
	@ApiOperation("排行榜按小时")
	@GetMapping("/getHour")
	public Set getHour() {
		return redisExampleService.getHour();
	}
	
	@ApiOperation("排行榜按天")
	@GetMapping("/getDay")
	public Set getDay() {
		return redisExampleService.getDay();
	}
	
	@ApiOperation("排行榜按周")
	@GetMapping("/getWeek")
	public Set getWeek() {
		return redisExampleService.getWeek();
	}
	
	@ApiOperation("排行榜按月")
	@GetMapping("/getMonth")
	public Set getMonth() {
		return redisExampleService.getMonth();
	}
	
	@ApiOperation("geo功能初始化")
	@GetMapping("/geoInit")
	public void geoInit() {
		redisExampleService.geoInit();
	}
	
	@ApiOperation("geo获取经纬度坐标")
	@GetMapping("/geoPosition")
	public Point geoPosition(String member) {
		return redisExampleService.geoPosition(member);
	}
	
	@ApiOperation("geohash算法生成哈希值")
	@GetMapping("/geoHash")
	public String geoHash(String member) {
		return redisExampleService.geoHash(member);
	}
	
	@ApiOperation("geo获取两个成员之间的距离")
	@GetMapping("/geoDistance")
	public Distance geoDistance(String member1, String member2) {
		return redisExampleService.geoDistance(member1, member2);
	}
	
	@ApiOperation("geo根据经纬度查找附近的内容")
	@GetMapping("/geoRadiusByXY")
	public GeoResults geoRadiusByXY(double x, double y, double distance) {
		return redisExampleService.geoRadiusByXY(x, y, distance);
	}
	
	@ApiOperation("geo根据成员查找附近的内容")
	@GetMapping("/geoRadiusByMember")
	public GeoResults geoRadiusByMember(String member, double distance) {
		return redisExampleService.geoRadiusByMember(member, distance);
	}
}
