package com.nitheesh.certificate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitheesh.certificate.Payload.EmailDto;
import com.nitheesh.certificate.service.EmailServiceCA;



@RestController
@RequestMapping("email")
public class EmailController {
	
	@Autowired
	private EmailServiceCA service;
	
	@PostMapping("/renewal")
	public ResponseEntity<String> sendEmailToCa(@RequestBody EmailDto emailDto){
		
		return new ResponseEntity<String>(service.sendEmail(emailDto), HttpStatus.OK);
		
	}

}
