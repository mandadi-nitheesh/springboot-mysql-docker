package com.nitheesh.certificate.renewalService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateResponse;

@RestController
@RequestMapping("/renewal/certificate")
public class CertificateRenewalController {
	
	@Autowired
	private CertificationRenewalService service;
	
	@PutMapping("/{CertificateType}/{id}")
	@PreAuthorize("hasAuthority('ROLE_CA')")
	public ResponseEntity<CertificateResponse> renewalExistingCertificate(@PathVariable("id") String id,@PathVariable("CertificateType") String subjectDn,@RequestBody CertificateDetails certificateDetails) {
		try {
			return new ResponseEntity<CertificateResponse>(service.renewalCertificate(id, subjectDn, certificateDetails),HttpStatus.OK);
		} catch (OperatorCreationException | CertificateException | InvalidKeySpecException | NoSuchAlgorithmException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
