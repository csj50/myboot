package netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.dubborpc.provider.HelloServiceImpl;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.channel().close();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//获取客户端发送的消息，并调用服务
		System.out.println("msg=" + msg);
		
		//客户端在调用服务器的api时，我们需要定义一个协议
		//比如我们要求，每次发消息时，都必须以某个字符串开头 "HelloService#hello#"
		if (msg.toString().startsWith("HelloService#hello#")) {
			//去除协议头
			//这里可以用反射生成处理类
			//还要考虑粘包拆包问题
			String result = new HelloServiceImpl().hello(msg.toString().substring(19));
			ctx.writeAndFlush(result);
		}
		
	}
}
