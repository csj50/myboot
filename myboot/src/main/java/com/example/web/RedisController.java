package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.create.entity.TblTeacherInf;
import com.example.service.TeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "redis测试接口")
@RestController
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	TeacherService teacherService;

	@ApiOperation("增加一条记录")
	@GetMapping("/insert")
	public boolean insert(TblTeacherInf teacher) {
		return teacherService.insert(teacher);
	}
	
	@ApiOperation("修改一条记录")
	@GetMapping("/update")
	public boolean update(TblTeacherInf teacher) {
		return teacherService.update(teacher);
	}
	
	@ApiOperation("删除一条记录")
	@GetMapping("/delete")
	public boolean delete(TblTeacherInf teacher) {
		return teacherService.delete(teacher);
	}
	
	@ApiOperation("查找一条记录")
	@GetMapping("/findById")
	public TblTeacherInf findById(Integer id) {
		return teacherService.findById(id);
	}
}
