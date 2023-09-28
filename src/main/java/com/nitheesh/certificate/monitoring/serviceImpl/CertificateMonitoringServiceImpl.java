package com.nitheesh.certificate.monitoring.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nitheesh.certificate.entity.CertificateData;
import com.nitheesh.certificate.monitoring.service.CertificateMonitoringService;
import com.nitheesh.certificate.monitoring.service.NotificationService;
import com.nitheesh.certificate.service.CertificateDaoService;

@Service
public class CertificateMonitoringServiceImpl implements CertificateMonitoringService{
	
	@Value("certificate.selfsigned.filepath")
	private String certificatefilePath;
	
	
	Logger logger=LoggerFactory.getLogger(CertificateMonitoringServiceImpl.class);
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private CertificateDaoService certificateDaoService;
	

	@Override
	public void checkIfCertificateExpired() throws CertificateException, FileNotFoundException {
//CertificateFactory fac = CertificateFactory.getInstance("X509");
		
//        FileInputStream stream=new FileInputStream(certificatefilePath);
//		
//        X509Certificate cert = (X509Certificate) fac.generateCertificate(stream);
        
        
        List<CertificateData> certificateDatas=certificateDaoService.getAllCertificates();
        
        List<X509Certificate> certificates=certificateDatas.stream().map((c)->generateCetificates(c.getData())).toList();
        
        for(X509Certificate certificate:certificates) {
        	
        	logger.info("inside the loop");

        	 
            if(iscertificateExpiresInSevenDays(certificate)) {  
            	logger.info("about to expire");
            	notificationService.sendNotification(certificate);
            }
            
        }

	}
	
	
	// function which executes logic about ths expiration of certificate..
	
	public Boolean iscertificateExpiresInSevenDays(X509Certificate certificate) {
		long timeUntilExpiry = certificate.getNotAfter().getTime() - System.currentTimeMillis();
        return timeUntilExpiry < 7 * 24 * 60 * 60 * 1000; // 7 days
        
	}
	
	
	// generating the certificate from the data recieved from the database.
	
	public X509Certificate generateCetificates(byte[] data){
		CertificateFactory fac;
		try {
			
			logger.info("inside certificate generation...");
			
			fac = CertificateFactory.getInstance("X509");
			
			ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(data);
			
			X509Certificate cert = (X509Certificate) fac.generateCertificate(byteArrayInputStream);
			
			return cert;
		} 
		catch (CertificateException e) {
			
			e.printStackTrace();
			
			return null;
		}
		
				
	}
	
	
	

}
