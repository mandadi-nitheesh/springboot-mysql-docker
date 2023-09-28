package com.nitheesh.certificate.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import org.bouncycastle.operator.OperatorCreationException;

import com.nitheesh.certificate.Payload.CertificateDataDto;
import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateDetailsDto;
import com.nitheesh.certificate.Payload.CertificateResponse;
import com.nitheesh.certificate.entity.CertificateData;

public interface CetificateService {
	
	
	public X509Certificate generateSelfSignedCertificate(CertificateDetails details) throws OperatorCreationException, NoSuchAlgorithmException, CertificateException, IOException;

	public CertificateResponse generateSignedCertificate(CertificateDetails details) throws OperatorCreationException, CertificateException, IOException, NoSuchAlgorithmException;
	
	public CertificateResponse generateCaCertificate(CertificateDetails details) throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, IOException;
	
	public CertificateResponse generateUnsignedCertificate(CertificateDetails details) throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, IOException;
	
	
	public CertificateDetails getDataFromDb(String id) throws CertificateException;
	
	
	public List<CertificateDataDto> getAllCertificateData();
	
	
	public CertificateData getCertificateByid(String id);
	
	public CertificateDetailsDto getDetailsOfCertificate(String id) throws CertificateException;
}
