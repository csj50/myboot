package com.example.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.BizErrorCode;
import com.example.exception.BizException;

import io.swagger.annotations.Api;

@Api(description = "返回异常测试")
@RestController
@RequestMapping("/error")
public class ExceptionController {

	@GetMapping("/runtimeException")
	public void runtimeException() {
		int i = 9/0;
	}
	
	@GetMapping("/exception")
	public void exception() throws Exception {
		throw new Exception("非法数据");
	}
	
	@GetMapping("/bizException")
	public void bizException() {
		throw new BizException(BizErrorCode.E999998);
	}
}
