package com.example.sms;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sms.properties")
public class SmsProperties {
	
	private Properties properties = new Properties();

	/**
	 * 超时时间
	 */
	private String timeout;
	
	/**
	 * 重试次数
	 */
	private String retry;

	public String getTimeout() {
		return properties.getProperty("timeout");
	}

	public void setTimeout(String timeout) {
		properties.setProperty("timeout", timeout);
	}

	public String getRetry() {
		return properties.getProperty("retry");
	}

	public void setRetry(String retry) {
		properties.setProperty("retry", retry);
	}
	
}
