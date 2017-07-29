package com.truedo.agrokult;

import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



public class CreateJsonToken {
	  static final long EXPIRATIONTIME = 864_000_000; // 10 days
	  static final String SECRET = "trueDo_agrokult";
	  static final String TOKEN_PREFIX = "Bearer";
	  static final String HEADER_STRING = "Authorization";

	  
	  public static String addAuthentication(String username,String phone,String password) {
		    String JWT = Jwts.builder()
		        .setSubject(username+"~"+phone+"~"+password)
		        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
		        .signWith(SignatureAlgorithm.HS512, SECRET)
		        .compact();
		    return  TOKEN_PREFIX + " " + JWT;
		  }
}
