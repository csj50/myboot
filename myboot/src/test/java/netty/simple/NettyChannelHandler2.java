package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 说明
 * 1. 我们自定义一个Handler，需要继承netty规定好的某个HandlerAdapter（规范）
 * 2. 这时我们自定义一个Handler，才能称之为Handler
 * 
 */
public class NettyChannelHandler2 extends ChannelInboundHandlerAdapter {

	//读取数据的事件（这里我们可以读取客户端发送的消息）
	/*
	 * 1. ChannelHandlerContext ctx：上下文对象，含有管道pipeline，通道channel，地址
	 * 2. Object msg：就是客户端发送的数据，默认是Object
	 * 3. 通道读写数据，管道处理数据
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		//方案1：用户程序自定义的普通任务
		//会提交到当前channel关联的NioEventLoop里面的taskQueue执行
		//任务一
		ctx.channel().eventLoop().execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10 * 1000);
					ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello，客户端~", CharsetUtil.UTF_8));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		//任务二
		ctx.channel().eventLoop().execute(new Runnable() {

			@Override
			public void run() {
				System.out.println("任务二...");
				ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello，客户端3~", CharsetUtil.UTF_8));
				
			}
			
		});
		
		ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello，客户端2~", CharsetUtil.UTF_8));
		
		//客户端
		//会收到：hello，客户端2~
		//再收到：hello，客户端~
		//再收到：hello，客户端3~
		
	}
	
	//数据读取完毕
	//这个方法会在channelRead读完后触发
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//把数据写到缓冲区，并且刷新缓冲区，是write + flush
		//一般来讲，我们对这个发送的数据进行编码
		//ctx.channel().writeAndFlush(Unpooled.copiedBuffer("hello，客户端~", CharsetUtil.UTF_8));
		
	}
	
	//处理异常，一般是需要关闭通道
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
	}
}
