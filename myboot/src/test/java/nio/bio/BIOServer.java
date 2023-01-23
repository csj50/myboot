package nio.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
	public static void main(String[] args) throws Exception {
		// 线程池机制

		// 思路
		// 1. 创建一个线程池
		// 2. 如果有客户端连接了，就创建一个线程，与之通讯（单独写一个方法）

		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

		// 创建一个ServerSocket
		ServerSocket serverSocket = new ServerSocket(6666);

		System.out.println("服务器启动了");

		while (true) {
			// 监听，等待客户端连接
			final Socket socket = serverSocket.accept(); // 这里会阻塞
			System.out.println("连接到一个客户端了");

			// 启动一个线程
			newCachedThreadPool.execute(() -> {
				// run方法
				handler(socket);
			});
		}
	}

	// 编写一个handler方法，和客户端通讯
	public static void handler(Socket socket) {
		try {
			System.out.println("线程信息 id=" + Thread.currentThread().getId() + " 名字 name=" + Thread.currentThread().getName());
			byte[] bytes = new byte[1024];
			// 通过socket获取输入流
			InputStream inputStream = socket.getInputStream();
			// 循环读取客户端发送的数据
			while (true) {
				int read = inputStream.read(bytes); //// 这里会阻塞
				if (read != -1) {
					// 输出客户端发送的数据
					System.out.println(new String(bytes, 0, read));
				} else {
					// 读取完毕
					break;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				// 关闭和client的连接
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
