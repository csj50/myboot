package com.example.annotation;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class MyCrossOriginAop {

	@Before(value = "@annotation(com.example.annotation.MyCrossOrigin)")
	public void before() {
		//如果接口上有加上该注解MyCrossOrigin，自动走前置通知的代码
		System.out.println("----------前置通知----------");
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setHeader("Access-Control-Allow-Origin", "*");
	}
}
