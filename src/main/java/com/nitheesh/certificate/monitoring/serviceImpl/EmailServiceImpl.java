package com.nitheesh.certificate.monitoring.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nitheesh.certificate.monitoring.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String toemail,String body,String subjet) {
		
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		
		mailMessage.setFrom("nitheeshreddy290800@gmail.com");
		
		mailMessage.setTo(toemail);
		
		mailMessage.setText(body);
		
		mailMessage.setSubject(subjet);
	
		
		javaMailSender.send(mailMessage);
		
		System.out.println("mail sent");
		
		
		
		
	}

}
