package com.nitheesh.certificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nitheesh.certificate.Jwt.CustomUser;

public interface CustomUserRepo extends JpaRepository<CustomUser, Long> {
	
	
	 public CustomUser findByEmail(String email);

}
