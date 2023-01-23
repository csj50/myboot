package nio.buffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {
	public static void main(String[] args) throws Exception {
		
		//修改文件
		RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\file01.txt", "rw");
		//获取对应的通道
		FileChannel fileChannel = randomAccessFile.getChannel();
		
		/**
		 * MapMode mode：使用的模式
		 * long position：可以直接修改的起始位置
		 * long size：是映射到内存的大小（最多可以映射多少大小），即将文件file01.txt的多少个字节映射到内存
		 * 可以直接修改的范围就是0-5（不包含5）
		 * 实际类型是DirectByteBuffer
		 */
		MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
		mappedByteBuffer.put(0, (byte) 'H');
		mappedByteBuffer.put(3, (byte) '9');
		
		//关闭文件
		randomAccessFile.close();
		
	}
}
