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
		value = @Queue(value = "${mq.config.queue.info}", autoDelete = "true"),
		//绑定一个交换器
		exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.DIRECT),
		//配置路由键
		key = "${mq.config.queue.routing.key.info}"
		)
		)
public class RabbitDirectInfoService {

	@RabbitHandler
	public void process(String msg) {
		System.out.println("接收到INFO日志:" + msg);
	}
}
