package com.example.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.exception.BizException;
import com.example.message.CommonResponse;

@ControllerAdvice(basePackages = "com.example.web")
@ResponseBody
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public CommonResponse runtimeException(RuntimeException e) {
		return CommonResponse.fail("999999", e.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public CommonResponse exception(Exception e) {
		return CommonResponse.fail("999999", e.getMessage());
	}
	
	@ExceptionHandler(BizException.class)
	public CommonResponse bizException(BizException e) {
		return CommonResponse.fail(e.getCode(), e.getMessage());
	}
}
