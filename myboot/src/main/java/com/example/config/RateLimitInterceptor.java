package com.example.config;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimitInterceptor implements HandlerInterceptor {

	protected static Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);
	
	@Autowired
	RateLimiter rateLimiterAll;
	
	@Autowired
	RateLimiter rateLimiterResource;
	
	@Autowired
	RateLimiter rateLimiterIngerface;
    
	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//尝试获取一个令牌，带超时时间，超时时间单位为毫秒
		if (!rateLimiterAll.tryAcquire(100, TimeUnit.MICROSECONDS)) {
			logger.info("全局限流器限流中...");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			response.getWriter().write("{\"data\":\"访问次数受限\",\"sign\":null,\"repCode\":\"999999\",\"repMsg\":\"失败\"}");
			response.getWriter().flush();
			response.getWriter().close();
			return false;
		} else {
			logger.info("全局限流器通过...");
		}
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
