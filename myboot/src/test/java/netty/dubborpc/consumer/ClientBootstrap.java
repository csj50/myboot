package netty.dubborpc.consumer;

import netty.dubborpc.netty.NettyClient;
import netty.dubborpc.publicinterface.HelloService;

public class ClientBootstrap {
	
	//这里定义协议头
	public static final String providerName = "HelloService#hello#";
	
	public static void main(String[] args) {
		//创建一个消费者
		NettyClient consumer = new NettyClient();
		
		for (int i=0; i<10; i++) {
			//创建代理对象
			HelloService helloService = (HelloService)consumer.getBean(HelloService.class, providerName);
			
			//通过代理对象调用服务提供者的方法
			String result = helloService.hello("你好 RPC~");
			System.out.println("调用结果 result：" + result);
			System.out.println("-------------------------");
		}
		
	}
}
