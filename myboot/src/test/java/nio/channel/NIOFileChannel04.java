package nio.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {
	public static void main(String[] args) throws Exception {
		//创建输入流
		FileInputStream fileInputStream = new FileInputStream("d:\\a.jpg");
		//创建输出流
		FileOutputStream fileOutputStream = new FileOutputStream("d:\\a2.jpg");
		
		//获取各个流对应的fileChannel
		FileChannel source = fileInputStream.getChannel();
		FileChannel dest = fileOutputStream.getChannel();
		
		//使用transferFrom完成拷贝
		dest.transferFrom(source, 0, source.size());
		
		//关闭通道和流
		source.close();
		dest.close();
		fileInputStream.close();
		fileOutputStream.close();
	}
}
