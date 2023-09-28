package com.nitheesh.certificate.jwtpayload;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
	
	private String jwtToken;
	
	private String username;
	
	private Collection<? extends GrantedAuthority> rolesAuthorities;

}
