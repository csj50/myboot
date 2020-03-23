package com.example.config;

import java.time.LocalDateTime;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyListener1 implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("MyListener1 init");
		System.out.println("app startup at " + LocalDateTime.now().toString());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("MyListener1 destroy");
		System.out.println("app stop at " + LocalDateTime.now().toString());
	}

}
