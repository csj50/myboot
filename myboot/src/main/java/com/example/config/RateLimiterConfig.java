package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.util.concurrent.RateLimiter;

@Configuration
public class RateLimiterConfig {

	@Value("${rateLimiter.all}")
	private String rateLimiter1;
	
	@Value("${rateLimiter.resource}")
	private String rateLimiter2;
	
	@Value("${rateLimiter.interface}")
	private String rateLimiter3;
	
	/**
	 * 全局限流器
	 * @return
	 */
	@Bean
	public RateLimiter rateLimiterAll() {
		RateLimiter rateLimiterAll = RateLimiter.create(Double.parseDouble(rateLimiter1));
		return rateLimiterAll;
	}
	
	/**
	 * 针对单个resource限流器
	 * @return
	 */
	@Bean
	public RateLimiter rateLimiterResource() {
		RateLimiter rateLimiterResource = RateLimiter.create(Double.parseDouble(rateLimiter2));
		return rateLimiterResource;
	}
	
	/**
	 * 针对单个接口限流器
	 * @return
	 */
	@Bean
	public RateLimiter rateLimiterIngerface() {
		RateLimiter rateLimiterIngerface = RateLimiter.create(Double.parseDouble(rateLimiter3));
		return rateLimiterIngerface;
	}
}
