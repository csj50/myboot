package myboot;

import java.util.Date;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jackson.io.JacksonSerializer;

public class JWTDemo {

	public static void main(String[] args) {
		//jwt秘钥
		String signKey = "test123test123test123test123test123test123test123";
		//创建jwt
		JwtBuilder jwtBuilder =Jwts.builder().setId("66").setSubject("test123")
				.setIssuedAt(new Date())
				.claim("username", "zhangsan123123")
				//设置签名值
				.signWith(SignatureAlgorithm.HS256, signKey)
				//添加序列化
				.serializeToJsonWith(new JacksonSerializer<>());
		System.out.println(jwtBuilder.compact());
	}
}
