package com.cogent.fooddeliveryapp.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cogent.fooddeliveryapp.security.services.UserDetailsImpl;


/**
 * @author : Ki Beom Lee
 * @time : 2022. 2. 23.-오후 2:40:13
 */
// one per filter 
public class AuthTokenFilter extends OncePerRequestFilter {
	
	@Autowired
	JwtUtils jwtUtils ;  


	@Autowired
	UserDetailsService userDetailService ;  
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenFilter.class) ;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// extract the token from the request 
		String jwt = parseJwt(request);
		try {
			if(jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUsernameFromJwtToken(jwt);
				UserDetails details = userDetailService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			logger.error("can't set user authentication{}"+ e);
		}
		filterChain.doFilter(request, response);  // will call next filter/DS(dispacher servlet)
		// once we will ge it then validate it.
		
		
		
	}
	// extract token 
	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization") ;
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}
		return null ;
	}
	
	
}
