package nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {
	public static void main(String[] args) throws Exception {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("localhost", 7001));
		String fileName = "d:\\aaa.zip";
		//得到一个文件的channel
		FileChannel fileChannel = new FileInputStream(fileName).getChannel();
		//准备发送
		long startTime = System.currentTimeMillis();
		
		//在linux下一个transferTo方法可以完成传输
		//在windows下，一次调用transferTo只能发送8MB的文件，就需要分段传输文件，而且要注意传输时的位置
		//transferTo底层使用到零拷贝
		
		long start = 0; //起始位置
		long size = fileChannel.size(); //文件大小
		long transCount = 0; //传输了多少
		long sum = 0;
		
		while (true) {
			transCount = fileChannel.transferTo(start, size, socketChannel);
			sum += transCount;
			if (transCount < size) {
				start += transCount;
				size -= transCount;
			} else {
				break;
			}
		}
		
		//long transCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
		System.out.println("发送总字节数：" + sum + "，耗时：" + (System.currentTimeMillis() - startTime));
		
		//关闭
		fileChannel.close();
		socketChannel.close();
	}
}
