package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioClientConfig {
	
	@Value("${minio.url}")
	private String url;
	
	@Value("${minio.access-key}")
	private String accessKey;
	
	@Value("${minio.secret-key}")
	private String secretKey;

	/**
	 * 注入minio客户端
	 */
	@Bean
	public MinioClient minioClient() {

		return MinioClient.builder()
				.endpoint(url)
				.credentials(accessKey, secretKey)
				.build();
	}
}
