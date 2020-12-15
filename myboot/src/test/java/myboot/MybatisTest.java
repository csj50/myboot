package myboot;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.create.entity.TblTeacherInf;
import com.create.entity.TblTeacherInfMapper;
import com.create.entity.TblTeacherInfMapperTk;
import com.example.myboot.MybootApplication;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybootApplication.class)
public class MybatisTest {

	@Autowired
	TblTeacherInfMapper teacherMapper;
	
	@Autowired
	TblTeacherInfMapperTk teacherMapperTk;
	
	@Test
	public void testMybatis() {
		TblTeacherInf teacher = teacherMapper.selectByPrimaryKey(1);
		System.out.println("id is: " + teacher.getId());
		System.out.println("name is: " + teacher.getName());
	}
	
	@Test
	public void testTkMybatis() {
		TblTeacherInf record = new TblTeacherInf();
		record.setId(2);
		TblTeacherInf teacher = teacherMapperTk.selectOne(record);
		System.out.println("id is: " + teacher.getId());
		System.out.println("name is: " + teacher.getName());
	}
	
	@Test
	public void testMybatisPageHelper() {
		//使用RowBounds
		//offset是5，limit是5，从第5条开始（不包含）查5条记录
		RowBounds rowBounds = new RowBounds(5, 5);
		//查询
		List<TblTeacherInf> lists = teacherMapperTk.selectByRowBounds(null, rowBounds);
		System.out.println("count is: " + ((Page<TblTeacherInf>)lists).getTotal());
		for(TblTeacherInf tmp : lists) {
			System.out.println("id is: " + tmp.getId());
			System.out.println("name is: " + tmp.getName());
		}
	}
	
	@Test
	public void testTkMybatisPageHelper() {
		//使用PageHelper
		//获取第2页，5条内容，默认查询总数count
		PageHelper.startPage(2, 5);
		//紧跟着的第一个select方法会被分页
		List<TblTeacherInf> lists = teacherMapperTk.selectAll();
		System.out.println("count is: " + ((Page<TblTeacherInf>)lists).getTotal());
		for(TblTeacherInf tmp : lists) {
			System.out.println("id is: " + tmp.getId());
			System.out.println("name is: " + tmp.getName());
		}
	}
}
