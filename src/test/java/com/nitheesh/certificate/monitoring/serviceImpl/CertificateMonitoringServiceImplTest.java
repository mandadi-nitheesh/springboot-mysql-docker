package com.nitheesh.certificate.monitoring.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.nitheesh.certificate.CetificateManagementApplication;

@SpringBootTest(classes = CetificateManagementApplication.class)
@ContextConfiguration
class CertificateMonitoringServiceImplTest {
	
	
	X509Certificate cert;
	
	byte[] data;
	
	@BeforeEach
	void preprocess() throws CertificateException, IOException {
		CertificateFactory fac = CertificateFactory.getInstance("X509");
		FileInputStream stream=new FileInputStream("/Users/mandadinitheesh/eclipse-workspace/Cetificate-Management/certificate/unsig.crt");
		 //System.out.println(stream.);
		 cert=(X509Certificate) fac.generateCertificate(stream);
		 
		 data=cert.getEncoded();
		 System.out.println(cert.getNotAfter());
	}
	
	@Test
	void testCertificationExpires() {
		CertificateMonitoringServiceImpl serviceImpl=new CertificateMonitoringServiceImpl();
		
		boolean expected=true;
		
		Boolean response = serviceImpl.iscertificateExpiresInSevenDays(cert);
		
		assertEquals(expected,response);
	}
	
	@Test
	public void testgenerateCertificate() {
		CertificateMonitoringServiceImpl serviceImpl=new CertificateMonitoringServiceImpl();
		X509Certificate response = serviceImpl.generateCetificates(data);
		
		assertEquals(cert,response);
	}
	

}
