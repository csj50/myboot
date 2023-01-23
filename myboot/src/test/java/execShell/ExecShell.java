package execShell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ExecShell {
	public static void main(String[] args) throws IOException {

		try {
			// String cmd = "ping 114.114.114.114"; // 运行的命令
			String cmd = "ping www.baidu.com";
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
			InputStream inputStream = process.getInputStream();
			InputStream errorStream = process.getErrorStream();

			// 处理标准输入流线程
			new Thread() {
				@Override
				public void run() {
					InputStreamReader inputStreamReader = null;
					try {
						inputStreamReader = new InputStreamReader(inputStream, "gbk");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}

					BufferedReader in = new BufferedReader(inputStreamReader);
					String line = null;
					try {
						while ((line = in.readLine()) != null) {
							System.out.println(line);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}.start();

			// 处理标准错误流线程
			new Thread() {
				@Override
				public void run() {
					InputStreamReader inputStreamReader = null;
					try {
						inputStreamReader = new InputStreamReader(errorStream, "gbk");
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}

					BufferedReader err = new BufferedReader(inputStreamReader);
					try {
						String line2 = null;
						while ((line2 = err.readLine()) != null) {
							if (line2 != null) {
								System.out.println(line2);
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							err.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
