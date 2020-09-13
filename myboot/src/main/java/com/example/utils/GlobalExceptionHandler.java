package com.example.utils;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.exception.BizException;
import com.example.message.CommonResponse;

@ControllerAdvice(basePackages = "com.example.web")
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * 运行时异常
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public CommonResponse runtimeException(RuntimeException e) {
		return CommonResponse.fail("999999", e.getMessage());
	}
	
	/**
	 * exception异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public CommonResponse exception(Exception e) {
		return CommonResponse.fail("999999", e.getMessage());
	}
	
	/**
	 * 业务异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BizException.class)
	public CommonResponse bizException(BizException e) {
		return CommonResponse.fail(e.getCode(), e.getMessage());
	}
	
	/**
	 * Valid抛的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public CommonResponse bindException(BindException  e) {
		BindingResult bindingResult = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        for(FieldError fieldError : bindingResult.getFieldErrors()){
            sb.append(fieldError.getDefaultMessage());
        }
		return CommonResponse.fail("999999", sb.toString());
	}
	
	/**
	 * Assert抛的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public CommonResponse illegalArgumentException(IllegalArgumentException e) {
		return CommonResponse.fail("999999", e.getMessage());
	}
}
