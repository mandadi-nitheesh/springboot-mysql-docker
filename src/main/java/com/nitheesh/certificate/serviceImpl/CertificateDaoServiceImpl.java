package com.nitheesh.certificate.serviceImpl;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nitheesh.certificate.Payload.CertificateResponse;
import com.nitheesh.certificate.entity.CertificateData;
import com.nitheesh.certificate.repository.CertificateRepo;
import com.nitheesh.certificate.service.CertificateDaoService;

@Service
public class CertificateDaoServiceImpl implements CertificateDaoService {
	
	
	@Autowired
	private CertificateRepo certificateRepo;
	

	@Override
	public CertificateResponse saveCertificate(X509Certificate file,String fileName,PublicKey key,PrivateKey pkey) throws IOException, CertificateEncodingException {
        String certificateName=fileName.concat(".crt");
		
		CertificateData filedata=CertificateData.builder()
				.fileName(certificateName)
				.data(file.getEncoded())
				.privateKeyData(pkey.getEncoded())
				.publicKeyData(key.getEncoded())
				.fileType("application/x-x509-ca-cert").build();
		
		CertificateData savedFile = certificateRepo.save(filedata);
		
		System.out.println(savedFile);
		
		CertificateResponse certificateResponse=CertificateResponse.builder()
				.filename(savedFile.getFileName())
				.fileType(savedFile.getFileType())
				.build();
		
		return certificateResponse;
	}


	@Override
	public List<CertificateData> getAllCertificates() {
		
		List<CertificateData>  allCertificates = certificateRepo.findAll();
		
		//System.out.println(allCertificates);
		
		return allCertificates;
	}


	@Override
	public CertificateData getCertificateData(String id) {
		Optional<CertificateData> data = certificateRepo.findById(id);
		
		if(data.isEmpty()) {
			throw new RuntimeException("data is not found");
		}
		return data.get();
	}

}
