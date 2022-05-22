package com.example.rabbit;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings = @QueueBinding(
		//绑定一个队列
		value = @Queue(value = "${mq.config.queue.logs}", autoDelete = "true"),
		//绑定一个交换器
		exchange = @Exchange(value = "${mq.config.exchange2}", type = ExchangeTypes.TOPIC),
		//配置路由键
		key = "*.log.*"
		)
		)
public class RabbitTopicLogsService {
	
	@RabbitHandler
	public void process(String msg) {
		System.out.println("all----------日志:" + msg);
	}
}
