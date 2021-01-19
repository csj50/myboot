package com.example.utils;

import org.springframework.util.DigestUtils;

public class ShortUrlGenerator {

	public static String[] shortUrl(String url) {
		// 可以自定义生成MD5加密字符传前的混合KEY
		String key = "";
		// 要使用生成URL的字符
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
				"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z" };

		// 对传入网址进行MD5加密
		String input = key + url;
		String sMD5EncryptResult = DigestUtils.md5DigestAsHex(input.getBytes());
		String hex = sMD5EncryptResult;
		String[] resUrl = new String[4];
		for (int i = 0; i < 4; i++) {
			// 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
			String sTempSubString = hex.substring(i * 8, i * 8 + 8);
			// 这里需要使用long型来转换，因为Inteper.parseInt()只能处理31位, 首位为符号位, 如果不用long，则会越界
			// 格式化截取串的前30位
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
			String outChars = "";
			for (int j = 0; j < 6; j++) {
				// 把得到的值与0x0000003D进行位与运算，取得字符数组chars索引
				// 0x0000003D是10进制的61，61代表chars数组长度61的0到61下标
				// 与操作保证index是61以内的值
				long index = 0x0000003D & lHexLong;
				// 把取得的字符相加
				outChars += chars[(int) index];
				// 每次循环按位右移5位
				// 因为30位的二进制，分6次循环，即每次右移5位
				lHexLong = lHexLong >> 5;
			}

			// 把字符串存入对应索引的输出数组
			resUrl[i] = outChars;
		}
		return resUrl;
	}

	public static void main(String[] args) {
		String sLongUrl = "http://www.baidu.com";
		String[] aResult = shortUrl(sLongUrl);
		// 打印出结果
		for (int i = 0; i < aResult.length; i++) {
			System.out.println(aResult[i]);
		}
	}
}
