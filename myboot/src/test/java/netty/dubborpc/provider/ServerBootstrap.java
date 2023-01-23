package netty.dubborpc.provider;

import netty.dubborpc.netty.NettyServer;

/**
 * ServerBootstrap会启动一个服务提供者，就是NettyServer
 * @author user
 *
 */
public class ServerBootstrap {
	public static void main(String[] args) {
		NettyServer.startServer("127.0.0.1", 7000);
	}
}
