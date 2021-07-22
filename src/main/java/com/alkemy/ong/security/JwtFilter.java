package com.alkemy.ong.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alkemy.ong.service.Interface.IUsersService;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
	
	
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private IUsersService iUserService;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String token = getToken(request);

			if (token != null && jwtProvider.validateToken(token)) {

				String email = jwtProvider.getEmailFromToken(token);

				UserDetails userDetails = iUserService.loadUserByUsername(email);

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			logger.error(messageSource.getMessage("jwt.error.method.doFilter.fail", null, Locale.getDefault()),
					e.getMessage());
		}
		filterChain.doFilter(request, response);
	}

	public String getToken(HttpServletRequest request) {

		String header = request.getHeader(AUTHORIZATION);
		if (header != null && header.startsWith(BEARER)) {
			return header.substring(7);
		}
		return null;
	}
}
