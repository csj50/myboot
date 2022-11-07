package myboot;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTDemo02 {

	public static void main(String[] args) {
		//jwt秘钥
		String signKey = "test123test123test123test123test123test123test123";
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NiIsInN1YiI6InRlc3QxMjMiLCJpYXQiOjE2Njc4MDE3MDUsInVzZXJuYW1lIjoiemhhbmdzYW4xMjMxMjMifQ.3wkV3WrPyjxyUs-9E_4-1QcpExE2N14zxFP_MlgAtjQ";
		Claims body = Jwts.parser().setSigningKey(signKey).parseClaimsJws(jwt).getBody();
		System.out.println(body.toString());
	}
}
