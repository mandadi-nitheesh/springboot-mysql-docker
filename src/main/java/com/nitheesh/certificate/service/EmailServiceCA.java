package com.nitheesh.certificate.service;

import com.nitheesh.certificate.Payload.EmailDto;

public interface EmailServiceCA {
	
	public String sendEmail(EmailDto emailDto);

}
