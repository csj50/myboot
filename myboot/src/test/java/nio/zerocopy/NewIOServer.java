package nio.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
	public static void main(String[] args) throws Exception {
		SocketAddress socketAddress = new InetSocketAddress(7001);
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		
		ServerSocket serverSocket = serverSocketChannel.socket();
		
		serverSocket.bind(socketAddress);
		
		//创建buffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
		
		while(true) {
			SocketChannel socketChannel = serverSocketChannel.accept();
			
			int readCount = 0;
			
			while(readCount != -1) {
				try {
					//从socketChannel读取数据到byteBuffer
					readCount = socketChannel.read(byteBuffer);
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				
				//将buffer倒带
				byteBuffer.rewind();
			}
		}
	}
}
