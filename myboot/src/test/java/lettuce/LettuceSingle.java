package lettuce;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于Lettuce单机连接redis
 * @author user
 *
 */
@Slf4j
public class LettuceSingle {
	
	public static void main(String[] args) {
		operSingle();
	}
	
	public static void operSingle() {
		//步骤1：RedisURI连接信息
		RedisURI redisUri = RedisURI.builder()
				.withHost("173.254.202.227")
				.withPort(10079)
				.withPassword("123_12345678^_00")
				.withTimeout(Duration.of(10, ChronoUnit.SECONDS))
				.build();
		
		//步骤2：redis客户端
		RedisClient redisClient = RedisClient.create(redisUri);
		
		//步骤3：redis连接
		StatefulRedisConnection<String, String> connect = redisClient.connect();
		
		//步骤4：redis命令API接口
		/**
		 * sync同步调用
		 */
		RedisCommands<String, String> command = connect.sync();
		command.set("hello", "hello world");
		String str = command.get("hello");
		log.info("----------同步-----{}----------", str);
		
		/**
		 * async异步调用
		 */
		RedisAsyncCommands<String, String> asyncCommand = connect.async();
		RedisFuture<String> future = asyncCommand.get("hello");
		try {
			String str1 = future.get();
			log.info("----------异步-----{}----------", str1);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		connect.close();
		redisClient.shutdown();
		
	}
}
