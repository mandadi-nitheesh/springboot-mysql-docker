package com.nitheesh.certificate.Jwt;

import java.util.List;

public interface CustomUserService {
	
	public List<CustomUserDto> getAllUsers();
	
	
	public CustomUser createUser(CustomUser user);

}
