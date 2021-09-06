package myboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.myboot.MybootApplication;
import com.example.sms.MyMessage;
import com.example.sms.SmsProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybootApplication.class)
public class StarterTest {

	@Autowired
	MyMessage myMessage;
	
	@Autowired
	SmsProperties smsProperties;
	
	@Test
	public void testSend() {
		System.out.println("timeout is: " + smsProperties.getTimeout());
		System.out.println("retry is: " + smsProperties.getRetry());
		myMessage.send("13600000001", "你好");
	}
}
