package com.example.exception;

import java.text.MessageFormat;

public class BizException extends RuntimeException {

	private static final long serialVersionUID = -4032733694182140456L;

	/**
	 * 异常码
	 */
	private String code;
	private Object obj;

	public BizException() {
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BizException(String code, String msgFormat, Object... args) {
		super(MessageFormat.format(msgFormat, args));
		this.code = code;
	}

	public BizException(Boolean isResult, String code, String msgFormat, Object obj, String... args) {
		super(MessageFormat.format(msgFormat, (Object) args));
		this.code = code;
		this.obj = obj;
	}

	public BizException(ErrorCode errorCode, Object... args) {
		super(MessageFormat.format(errorCode.msg(), args));
		this.code = errorCode.code();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
