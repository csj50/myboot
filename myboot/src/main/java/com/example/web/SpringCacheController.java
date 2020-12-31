package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.create.entity.TblTeacherInf;
import com.example.service.SpringCacheService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "springcache测试接口")
@RestController
@RequestMapping("/springcache")
public class SpringCacheController {

	@Autowired
	SpringCacheService springCacheService;
	
	@ApiOperation("增加一条记录")
	@GetMapping("/insert")
	public TblTeacherInf insert(TblTeacherInf teacher) {
		return springCacheService.insert(teacher);
	}
	
	@ApiOperation("修改一条记录")
	@GetMapping("/update")
	public TblTeacherInf update(TblTeacherInf teacher) {
		return springCacheService.update(teacher);
	}
	
	@ApiOperation("删除一条记录")
	@GetMapping("/delete")
	public boolean delete(TblTeacherInf teacher) {
		return springCacheService.delete(teacher);
	}
	
	@ApiOperation("查找一条记录")
	@GetMapping("/findById")
	public TblTeacherInf findById(Integer id) {
		return springCacheService.findById(id);
	}
	
}
