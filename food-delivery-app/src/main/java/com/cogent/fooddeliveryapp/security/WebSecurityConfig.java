package com.cogent.fooddeliveryapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cogent.fooddeliveryapp.security.jwt.AuthEntryPointJwt;
import com.cogent.fooddeliveryapp.security.jwt.AuthTokenFilter;
import com.cogent.fooddeliveryapp.security.services.UserDetailsServiceImpl;
import com.cogent.fooddeliveryapp.security.services.UserDetailsImpl;

/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 23.-오후 4:41:40
 */
@Configuration
@EnableWebSecurity // it will make sure that security env. is enabled
@EnableGlobalMethodSecurity(prePostEnabled = true) //
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private AuthEntryPointJwt authEntryPointJwt;

	@Bean
//	@Scope("prototype") for more than one 
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override // auth manager
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		//core part of the security -> we can restrict the access of endpoints through this config.
		//we can set unauthorized access through this. 
		//we can provide direct go  access for sign up and sign in(authorizing hte res).
		//apply token validation for end points.
		//cors:
		//
		
		
		super.configure(http);
	}

}
