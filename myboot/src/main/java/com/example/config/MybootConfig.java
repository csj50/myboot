package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
