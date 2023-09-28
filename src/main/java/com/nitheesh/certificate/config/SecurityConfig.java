package com.nitheesh.certificate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.nitheesh.certificate.Jwt.JwtAuthenticationEntryPoint;
import com.nitheesh.certificate.Jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebMvc
@EnableMethodSecurity
public class SecurityConfig {
	
	public static final String[] PUBLIC_STRINGS= {
			"/v3/api-docs/**",
			"/v3/api-docs.yaml",
			"/api/auth/v1/**",
			"/swagger-resources/**",
			"/swagger-ui.html",
			"/swagger-ui/**",
			"/webjars/**"
			
	};
	
	@Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

       http.csrf((csrf)->csrf.disable())
       .cors(Customizer.withDefaults())
       .authorizeHttpRequests((r)->r.requestMatchers("/create/**").authenticated()
    		   .requestMatchers("/auth/allUsers").authenticated()
    		   .requestMatchers("/auth/**").permitAll()
    		   .requestMatchers(PUBLIC_STRINGS).permitAll()
    		   .anyRequest().authenticated())
       .sessionManagement((s)->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       
       http.addFilterAfter(filter,UsernamePasswordAuthenticationFilter.class);
       
        return http.build();
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    	DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
    	
    	provider.setUserDetailsService(userDetailsService);
    	
    	provider.setPasswordEncoder(passwordEncoder);
    	
    	return provider;
    }
    
    	

}
