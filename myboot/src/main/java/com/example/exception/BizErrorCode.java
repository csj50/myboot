package com.example.exception;

import java.text.MessageFormat;

public enum BizErrorCode implements ErrorCode {

	E000000("000000", "成功"), 
	E999998("999998", "请求过于频繁"), 
	E999999("999999", "失败");

	private String code;
	private String msg;

	BizErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static BizErrorCode getErrorCode(int code) {
		BizErrorCode[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			BizErrorCode ele = var1[var3];
			if (ele.code().equals(code)) {
				return ele;
			}
		}

		return null;
	}

	/**
	 * get msg
	 *
	 * @return
	 */
	@Override
	public String msg() {
		return msg;
	}

	/**
	 * get code
	 *
	 * @return
	 */
	@Override
	public String code() {
		return code;
	}

	public String msg(Object... args) {
		return MessageFormat.format(this.msg(), args);
	}
}
