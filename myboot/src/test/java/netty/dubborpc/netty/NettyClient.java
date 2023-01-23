package netty.dubborpc.netty;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {

	//创建线程池
	private static ExecutorService executor = Executors.newFixedThreadPool(10);
	
	private static NettyClientHandler client;
	
	//编写方法，使用代理模式获取代理对象
	public Object getBean(final Class<?> serviceClass, final String protocolHeader) {
		
		return Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(), 
				new Class<?>[] {serviceClass}, 
				//lambel表达式就是实现的接口InvocationHangler的invoke方法
				(proxy, method, args) -> {
					
					//这部分代码，客户端每调用一次hello，就会进入该代码块
					
					if (client == null) {
						initClient();
						initConnect();
					}
					
					//设置要发给服务器的信息
					//protocolHeader协议头，args[0]就是客户端调用api hello()里传的参数
					client.setParam(protocolHeader + args[0]);
					
					return executor.submit(client).get();
				});
	}
	
	//初始化客户端
	private static void initClient() {
		client = new NettyClientHandler();
	}
	
	//初始化连接
	private static void initConnect() {
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group) //设置线程组
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new LoggingHandler(LogLevel.DEBUG))
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new StringDecoder());
						pipeline.addLast(new StringEncoder());
						pipeline.addLast(client);
					}
				}); //自定义一个初始化对象
			
			ChannelFuture cf = bootstrap.connect("127.0.0.1", 7000).sync();
			
			//这里不能阻塞必须返回，因为后续代理还要调用call方法，所以不能closeFuture sync，但可以对closeFuture加一个listener回调
			//cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//这里也不能shutdown
			//group.shutdownGracefully();
		}
	}
}
