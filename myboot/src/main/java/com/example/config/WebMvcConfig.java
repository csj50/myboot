package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 * 
 * @author User
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	// 在此处，将拦截器注册为一个Bean，才能使用@Autowired注解注入对象
	@Bean
	public RateLimitInterceptor rateLimitInterceptor() {
		return new RateLimitInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 注册RateLimitInterceptor拦截器
		// 要注意这里使用this.rateLimitInterceptor()
		InterceptorRegistration registration = registry.addInterceptor(this.rateLimitInterceptor());
		// 所有路径都被拦截
		registration.addPathPatterns("/**")
				// 排除拦截swagger页面
				.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
						"doc.html", "/error")
				// 排除拦截webapp目录下的jsp和html页面
				.excludePathPatterns("/*.jsp", "/*.html")
				// 排除拦截druid
				.excludePathPatterns("/druid/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html", "doc.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

	}

}
