package com.example.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("实体对象")
@Data
public class Teacher {
	
	@ApiModelProperty(value = "姓名")
	private String name;
	
	@ApiModelProperty(value = "年龄")
	private String age;
	
	@ApiModelProperty(value = "起始时间")
	private String beginTime;
	
	@ApiModelProperty(value = "结束时间")
	private String endTime;
}
