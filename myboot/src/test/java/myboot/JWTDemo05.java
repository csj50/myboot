package myboot;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * JWT加密原理
 * @author user
 *
 */
public class JWTDemo05 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		//header
		JSONObject header = new JSONObject();
		header.put("alg", "HS256");
		
		//payload
		JSONObject payLoad = new JSONObject();
		payLoad.put("userName", "zhangsan123123");
		payLoad.put("userAge", "21");
		
		//base64编码
		String jwtHeader = Base64.getEncoder().encodeToString(header.toJSONString().getBytes());
		String jwtPayLoad = Base64.getEncoder().encodeToString(payLoad.toJSONString().getBytes());
		
		String signKey = "test123test123test123test123test123test123test123";
		//签名，后面再加盐
		String sign = DigestUtils.md5Hex(payLoad.toJSONString() + signKey);
		
		String jwt = jwtHeader + "." + jwtPayLoad + "." + sign;
		System.out.println(jwt);
		
		/**
		 * 解密
		 */
		String str[] = jwt.split("\\.");
		String headerStr = new String(Base64.getDecoder().decode(str[0]), "UTF-8");
		String payLoadStr = new String(Base64.getDecoder().decode(str[1]), "UTF-8");
		String signStr = str[2];
		
		System.out.println(DigestUtils.md5Hex(payLoadStr + signKey).equals(signStr));
	}
}
