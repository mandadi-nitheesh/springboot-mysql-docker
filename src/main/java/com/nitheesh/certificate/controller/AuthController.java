package com.nitheesh.certificate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitheesh.certificate.Jwt.CustomUser;
import com.nitheesh.certificate.Jwt.CustomUserDto;
import com.nitheesh.certificate.Jwt.CustomUserService;
import com.nitheesh.certificate.Jwt.JwtHelper;
import com.nitheesh.certificate.jwtpayload.JwtRequest;
import com.nitheesh.certificate.jwtpayload.JwtResponse;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private CustomUserService customUserService;
	
	
		
	@CrossOrigin("http://localhost:3000")
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
		
		doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		
		System.out.println("hello....");
		
		UserDetails userDetails=userDetailsService.loadUserByUsername(jwtRequest.getEmail());
		
		String token=jwtHelper.generateToken(userDetails);
		
		JwtResponse jwtResponse=JwtResponse.builder().jwtToken(token)
				.username(userDetails.getUsername())
				.rolesAuthorities(userDetails.getAuthorities()).build();
		
		return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);
	}
	
	
	
	public void doAuthenticate(String email,String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(email, password);
		
		
			authenticationManager.authenticate(authenticationToken);
			
	}
	
	
	@PostMapping("/createUser")
	public ResponseEntity<CustomUser> createUser(@RequestBody CustomUser user){
		
		
		return new ResponseEntity<CustomUser>(customUserService.createUser(user),HttpStatus.CREATED);
		
	}
	
	@GetMapping("/allUsers")
	@PreAuthorize("hasAuthority('ROLE_CA')")
	public ResponseEntity<List<CustomUserDto>> getAllUsers(){
		System.out.println("users :"+ customUserService.getAllUsers());
		return new ResponseEntity<List<CustomUserDto>>(customUserService.getAllUsers(),HttpStatus.OK);
	}
	
	

}
