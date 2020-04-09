package com.example.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.example.domain.Student;

@Configuration
public class MybootConfig {

	@Bean
	public Student stu1() {
		Student stu = new Student();
		stu.setName("校长");
		stu.setAge(28);
		return stu;
	}
	
	@Bean
	public Student stu2() {
		Student stu = new Student();
		stu.setName("小李");
		stu.setAge(18);
		return stu;
	}
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		// 避免中文乱码，将字符集从ISO-8859-1改为UTF_8
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;
	}
}
