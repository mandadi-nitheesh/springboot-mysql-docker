package com.nitheesh.certificate.monitoring.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration
class EmailServiceTest {
	
	@Autowired	
	private EmailService service;
	
	@Mock
	private JavaMailSender sender;
	

	@Test
	void testsendEmail() {
		
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		
         mailMessage.setFrom("nitheeshreddy290800@gmail.com");
		
		mailMessage.setTo("nitheesh.19bcn7095@vitap.ac.in");
		
		mailMessage.setText("testing");
		
		mailMessage.setSubject("testing message");
		
		doNothing().when(sender).send(mailMessage);
		
		sender.send(mailMessage);
		
	//service.sendEmail("nitheesh.19bcn7095@vitap.ac.in","testing","testing message");

	}

}
