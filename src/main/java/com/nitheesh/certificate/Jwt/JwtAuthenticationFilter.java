package com.nitheesh.certificate.Jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Qualifier("handlerExceptionResolver")
	@Autowired
	private HandlerExceptionResolver erroResolver;
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				
		String header=request.getHeader("Authorization");
		
		System.out.println(header);
		
		String username=null;
		
		String token=null;
		
		try {
		
		if(header!=null && header.startsWith("Bearer")) {
			token=header.substring(7);
			
				username = this.jwtHelper.getUserNameFromToken(token);

		}
		else {
			logger.info("Invalid header value...");
		}
		
		// if authentication is null....
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication() ==null ) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.isTokenValid(token, userDetails);
            
            if (validateToken) {

                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            } else {
                logger.info("Validation fails !!");
            }
            
            
		}
		
	filterChain.doFilter(request, response);
	
		}
		catch (Exception e) {
			erroResolver.resolveException(request, response,null,e);
		}
	
		
	}

}
