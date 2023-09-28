package com.nitheesh.certificate.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nitheesh.certificate.repository.CustomUserRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private CustomUserRepo userRepo;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser user=userRepo.findByEmail(username);
		return user;
	}

}
