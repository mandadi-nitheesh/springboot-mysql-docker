package com.nitheesh.certificate.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nitheesh.certificate.Payload.EmailDto;
import com.nitheesh.certificate.service.EmailServiceCA;


@Service
public class EmailServiceCAImpl implements EmailServiceCA {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public String sendEmail(EmailDto emailDto) {
SimpleMailMessage mailMessage=new SimpleMailMessage();
		
		mailMessage.setFrom(emailDto.getFrom());
		
		mailMessage.setTo("nitheesh.19bcn7095@vitap.ac.in");
		
		mailMessage.setText(String.format("please renew the %s as per the request of user",emailDto.getCertificateName()));
		
		mailMessage.setSubject("Renewal Of Certificate");
		
		javaMailSender.send(mailMessage);
		return "emailSent";
	}

}
