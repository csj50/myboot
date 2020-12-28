package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.create.entity.TblTeacherInf;
import com.create.entity.TblTeacherInfMapper;

@Service
public class TeacherService {

	public static final String CACHE_KEY = "redis-cache:";

	@Autowired
	TblTeacherInfMapper teacherMapper;

	@Autowired
	RedisTemplate<Object, Object> redisTemplate; // 初始化泛型防止警告提示

	/**
	 * 增加
	 * 
	 * @param teacher
	 * @return
	 */
	public boolean insert(TblTeacherInf teacher) {
		// 先插入数据库
		teacherMapper.insert(teacher);
		// 再查询出插入的对象
		teacher = teacherMapper.selectByPrimaryKey(teacher.getId());
		if (null != teacher) {
			// 再增加redis缓存
			redisTemplate.opsForValue().set(CACHE_KEY + teacher.getId(), teacher);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改
	 * 
	 * @param teacher
	 * @return
	 */
	public boolean update(TblTeacherInf teacher) {
		// 先修改数据库
		teacherMapper.updateByPrimaryKeySelective(teacher);
		// 再查询出修改的对象
		teacher = teacherMapper.selectByPrimaryKey(teacher.getId());
		if (null != teacher) {
			// 再更新redis缓存，更新也是set命令
			redisTemplate.opsForValue().set(CACHE_KEY + teacher.getId(), teacher);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除
	 * 
	 * @param teacher
	 * @return
	 */
	public boolean delete(TblTeacherInf teacher) {
		// 临时保存key
		String tempKey = CACHE_KEY + teacher.getId();
		// 先删除数据库记录
		teacherMapper.deleteByPrimaryKey(teacher.getId());
		// 再查询删除的对象，为空则删除缓存
		teacher = teacherMapper.selectByPrimaryKey(teacher.getId());
		if (null == teacher) {
			// 再删除redis缓存
			redisTemplate.delete(tempKey);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查找
	 * 
	 * @param id
	 * @return
	 */
	public TblTeacherInf findById(Integer id) {
		ValueOperations<Object, Object> operation = redisTemplate.opsForValue();
		// 先查找redis缓存
		TblTeacherInf obj = (TblTeacherInf) operation.get(CACHE_KEY + id);
		if (obj == null) {
			// 到数据库查询
			obj = teacherMapper.selectByPrimaryKey(id);
			if (obj != null) {
				// 添加到redis缓存
				operation.set(CACHE_KEY + obj.getId(), obj);
			}
		}
		return obj;
	}
}
