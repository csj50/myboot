package myboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.mail.MailService;
import com.example.myboot.MybootApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybootApplication.class)
public class MailTest {

	@Value("${test.mail.from}")
	private String mailFrom;

	@Value("${test.mail.to}")
	private String mailTo;

	@Autowired
	MailService mailService;

	@Test
	public void mailTest() {
		mailService.sendSimpleMail(mailFrom, mailTo, "myboot测试邮件", "你好，这是测试邮件");
	}
}
