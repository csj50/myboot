package myboot;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.base.Constants;
import com.example.myboot.MybootApplication;
import com.example.utils.RedisLockUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybootApplication.class)
public class RedisLockTest {

	@Autowired
	RedisLockUtil redisLock;

	@Test
	public void redisLockTest() {
		String key = Constants.LOCK_KEY + "123";
		String value = "123";
		redisLock.lock(key, value, 3000, TimeUnit.SECONDS);
	}

	@Test
	public void redisUnlockTest() {
		String key = Constants.LOCK_KEY + "123";
		String value = "123";
		redisLock.unlock(key, value);
	}

}
