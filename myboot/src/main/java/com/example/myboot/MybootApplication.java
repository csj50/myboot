package com.example.myboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan(basePackages = {"com.example"})  // 指定扫描路径
@ComponentScan(basePackages = {"com.example"})  // 指定扫描路径
@SpringBootApplication
public class MybootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybootApplication.class, args);
	}
}
