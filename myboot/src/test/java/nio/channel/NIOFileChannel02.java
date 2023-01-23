package nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel02 {
	public static void main(String[] args) throws Exception {
		//创建文件的输入流
		File file = new File("d:\\file01.txt");
		FileInputStream fileInputStream = new FileInputStream(file);
		
		//通过fileInputStream获取对应的FileChannel -> 实际类型FileChannelImpl
		FileChannel fileChannel = fileInputStream.getChannel();
		
		//创建缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		//将通道的数据读入到byteBuffer中
		fileChannel.read(byteBuffer);
		
		//将byteBuffer的字节数据转成String
		System.out.println(new String(byteBuffer.array())); //返回buffer中的字节数组hb
		
		//关闭流
		fileInputStream.close();
	}
}
