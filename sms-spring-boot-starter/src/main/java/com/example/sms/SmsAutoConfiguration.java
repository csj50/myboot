package com.example.sms;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SmsProperties.class)
public class SmsAutoConfiguration {
	
	@Bean
	public MyMessage myMessage() {
		return new MyMessage();
	}
}
