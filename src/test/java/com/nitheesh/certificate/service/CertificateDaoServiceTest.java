package com.nitheesh.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.nitheesh.certificate.Payload.CertificateResponse;
import com.nitheesh.certificate.entity.CertificateData;
import com.nitheesh.certificate.repository.CertificateRepo;

@SpringBootTest
public class CertificateDaoServiceTest {
	
	
	@Autowired
	private CertificateDaoService service;
	
	byte[] certificatedata;
	
	byte[] privateKey;
	
	byte[] publicKey;
		
	 KeyPair keyPair;
	 
	 X509Certificate cert;
	 
	 
	    @MockBean
		private CertificateRepo repo;
	 
	 
	
	@BeforeEach
	public void createData() throws NoSuchAlgorithmException, CertificateException, NoSuchProviderException, FileNotFoundException {
		certificatedata= new byte[] {1,2,3};
		privateKey= new byte[] {1,2,3};
		publicKey= new byte[] {1,2,3};
		
		
		KeyPairGenerator generator=KeyPairGenerator.getInstance("RSA");
		 generator.initialize(2048);
		 
		 keyPair = generator.generateKeyPair();
		 
		 CertificateFactory fac = CertificateFactory.getInstance("X509");
		 FileInputStream stream=new FileInputStream("/Users/mandadinitheesh/eclipse-workspace/Cetificate-Management/certificate/unsig.crt");
		 //System.out.println(stream.);
		 cert=(X509Certificate) fac.generateCertificate(stream);

		 System.out.println("executed....");
	}
	
	

	
	@Test
	void testAddingCertificateToDb() throws CertificateEncodingException, IOException {
		
		
		CertificateData data2=new CertificateData("xxxyyy","nitheesh-certificate.crt","appl/x509", certificatedata, privateKey,publicKey);
				
		
		//Mockito.doReturn(data2).when(repo.save(data));
		
		when(repo.save(Mockito.any())).thenReturn(data2);
		
		//CertificateData saveData=repo.save(data);
		
		CertificateResponse certificateResponse=new CertificateResponse();
		certificateResponse.setFilename(data2.getFileName());
		
		certificateResponse.setFileType(data2.getFileType());
		
		CertificateResponse response=service.saveCertificate(cert,"nitheesh-certificate", keyPair.getPublic(),keyPair.getPrivate());
		
		
		assertEquals(certificateResponse, response);
		
		
	}
	
	@Test
	void testGetAllCertificateFromDb() {
		
		List<CertificateData> data=new ArrayList<>();
		
		data.add(new CertificateData("aaaaaa","vazeem-certificate.crt","aapl/x509", certificatedata, privateKey, publicKey));
		data.add(new CertificateData("bbbbbb","naveen-certificate.crt","aapl/x509", certificatedata, privateKey, publicKey));
		data.add(new CertificateData("cccccc","karthik-certificate.crt","aapl/x509", certificatedata, privateKey, publicKey));
		
		when(repo.findAll()).thenReturn(data);
		
		List<CertificateData> response=service.getAllCertificates();
		
		//System.out.println(response);
		
		assertEquals(data, response);
	}

}
