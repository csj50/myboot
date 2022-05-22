package com.example.web;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "rabbitmq测试接口")
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Value("${mq.config.exchange}")
	private String exchange;
	
	@Value("${mq.config.exchange2}")
	private String exchange2;
	
	@Value("${mq.config.exchange3}")
	private String exchange3;
	
	@ApiOperation("发送一条记录")
	@GetMapping("/send")
	public void send() {
		String msg = "hello" + new Date();
		//第一个参数是queue名称，第二个参数是msg
		rabbitTemplate.convertAndSend("testQueue", msg);
	}
	
	@ApiOperation("direct交换器测试")
	@GetMapping("/direct")
	public void direct() {
		String msg = "hello" + new Date();
		//第一个参数是交换器，第二个参数是路由键，第三个参数是msg
		rabbitTemplate.convertAndSend(exchange, "log.info.routing.key", msg);
		rabbitTemplate.convertAndSend(exchange, "log.error.routing.key", msg);
	}
	
	@ApiOperation("topic交换器测试")
	@GetMapping("/topic")
	public void topic() {
		//第一个参数是交换器，第二个参数是路由键，第三个参数是msg
		rabbitTemplate.convertAndSend(exchange2, "user.log.info", "user info......");
		rabbitTemplate.convertAndSend(exchange2, "user.log.debug", "user debug......");
		rabbitTemplate.convertAndSend(exchange2, "user.log.warn", "user warn......");
		rabbitTemplate.convertAndSend(exchange2, "user.log.error", "user error......");
		
		rabbitTemplate.convertAndSend(exchange2, "product.log.info", "product info......");
		rabbitTemplate.convertAndSend(exchange2, "product.log.debug", "product debug......");
		rabbitTemplate.convertAndSend(exchange2, "product.log.warn", "product warn......");
		rabbitTemplate.convertAndSend(exchange2, "product.log.error", "product error......");
		
		rabbitTemplate.convertAndSend(exchange2, "order.log.info", "order info......");
		rabbitTemplate.convertAndSend(exchange2, "order.log.debug", "order debug......");
		rabbitTemplate.convertAndSend(exchange2, "order.log.warn", "order warn......");
		rabbitTemplate.convertAndSend(exchange2, "order.log.error", "order error......");
	}
	
	@ApiOperation("fanout交换器测试")
	@GetMapping("/fanout")
	public void fanout() {
		String msg = "hello" + new Date();
		rabbitTemplate.convertAndSend(exchange3, "", msg);
	}
}
