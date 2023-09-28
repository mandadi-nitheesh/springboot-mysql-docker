package com.nitheesh.certificate.scheduler;

import java.io.FileNotFoundException;
import java.security.cert.CertificateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.nitheesh.certificate.monitoring.service.CertificateMonitoringService;

@Configuration
@EnableScheduling
public class Scheduler {
	
	@Autowired
	private CertificateMonitoringService service;
	
	
	Logger logger=LoggerFactory.getLogger(Scheduler.class);
	
	
	@Scheduled(fixedRate = 24*60*60*1000)
	public void emailScheduler() throws CertificateException, FileNotFoundException {
		
		logger.info("scheduler is called.....");
		
		service.checkIfCertificateExpired();
		
	}
	
	
	

}
