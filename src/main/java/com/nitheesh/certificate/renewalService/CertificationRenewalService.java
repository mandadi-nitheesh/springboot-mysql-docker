package com.nitheesh.certificate.renewalService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.operator.OperatorCreationException;

import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateResponse;

public interface CertificationRenewalService {
	
	public CertificateResponse renewalCertificate(String id,String subjectDn,CertificateDetails certificateDetails) throws OperatorCreationException, CertificateException, IOException, InvalidKeySpecException, NoSuchAlgorithmException;

}
