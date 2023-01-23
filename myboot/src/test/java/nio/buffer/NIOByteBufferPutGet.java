package nio.buffer;

import java.nio.ByteBuffer;

public class NIOByteBufferPutGet {
	public static void main(String[] args) {
		//创建一个Buffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		//类型化方式放入数据
		byteBuffer.putInt(10);
		byteBuffer.putLong(9L);
		byteBuffer.putChar('上');
		byteBuffer.putShort((short)1);
		
		//取出
		byteBuffer.flip();
		
		System.out.println(byteBuffer.getInt());
		System.out.println(byteBuffer.getLong());
		System.out.println(byteBuffer.getChar());
		System.out.println(byteBuffer.getLong());
	}
}
