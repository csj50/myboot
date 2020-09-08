package com.example.web;

import javax.validation.Valid;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Teacher;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "教师接口")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

	@ApiOperation("测试方法一")
	@GetMapping("/create")
	public Teacher create() {
		Teacher teacher = new Teacher();
		teacher.setName("张三");
		teacher.setAge("18");
		teacher.setBeginTime("20200101");
		teacher.setEndTime("20201231");
		return teacher;
	}
	
	@ApiOperation("测试方法二")
	@PostMapping("/update")
	public boolean update(@Valid Teacher request) {
		//do something
		return true;
	}
	
	@ApiOperation("测试断言")
	@GetMapping("/testAssert")
	public void testAssert() {
		Teacher teacher = null;
		Assert.notNull(teacher, "对象不能为空");
	}
}
