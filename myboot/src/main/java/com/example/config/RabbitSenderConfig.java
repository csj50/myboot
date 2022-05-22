package com.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitSenderConfig {

//	@Bean
//	public Queue queue() {
//		//有四个参数
//		//name: 队列名称
//		//durable: 是否持久化，默认true
//		//exclusive: 该队列只能被创建者使用，默认false
//		//autoDelete: 长时间不用队列会被自动删除，默认false
//		return new Queue("testQueue");
//	}
	
}
