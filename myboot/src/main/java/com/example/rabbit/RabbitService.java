package com.example.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitService {

	@RabbitListener(queues = "testQueue") //监听队列名称
	public void process(String msg) {
		System.out.println("receiver:" + msg);
	}
}
