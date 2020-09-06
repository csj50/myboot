package com.example.domain;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.example.annotation.Phone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("实体对象")
@Data
public class Teacher {
	
	@ApiModelProperty(value = "姓名")
	@NotEmpty(message = "用户名不能为空")
	@Length(min = 2, max = 12, message = "长度必须位于2到12之间")
	private String name;
	
	@ApiModelProperty(value = "年龄")
	@NotEmpty(message = "年龄不能为空")
	@Range(min = 20, max = 65, message = "年龄范围必须在20到65之间")
	private String age;
	
	@ApiModelProperty(value = "起始时间")
	private String beginTime;
	
	@ApiModelProperty(value = "结束时间")
	private String endTime;
	
	@ApiModelProperty(value = "手机号")
	@Phone
	private String phone;
}
