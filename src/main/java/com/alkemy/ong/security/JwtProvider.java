package com.alkemy.ong.security;

import java.util.Date;
import java.util.Locale;

import com.alkemy.ong.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	@Autowired
	private MessageSource messageSource;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;

	public String generatedToken(User user) {
		return Jwts.builder().setSubject(user.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public String getEmailFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	public Boolean validateToken(String token) throws Exception {

		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error(messageSource.getMessage("jwt.error.token.malformed", null, Locale.getDefault()));
		} catch (UnsupportedJwtException e) {
			logger.error(messageSource.getMessage("jwt.error.token.unsupported", null, Locale.getDefault()));
		} catch (ExpiredJwtException e) {
			logger.error(messageSource.getMessage("jwt.error.token.expired", null, Locale.getDefault()));
		} catch (IllegalArgumentException e) {
			logger.error(messageSource.getMessage("jwt.error.token.notFound", null, Locale.getDefault()));
		} catch (SignatureException e) {
			logger.error(messageSource.getMessage("jwt.error.token.failure", null, Locale.getDefault()));
		}

		return false;

	}
}
