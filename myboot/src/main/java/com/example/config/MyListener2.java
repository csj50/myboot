package com.example.config;

import java.time.LocalDateTime;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener2 implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("MyListener2 init");
		System.out.println("app startup at " + LocalDateTime.now().toString());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("MyListener2 destroy");
		System.out.println("app stop at " + LocalDateTime.now().toString());
	}

}
