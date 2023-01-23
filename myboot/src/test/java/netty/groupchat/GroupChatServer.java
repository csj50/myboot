package netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {

	private int port; //监听端口
	
	GroupChatServer(int port) {
		this.port = port;
	}
	
	//编写run方法，处理客户端的请求
	public void run() {
		
		//创建两个线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(); //默认cpu核数*2
		ServerBootstrap server = new ServerBootstrap();
		
		try {
			server.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//获取到pipeline
					ChannelPipeline pipeline = ch.pipeline();
					//向pipeline加入解码器
					pipeline.addLast("decoder", new StringDecoder());
					//向pipeline加入编码器
					pipeline.addLast("encoder", new StringEncoder());
					//如果不加这个编码解码器的，无法直接传输字符串，应该只能处理ByteBuf而不能直接处理String
					
					//加入自己的业务处理handler
					pipeline.addLast(new GroupChatServerHandler());
					
				}
				
			});
		
			System.out.println("netty 服务器启动......");
			
			//绑定一个端口并且同步，生成了一个ChannelFuture对象
			//启动服务器并绑定端口
			ChannelFuture cf = server.bind(port).sync();
			
			//对关闭事件进行监听
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) {
		
		new GroupChatServer(7000).run();
		
	}
}
