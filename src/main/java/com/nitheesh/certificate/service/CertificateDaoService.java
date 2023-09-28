package com.nitheesh.certificate.service;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;

import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateResponse;
import com.nitheesh.certificate.entity.CertificateData;

// used to save and retieve and download the files in the database....

public interface CertificateDaoService {
	
	public CertificateResponse saveCertificate(X509Certificate file,String filename,PublicKey key,PrivateKey privateKey) throws IOException, CertificateEncodingException;
	
	
	public List<CertificateData> getAllCertificates();
	
	
	public CertificateData getCertificateData(String id);
	

}
