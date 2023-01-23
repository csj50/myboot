package nio.buffer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Arrays;

public class ScatteringAndGateringTest {
	public static void main(String[] args) throws Exception {
		//使用ServerSocketChannel和SocketChannel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//创建server的address
		InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
		//绑定端口到socket，并启动
		serverSocketChannel.socket().bind(inetSocketAddress);
		
		//创建buffer数组
		ByteBuffer[] byteBuffers = new ByteBuffer[2];
		byteBuffers[0] = ByteBuffer.allocate(5); //第一个分配5个字节
		byteBuffers[1] = ByteBuffer.allocate(3); //第二个分配3个字节
		
		//等待客户端连接telnet
		SocketChannel socketChannel = serverSocketChannel.accept();
		
		//假定从客户端接收8个字节
		int messageLength = 8;
		
		//循环的读取
		while (true) {
			//统计读了多少个字节
			int byteRead = 0;
			
			while (byteRead < messageLength) {
				long l = socketChannel.read(byteBuffers); //返回读取到的个数，会自动处理数组
				byteRead += l; //累计读取到的字节数
				System.out.println("byteRead=" + byteRead);
				//使用流打印，看看当前的buffer的position和limit
				Arrays.asList(byteBuffers).stream().map(buffer -> "position=" + buffer.position()
				+ ", limit=" + buffer.limit()).forEach(System.out::println);
			}
			
			//将所有的buffer进行flip
			//Arrays.asList(byteBuffers).stream().map(buffer -> buffer.flip());
			Arrays.asList(byteBuffers).stream().forEach(buffer -> buffer.flip()); //注意stream().map遍历不会改变原来的值
			
			//将数据读出显示到客户端
			long byteWrite = 0;
			while (byteWrite < messageLength) {
				long l = socketChannel.write(byteBuffers);
				byteWrite += l;
			}
			
			//将所有的buffer进行clear
			Arrays.asList(byteBuffers).stream().forEach(buffer -> buffer.clear());
			
			System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite 
					+ ", messageLength=" + messageLength);
			
		}
	}
}
