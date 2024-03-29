package netty.http;

import java.net.URI;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

//SimpleChannelInboundHandler继承了ChannelInboundHandlerAdapter
//HttpObject指定了客户端和服务器端，在处理的时候的数据类型
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

	//channelRead0：读取客户端数据
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		
		//判断msg是不是HttpRequest请求
		if (msg instanceof HttpRequest) {
			System.out.println("msg 类型 = " + msg.getClass());
			System.out.println("客户端地址 = " + ctx.channel().remoteAddress());
			
			//过滤信息
			HttpRequest httpRequest = (HttpRequest) msg;
			//获取uri
			URI uri = new URI(httpRequest.uri());
			if ("/favicon.ico".equals(uri.getPath())) {
				System.out.println("请求了favicon.ico，不做响应");
				return;
			}
			
			//回复信息给浏览器 [http协议]
			ByteBuf content = Unpooled.copiedBuffer("hello，我是服务器！", CharsetUtil.UTF_8);
			//构造一个http的响应，即httpResponse
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
			
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=utf-8");
			response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
			
			//将构建好的response返回
			ctx.writeAndFlush(response);
			
		}
	}

}
