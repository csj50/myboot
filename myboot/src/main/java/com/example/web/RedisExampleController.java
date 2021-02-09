package com.example.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.create.entity.TblTeacherInf;
import com.example.domain.Product;
import com.example.service.RedisExampleService;

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
}
