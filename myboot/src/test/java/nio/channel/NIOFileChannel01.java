package nio.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel01 {
	public static void main(String[] args) throws Exception {
		String str = "hello，你好";
		//创建一个输出流->包装到channel中
		FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");
		
		//通过fileOutputStream输出流获取对应的FileChannel
		//这个fileChannel真实类型是FileChannelImpl
		FileChannel fileChannel = fileOutputStream.getChannel();
		
		//创建一个缓冲区ByteBuffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		//将str放入到byteBuffer中
		byteBuffer.put(str.getBytes());
		
		//对byteBuffer进行flip
		byteBuffer.flip();
		
		//将byteBuffer里的数据，写入到fileChannel
		fileChannel.write(byteBuffer);
		
		//关闭流
		fileOutputStream.close();
	}
}
