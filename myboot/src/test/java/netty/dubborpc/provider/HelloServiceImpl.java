package netty.dubborpc.provider;

import netty.dubborpc.publicinterface.HelloService;

public class HelloServiceImpl implements HelloService {
	
	private int count = 0;

	@Override
	public String hello(String msg) {
		System.out.println("收到客户端消息：" + msg);
		//根据msg返回不同的结果
		if (msg != null) {
			return "你好客户端，我已经收到你的消息 [" + msg + "] 第" + (++count) + "次";
		} else {
			return "你好客户端，消息为空";
		}
	}

}
