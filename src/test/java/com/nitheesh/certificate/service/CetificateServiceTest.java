package com.nitheesh.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.bouncycastle.operator.OperatorCreationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateResponse;

@SpringBootTest
@ContextConfiguration
class CetificateServiceTest {
	
	@MockBean
	private CertificateDaoService certificateDaoService;
	
	
	@Autowired
	private CetificateService  service;

	@Test
	void testgenerateSignedCerificate() throws IOException, OperatorCreationException, CertificateException, NoSuchAlgorithmException {
		
		CertificateResponse certificateResponse=new CertificateResponse();
		certificateResponse.setFilename("vazeem-certificate.crt");
		
		certificateResponse.setFileType("application/x509");

		
		when(certificateDaoService.saveCertificate(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(certificateResponse);
		
		CertificateDetails details=new CertificateDetails("nitheesh","telstra","telstra software engineer","india","banglore","karnataka","nitheeshreddy290800@gmail.com","Signed");
		
		CertificateResponse response=service.generateSignedCertificate(details);
		
		assertEquals(certificateResponse, response);
	}

}
