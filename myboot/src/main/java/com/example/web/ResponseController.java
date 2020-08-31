package com.example.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Teacher;
import com.example.message.CommonResponse;

import io.swagger.annotations.Api;

@Api(description = "返回值格式测试")
@RestController
@RequestMapping("/response")
public class ResponseController {

	/**
	 * 方法一
	 * @return
	 */
	@GetMapping("/succ")
	public CommonResponse getSucc() {
		return CommonResponse.succ();  //这样写会被其他程序员鄙视
	}
	
	/**
	 * 方法二：返回String
	 * @return
	 */
	@GetMapping("/string")
	public String getString() {
		return "abc";
	}
	
	/**
	 * 方法二：无返回
	 */
	@GetMapping("/empty")
	public void getEmpty() {
		
	}
	
	/**
	 * 方法二：返回对象
	 * @return
	 */
	@GetMapping("/teacher")
	public Teacher getTercher() {
		Teacher teacher = new Teacher();
		teacher.setName("张三");
		teacher.setAge("18");
		teacher.setBeginTime("2020");
		teacher.setEndTime("2021");
		return teacher;
	}
}
