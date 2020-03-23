package com.example.config;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContextListener;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

	@Bean
	public ServletRegistrationBean<Servlet> myServlet() {
		ServletRegistrationBean<Servlet> servlet = new ServletRegistrationBean<>(new MyServlet2());
		servlet.addUrlMappings("/servlet2");
		servlet.setName("MyServlet2");
		return servlet;
	}

	@Bean
	public FilterRegistrationBean<Filter> myFilter() {
		FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>(new MyFilter2());
		filter.addUrlPatterns("/*");
		filter.setOrder(2); // 设置注册bean的顺序
		filter.setName("MyFilter2");
		return filter;
	}

	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> myListener() {
		ServletListenerRegistrationBean<ServletContextListener> listener = new ServletListenerRegistrationBean<>(
				new MyListener2());
		return listener;
	}
}
