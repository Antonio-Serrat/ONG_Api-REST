package com.alkemy.ong.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alkemy.ong.service.impl.UsersServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsersServiceImpl userServiceImpl;
	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Bean
	public JwtFilter jwtTokenFilter() {
		return new JwtFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll().and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/auth/me").hasAnyRole("USER").and().authorizeRequests()
				.antMatchers(HttpMethod.DELETE, "/users/:id*").hasAnyRole("USER").and().authorizeRequests()
				.antMatchers(HttpMethod.PATCH, "/users/:id").hasAnyRole("USER").and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/organization/public").hasAnyRole("USER","ADMIN").and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/organization/new").hasAnyRole("ADMIN").and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/organization/newContact/:id").hasAnyRole("ADMIN").and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/organization/public/:id").hasAnyRole("ADMIN").and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN").and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/categories", "/categories/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/categories").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/categories/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/categories/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/news/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/news").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/news/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/news/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/activities").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/activities").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/activities/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/activities/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/activities/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/activities/:id/image/upload").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/contacts").hasAnyRole("USER").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/slides").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/slides/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/slides").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/slides/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/slides/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/contacts").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/backoffice/contacts").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/testimonials").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/testimonials/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/testimonials/:id").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/members").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/members").hasAnyRole("USER").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/members/:id").hasAnyRole("USER").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/members/:id").hasAnyRole("USER").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/comments").hasAnyRole("ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/comments").hasAnyRole("USER").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/comments/:id").hasAnyRole("USER").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/comments/:id").hasAnyRole("USER").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/posts/:id/comments").hasAnyRole("USER")
				.anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs/**");
		web.ignoring().antMatchers("/swagger.json");
		web.ignoring().antMatchers("/swagger-ui.html");
		web.ignoring().antMatchers("/swagger-resources/**");
		web.ignoring().antMatchers("/webjars/**");
	}
}
