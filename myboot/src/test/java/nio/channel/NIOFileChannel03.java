package nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel03 {
	public static void main(String[] args) throws Exception {
		//创建文件的输入流
		FileInputStream fileInputStream = new FileInputStream("d:\\file01.txt");
		//获取输入流对象的channel
		FileChannel fileChannel01 = fileInputStream.getChannel();
		
		//文件输出流对象
		FileOutputStream fileOutputStream = new FileOutputStream("d:\\file02.txt");
		//获取输入流对象的channel
		FileChannel fileChannel02 = fileOutputStream.getChannel();
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		
		while(true) {
			//读之前有个重要操作，一定不要忘了
			byteBuffer.clear(); //复位：The position is set to zero, the limit is set to the capacity, and the mark is discarded
			
			//循环读取
			int read = fileChannel01.read(byteBuffer);
			System.out.println("read = " + read);
			if (read == -1) {
				//表示读完
				break;
			}
			
			//读写切换
			byteBuffer.flip();
			
			//将buffer中的数据写入到fileChannel02
			fileChannel02.write(byteBuffer);
		}
		
		//关闭相关的流
		fileInputStream.close();
		fileOutputStream.close();
	}
}
