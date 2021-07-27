package lettuce;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LettuceCluster {
	
	public static void main(String[] args) {
		operCluster();
	}
	
	public static void operCluster() {
		//步骤1：RedisURI连接信息
		List<RedisURI> list = new ArrayList<>();
		list.add(RedisURI.create("redis://72.19.12.191:6381"));
		list.add(RedisURI.create("redis://72.19.12.191:6382"));
		list.add(RedisURI.create("redis://72.19.12.191:6383"));
		list.add(RedisURI.create("redis://72.19.12.191:6384"));
		list.add(RedisURI.create("redis://72.19.12.191:6385"));
		list.add(RedisURI.create("redis://72.19.12.191:6386"));
		
		//步骤2：redis集群客户端
		RedisClusterClient client = RedisClusterClient.create(list);
		
		//步骤3：redis连接（集群）
		StatefulRedisClusterConnection<String, String> connect = client.connect();
		
		//步骤4：redis的API命令
		/**
		 * sync同步调用
		 */
		RedisAdvancedClusterCommands<String, String> command = connect.sync();
		command.set("hello", "hello world");
		String str = command.get("hello");
		log.info("----------同步-----{}----------", str);
		
		/**
		 * async异步调用
		 */
		RedisAdvancedClusterAsyncCommands<String, String> asyncCommand = connect.async();
		RedisFuture<String> future = asyncCommand.get("hello");
		try {
			String str1 = future.get();
			log.info("----------异步-----{}----------", str1);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		connect.close();
		client.shutdown();
	}
}
