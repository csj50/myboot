package com.example.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("通用返回对象")
@Data
public class CommonResponse {
	
	@ApiModelProperty(value = "数据")
	private String data; //数据
	
	@ApiModelProperty(value = "签名")
	private String sign; //签名
	
	@ApiModelProperty(value = "应答码")
	private String repCode; //应答码
	
	@ApiModelProperty(value = "应答信息")
	private String repMsg; //应答信息
	
	public static CommonResponse succ() {
		CommonResponse response = new CommonResponse();
		response.setRepCode("000000");
		response.setRepMsg("成功");
		return response;
	}

	public static CommonResponse succ(String data) {
		CommonResponse response = new CommonResponse();
		response.setData(data);
		response.setRepCode("000000");
		response.setRepMsg("成功");
		return response;	
	}
	
	public static CommonResponse fail(String repCode, String repMsg) {
		CommonResponse response = new CommonResponse();
		response.setRepCode(repCode);
		response.setRepMsg(repMsg);
		return response;
	}
	
}
