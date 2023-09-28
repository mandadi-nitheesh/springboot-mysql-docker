package com.nitheesh.certificate.KeyPairServiceImpl;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.nitheesh.certificate.KeyPairService.KeyPairGeneratorService;


@Service
public class KeyPairGeneratorImpl implements KeyPairGeneratorService {

	@Override
	public KeyPair generateKetPair() throws NoSuchAlgorithmException {
		
		
		
		 KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		 
         keyPairGenerator.initialize(2048);
         KeyPair keyPair = keyPairGenerator.generateKeyPair();
		
				return keyPair;
	}

}
