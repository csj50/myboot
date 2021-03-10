package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类
 * @author User
 *
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	//在此处，将拦截器注册为一个Bean，才能使用@Autowired注解注入对象
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
		registration.addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
