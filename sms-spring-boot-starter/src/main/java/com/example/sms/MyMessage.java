package com.example.sms;

/**
 * 模拟发送短信功能
 * @author user
 *
 */
public class MyMessage {

	public void send(String phone, String msg) {
		System.out.println("send to: " + phone + ", msg: " + msg);
	}
}
