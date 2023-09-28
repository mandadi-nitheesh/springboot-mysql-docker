package com.nitheesh.certificate.monitoring.serviceImpl;

import java.security.cert.X509Certificate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nitheesh.certificate.monitoring.service.EmailService;
import com.nitheesh.certificate.monitoring.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private EmailService emailService;
	
	
	
	@Override
	public void sendNotification(X509Certificate cert) {
		
       String string = cert.getIssuerX500Principal().toString();  
		
		System.out.println(string);
		
		String array[]=string.split(",");
		
		String string2 = Arrays.stream(array).filter((s)->s.contains("EMAILADDRESS")).findFirst().get();
		
		// generates email.... from the certificate...
		
		String email = string2.substring(13);
		
		 String subject="expiration of your certificate "; String body="your certification expires with in 7 days so please renewal your certificate ";
		
		emailService.sendEmail(email,body,subject);
		
		
		
		
	}

}
