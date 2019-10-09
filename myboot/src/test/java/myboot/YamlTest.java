package myboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.myboot.MybootApplication;
import com.example.properties.AcmeProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybootApplication.class)
public class YamlTest {

	@Value("${model1.name}")
	private String str1;

	@Value("${model2.name}")
	private String str2;

	@Autowired
	private AcmeProperties acmeProperties;

	@Test
	public void TestYaml() {
		System.out.println("model1.name: " + str1);
		System.out.println("model2.name: " + str2);
		System.out.println("acme.address: " + acmeProperties.getRemoteAddress());
		System.out.println("acme.password: " + acmeProperties.getSecurity().getPassword());
		System.out.println("acme.username: " + acmeProperties.getSecurity().getUsername());
	}
}
