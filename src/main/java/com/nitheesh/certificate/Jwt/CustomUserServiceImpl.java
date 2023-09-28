package com.nitheesh.certificate.Jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.nitheesh.certificate.repository.CustomUserRepo;
@Service
public class CustomUserServiceImpl implements CustomUserService{
	
	@Autowired
	private CustomUserRepo userRepo;
	
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public List<CustomUserDto> getAllUsers() {
		// TODO Auto-generated method stub
		
		List<CustomUser> users=userRepo.findAll();
		
		List<CustomUserDto> dtos=users.stream().map((u)->mapToDto(u)).toList();
		System.out.println(dtos);
		
		return dtos;
	}

	@Override
	public CustomUser createUser(CustomUser user) {
		user.setPassword(encoder.encode(user.getPassword()));
		CustomUser savedCustomUser=userRepo.save(user);
		System.out.println(savedCustomUser);
		return savedCustomUser;
	}
	
	public CustomUserDto mapToDto(CustomUser customUser) {
		CustomUserDto dto=new CustomUserDto();
		dto.setId(customUser.getId());
		dto.setEmail(customUser.getEmail());
		return dto;
	}

}
