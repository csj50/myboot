package com.example.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "session一致性")
@RestController
@RequestMapping("/redisSession")
public class RedisSessionController {

	@ApiOperation("设置sessionId")
	@GetMapping("/setSessionId")
	public void setSession(HttpServletRequest request, HttpServletResponse response) {		
		//从请求中提取HttpSession对象
		HttpSession session = request.getSession();
		session.setAttribute("msg", "方法调用");
	}
	
	@ApiOperation("获取session属性")
	@GetMapping("/getSessionAttr")
	public String getSessionAttr(HttpServletRequest request, HttpServletResponse response) {
		//从请求中提取HttpSession对象
		HttpSession session = request.getSession();
		return (String)session.getAttribute("msg");
	}
}
