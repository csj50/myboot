package nio.buffer;

import java.nio.ByteBuffer;

public class ReadOnlyBuffer {
	public static void main(String[] args) {
		//创建一个Buffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		for(int i=0; i<1024; i++) {
			byteBuffer.put((byte)i);
		}
		
		//切换
		byteBuffer.flip();
		
		//得到一个只读的buffer
		ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
		System.out.println(readOnlyBuffer.getClass());
		
		//读取
		while (readOnlyBuffer.hasRemaining()) {
			System.out.println(readOnlyBuffer.get());
		}
		
		//写入
		readOnlyBuffer.put((byte)1);
		
	}
}
