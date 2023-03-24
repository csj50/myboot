package com.example.utils;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.message.CommonResponse;

@ControllerAdvice(basePackages = "com.example.web")
public class ResponseHandler implements ResponseBodyAdvice<Object> {

	/**
	 * 是否支持advice功能
	 * true=支持，false=不支持
	 */
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	/**
	 * 处理response的具体方法
	 */
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (body instanceof String) {
			//需要返回的类型是String
			return JsonUtil.obj2String(CommonResponse.succ((String)body));
		} else if (body == null) {
			return CommonResponse.succ();
		} else if (body instanceof byte[]) {
			return body; //如果是ResponseEntity<byte[]>，直接返回body，用于文件下载
		} else {
			//将对象转成json串
			return CommonResponse.succ(JsonUtil.obj2String(body));
		}
		//返回失败由抛异常捕获
	}

}
