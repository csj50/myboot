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
		value = @Queue(value = "${mq.config.queue.push}", autoDelete = "true"),
		//绑定一个交换器
		exchange = @Exchange(value = "${mq.config.exchange3}", type = ExchangeTypes.FANOUT)
		)
		)
public class RabbitFanoutSmsInfoService {

	@RabbitHandler
	public void process(String msg) {
		System.out.println("推送处理:" + msg);
	}
}
