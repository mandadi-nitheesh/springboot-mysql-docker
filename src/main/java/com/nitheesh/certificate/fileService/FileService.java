package com.nitheesh.certificate.fileService;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

public interface FileService {
	
	
	public void addCertificateToFile(X509Certificate certificate,String filename) throws CertificateEncodingException, IOException;

}
