package com.microservices.authsvc.config;

import com.microservices.authsvc.config.exceptions.CustomAuthenticationEntryPoint;
import com.microservices.authsvc.services.UserService;
import com.microservices.authsvc.util.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  {

	@Autowired
	UserService userService;
	@Autowired
	TokenFilter tokenFilter;
	@Autowired
	CustomAuthenticationEntryPoint CustomAuthenticationEntryPoint;

	private static final String[] AUTH_WHITELIST = {
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/login",
			"/create",
			"/swagger-ui/**"
	};



	  @Bean
	   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable().authorizeHttpRequests((authz) -> authz
	                .antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated()
	            ).exceptionHandling().authenticationEntryPoint(CustomAuthenticationEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	        return http.build();
	    }

	  @Bean
	  public PasswordEncoder encoder() {
	      return new BCryptPasswordEncoder();
	  }


	  //Configure custom userDetailsService
	@Bean
	public AuthenticationManager authManager(HttpSecurity http)
			throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userService)
				.passwordEncoder(encoder())
				.and()
				.build();
	}
	
	}


