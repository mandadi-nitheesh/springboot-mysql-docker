package com.nitheesh.certificate.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.bouncycastle.operator.OperatorCreationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nitheesh.certificate.CetificateManagementApplication;
import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateResponse;
import com.nitheesh.certificate.service.CetificateService;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class CertificateCreationControllerTest {
	
	
	@Mock
	private CetificateService service;
		

	@Test
	void testSignedCertificate() throws OperatorCreationException, NoSuchAlgorithmException, CertificateException, IOException {
		
		CertificateDetails details=new CertificateDetails("nitheesh","telstra","telstra software engineer","india","banglore","karnataka","nitheeshreddy290800@gmail.com","Signed");
		
		CertificateResponse expected=new CertificateResponse("nitheesh-certificate.crt","/x509/crt/at");
		
		when(service.generateSignedCertificate(details)).thenReturn(expected);
		
		CertificateCreationController controller=new CertificateCreationController();
		controller.setCetificateService(service);
		
		CertificateResponse result = controller.generateSignedCertificate(details).getBody();
		
		assertEquals(expected, result);
	}

	@Test
	public void testCertificateCreationController() throws Exception {
		
		String uriString="/create/certificate/CA";
		CertificateResponse response=new CertificateResponse("nitheesh-certificate.crt","/application/509");

		
		when(service.generateCaCertificate(Mockito.any(CertificateDetails.class))).thenReturn(response);
				
		CertificateDetails details=new CertificateDetails("nitheesh","telstra","telstra software engineer","india","banglore","karnataka","nitheeshreddy290800@gmail.com","Signed");
		
				
		
		ResponseEntity<CertificateResponse> res=new ResponseEntity<CertificateResponse>(response,HttpStatus.CREATED);
		
		when(service.generateSignedCertificate(details)).thenReturn(response);
		
		CertificateCreationController certificateCreationController=new CertificateCreationController();
		certificateCreationController.setCetificateService(service);
		
		ResponseEntity<CertificateResponse> responseentity=certificateCreationController.generateCaCertificate(details);
		
		
		assertEquals(HttpStatus.CREATED.value(),responseentity.getStatusCodeValue());
		
	}
	
	
	
}
