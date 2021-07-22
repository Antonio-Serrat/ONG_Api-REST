package com.alkemy.ong.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
	@Autowired
	private MessageSource messageSource;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {

		logger.error(messageSource.getMessage("jwt.error.method.commence.fail", null, Locale.getDefault()));

		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				messageSource.getMessage("jwt.error.response.unauthorized", null, Locale.getDefault()));

	}

}
