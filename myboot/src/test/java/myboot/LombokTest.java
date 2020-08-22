package myboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.domain.Teacher;
import com.example.myboot.MybootApplication;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybootApplication.class)
@Slf4j
public class LombokTest {

	@Test
	public void testSetGet() {
		Teacher teacher = new Teacher();
		teacher.setName("张三");
		teacher.setAge("32");
		log.info("teacher is: {}", teacher);
	}
}

