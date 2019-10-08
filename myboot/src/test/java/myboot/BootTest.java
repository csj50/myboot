package myboot;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.example.myboot.MybootApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybootApplication.class) // 指定启动类
@WebAppConfiguration // 由于是Web项目,Junit模拟ServletContext
@TestPropertySource(value = "classpath:test.properties") // 指定测试参数文件
public class BootTest {

	@Value("${key}")
	private String str;

	@Value("${key2}")
	private String str2;

	@Before
	public void init() {
		System.out.println("开始测试-----------------");
	}

	@After
	public void after() {
		System.out.println("测试结束-----------------");
	}

	@Test
	public void TestHello() {
		System.out.println("hello world...");
		System.out.println("str is: " + str);
		System.out.println("str2 is: " + str2);
	}
}
