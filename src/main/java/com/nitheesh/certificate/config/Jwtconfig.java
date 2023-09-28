package com.nitheesh.certificate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Jwtconfig {
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user1=User.builder().username("nitheesh").password(byBCryptPasswordEncoder().encode("nitheesh")).roles("ADMIN","CA").build();
//		
//		UserDetails user2 =User.builder().username("karthik").password(byBCryptPasswordEncoder().encode("karthik")).roles("ADMIN","CA").build();
//		
//		return new InMemoryUserDetailsManager(user1,user2);
//		
//	}
	
	
	@Bean
	public BCryptPasswordEncoder byBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	
	
	
	
	

}
