package myboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.myboot.MybootApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybootApplication.class)
public class RestTemplateTest {

	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void TestGetForEntity() {
		// 提交请求
		ResponseEntity<String> response = restTemplate.getForEntity("https://www.baidu.com", String.class);
		String result = response.getBody();
		HttpHeaders headers = response.getHeaders();
		HttpStatus httpStatus = response.getStatusCode();
		System.out.println("Get status: " + httpStatus.toString());
		System.out.println("Get headers: " + headers.toString());
		System.out.println("Get result: " + result);
	}

	@Test
	public void TestPostForEntity() {
		// 构建httpHeader
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		// 构建HttpEntity
		String request = "{\"aaa\":\"111\"}";
		HttpEntity<String> httpEntity = new HttpEntity<String>(request, httpHeaders);
		// 提交请求
		ResponseEntity<String> response = restTemplate
				.postForEntity("http://192.168.23.46:8003/afms-external/action/channelCert", httpEntity, String.class);
		String result = response.getBody();
		System.out.println("Post result: " + result);
	}

	@Test
	public void TestGetForObject() {
		String response = restTemplate.getForObject("https://www.baidu.com", String.class);
		System.out.println("Get result: " + response);
	}

	@Test
	public void TestPostForObject() {
		String request = "{\"aaa\":\"111\"}";
		// 提交请求
		String response = restTemplate.postForObject("http://192.168.23.46:8003/afms-external/action/channelCert",
				request, String.class);
		System.out.println("Post result: " + response);
	}

	@Test
	public void TestPostForObject2() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(30 * 1000);
		requestFactory.setReadTimeout(30 * 1000);
		RestTemplate restTemplate2 = new RestTemplate(requestFactory);
		String request = "{\"aaa\":\"111\"}";
		// 提交请求
		String response = restTemplate2.postForObject("http://192.168.23.46:8003/afms-external/action/channelCert",
				request, String.class);
		System.out.println("Post result2: " + response);
	}
}
