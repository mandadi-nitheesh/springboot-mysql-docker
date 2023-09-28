package com.nitheesh.certificate.KeyPairService;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public interface KeyPairGeneratorService {
	
	
	public KeyPair  generateKetPair() throws NoSuchAlgorithmException;

}
