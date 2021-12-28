package myboot;

import org.jasypt.util.text.BasicTextEncryptor;

public class JasyptTest {
	public static void main(String[] args) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		// 加密所需的salt(盐)
		textEncryptor.setPassword("AD42F6697B035B7580E4FEF93BE20BAD");

		String str1 = "654321"; // 数据库
		String str2 = "123456"; // 邮箱

		System.out.println(textEncryptor.encrypt(str1));
		System.out.println(textEncryptor.encrypt(str2));
	}
}
