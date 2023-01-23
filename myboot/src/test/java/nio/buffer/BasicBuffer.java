package nio.buffer;

import java.nio.IntBuffer;

public class BasicBuffer {
	public static void main(String[] args) {
		//举例说明buffer的使用（简单说明）
		
		//创建一个Buffer，大小为5，即可以存放5个int
		IntBuffer intBuffer = IntBuffer.allocate(5);
		
		//向Buffer存放数据
		//intBuffer.capacity：buffer的容量
		for (int i=0; i < intBuffer.capacity(); i++) {
			intBuffer.put(i * 2);
		}
		
		//如何从buffer读取数据
		//将buffer转换，读写切换
		intBuffer.flip();
		
		
		while(intBuffer.hasRemaining()) {  //是否有未读数据
			System.out.println(intBuffer.get());  //通过buffer的index获取数据
		}
	}
}
