package com.example.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.UUID;

/**
 * 红包工具类
 * @author User
 *
 */
public class RedPacketUtil {

	/**
	 * 生成全局唯一id
	 * @return
	 */
	public static String incrementId() {
		String n = UUID.randomUUID().toString();
		return n;
	}
	
	/**
	 * 拆解红包
	 * 1.红包金额要被全部拆解完
	 * 2.红包金额不能差太离谱
	 * @param total
	 * @param count
	 * @return
	 */
	public static Double[] splitRedPacket(double total, int count) {
		double used = 0;
		Double[] arrays = new Double[count];
		Random random = new Random();
		//精确小数点2位
		NumberFormat formatter = new DecimalFormat("#.##");
		//最小金额
		double moneyMin = 0.01;
		
		//判断所有红包都是最小金额
		if (0.01 * count == total) {
			for (int j = 0; j < count; j++) {
				arrays[j] = 0.01;
			}
			return arrays;
		}
		
		//分解红包
		for (int i = 0; i < count; i++) {
			if (i == count - 1) {
				//最后一个红包
				arrays[i] = Double.valueOf(formatter.format(total - used));
			} else {
				//红包随机金额浮动系数
				double avg = (total - used) * 2 / (count - i); //计算系数为：可用金额*2除以剩余红包数
				//random.nextDouble()返回一个大于或等于0.0且小于1.0的随机浮点数
				double temp = moneyMin + random.nextDouble() * avg;
				//格式化为两位小数
				String money = formatter.format(temp);
				arrays[i] = Double.valueOf(money);
			}
			used = used + arrays[i];
		}
		return arrays;
	}
}
