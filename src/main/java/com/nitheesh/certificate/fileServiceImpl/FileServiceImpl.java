package com.nitheesh.certificate.fileServiceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

import org.springframework.stereotype.Service;

import com.nitheesh.certificate.fileService.FileService;
@Service
public class FileServiceImpl implements FileService {

	@Override
	public void addCertificateToFile(X509Certificate certificate, String filename) throws CertificateEncodingException, IOException {
		System.out.println(filename);
		
		FileOutputStream stream=new FileOutputStream(filename);
		stream.write(certificate.getEncoded());
		stream.close();
		
	}

}
