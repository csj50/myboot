package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.create.entity.TblTeacherInf;
import com.create.entity.TblTeacherInfMapper;

@Service
@CacheConfig(cacheNames = {"redis-cache"})
public class SpringCacheService {

	@Autowired
	TblTeacherInfMapper teacherMapper;
	
	/**
	 * 增加
	 * 
	 * @param teacher
	 * @return
	 */
	@Cacheable(key = "#teacher.id")
	public TblTeacherInf insert(TblTeacherInf teacher) {
		// 先插入数据库
		teacherMapper.insert(teacher);
		// 再查询出插入的对象
		return teacherMapper.selectByPrimaryKey(teacher.getId());
	}
	
	/**
	 * 修改
	 * 
	 * @param teacher
	 * @return
	 */
	@CachePut(key = "#teacher.id")
	public TblTeacherInf update(TblTeacherInf teacher) {
		// 先修改数据库
		teacherMapper.updateByPrimaryKeySelective(teacher);
		// 再查询出修改的对象
		return teacherMapper.selectByPrimaryKey(teacher.getId());
	}
	
	/**
	 * 删除
	 * 
	 * @param teacher
	 * @return
	 */
	@CacheEvict(key = "#teacher.id")
	public boolean delete(TblTeacherInf teacher) {
		// 先删除数据库
		teacherMapper.deleteByPrimaryKey(teacher.getId());
		return true;
	}
	
	/**
	 * 查找
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable(key = "#id")
	public TblTeacherInf findById(Integer id) {
		return teacherMapper.selectByPrimaryKey(id);
	}
}
