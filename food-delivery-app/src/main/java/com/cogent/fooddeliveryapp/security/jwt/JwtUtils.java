package com.cogent.fooddeliveryapp.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cogent.fooddeliveryapp.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 23.-오후 12:30:22
 */
@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${com.cogent.fooddeliveryapp.jwtSecret}")
	private String jwtSecret;

	@Value("${com.cogent.fooddeliveryapp.jwtExpirationMs}")
	private long jwtExpirationMs;

	// generate token
	public String generateToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal(); // get credentials
		return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

	}

	// valid token
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(authToken);
			return true;
		} catch (ExpiredJwtException e) {

			logger.error("JWT token is expired signature: {} ", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsuported: {} ", e.getMessage());

		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {} ", e.getMessage());
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {} ", e.getMessage());
		} catch (IllegalArgumentException e) {

		}
		return false;

	}
	// get name from the token ==> 
	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser() // compact --> java obejct 
				.setSigningKey(jwtSecret) // secret key --> encoding done 
				.parseClaimsJwt(token) // provide actual token 
				.getBody() // extracting the body content (payload info)
				.getSubject(); // extracting the subject 
	}
}
