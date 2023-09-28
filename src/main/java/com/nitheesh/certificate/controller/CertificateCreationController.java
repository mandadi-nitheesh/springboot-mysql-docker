package com.nitheesh.certificate.controller;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitheesh.certificate.Payload.CertificateDataDto;
import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateResponse;
import com.nitheesh.certificate.entity.CertificateData;
import com.nitheesh.certificate.fileService.FileService;
import com.nitheesh.certificate.service.CetificateService;

import lombok.Data;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/create/certificate")
@Data
public class CertificateCreationController {
	
	@Autowired
	private CetificateService cetificateService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${certificate.selfsigned.filepath}")
	private String filename;
	
	@Value("${certificate.cacertificate.filepath}")
	private String caFileName;
	
	@Value("${certificate.unsignedcertificate.filepath}")
	private String unsignedFileName;
	
	@Value("${certificate.signedcertificate.filepath}")
	private String signedFilename;
	
	
	
	//self-signed certificate....  controller
	
	
	@PostMapping("/SelfSigned")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<String> generateSelfSigned(@RequestBody CertificateDetails details) {
		
		try {
		X509Certificate certificate=cetificateService.generateSelfSignedCertificate(details);
		
		fileService.addCertificateToFile(certificate, filename);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("successfully created a cetificate");
		
		}
		catch (Exception e) {
			e.printStackTrace();
		return	ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("something wrong happened certificate not created");
		}
		
	}
	
	
	// signed certificate controller
	
	

	@PostMapping("/Signed")
	public ResponseEntity<CertificateResponse> generateSignedCertificate(@RequestBody CertificateDetails details){
		try {
			//X509Certificate certificate=cetificateService.generateSignedCertificate(details);
			//fileService.addCertificateToFile(certificate, signedFilename);
			
			
			return ResponseEntity.status(HttpStatus.CREATED).body(cetificateService.generateSignedCertificate(details));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return	null;
		}
		
	}
	
	
	// CA certificate controller...
	
	
	
	@PostMapping("/CA")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<CertificateResponse> generateCaCertificate(@RequestBody CertificateDetails details){
		try {
			
			
			//X509Certificate certificate=cetificateService.generateCaCertificate(details);
			
			//fileService.addCertificateToFile(certificate, caFileName);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(cetificateService.generateCaCertificate(details));
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	
	//unsigned certificate controller...
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/Unsigned")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<CertificateResponse> generateUnSignedCertificate(@RequestBody CertificateDetails details){
		try {
			//X509Certificate certificate=cetificateService.generateUnsignedCertificate(details);
			
			//fileService.addCertificateToFile(certificate, unsignedFileName);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(cetificateService.generateSignedCertificate(details));
		}
		catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	
//	// getting details inside of the certificate ....
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<CertificateDetails> getCertificateDataFromDb(@PathVariable("id") String id) throws CertificateException{
//		
//		return new ResponseEntity<CertificateDetails>(cetificateService.getDataFromDb(id),HttpStatus.OK);
//		
//	}
//	
//	
//	// getting all the certificate from the database..
//	
//	@GetMapping("/allCertificateData")
//	public ResponseEntity<List<CertificateDataDto>> getDataOfAllCertificates(){
//		return new ResponseEntity<List<CertificateDataDto>>(cetificateService.getAllCertificateData(),HttpStatus.OK);
//	}
//	
//	
//	// download url api....
//	@GetMapping("/download/{id}")
//	public ResponseEntity<Resource> downloadCertificate(@PathVariable("id") String id){
//		CertificateData data=cetificateService.getCertificateByid(id);
//		
//		System.out.println(data);
//		
//		return ResponseEntity.ok().contentType(MediaType.parseMediaType(data.getFileType()))
//				
//				.header(HttpHeaders.CONTENT_DISPOSITION,"attatchment;filename=\""+data.getFileName()+"\"")
//				
//				.body(new ByteArrayResource(data.getData()));
//	}

}
