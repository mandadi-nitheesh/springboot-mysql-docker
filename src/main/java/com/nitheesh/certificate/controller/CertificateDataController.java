package com.nitheesh.certificate.controller;

import java.security.cert.CertificateException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nitheesh.certificate.Payload.CertificateDataDto;
import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateDetailsDto;
import com.nitheesh.certificate.entity.CertificateData;
import com.nitheesh.certificate.service.CetificateService;

@RestController
@RequestMapping("/certificate")
public class CertificateDataController {
	
	@Autowired
	private CetificateService cetificateService;
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_CA','ROLE_USER')")
	public ResponseEntity<CertificateDetails> getCertificateDataFromDb(@PathVariable("id") String id) throws CertificateException{
		
		return new ResponseEntity<CertificateDetails>(cetificateService.getDataFromDb(id),HttpStatus.OK);
		
	}
	
	
	// getting all the certificate from the database..
	
	@GetMapping("/allCertificateData")
	@PreAuthorize("hasAnyRole('ROLE_CA','ROLE_USER')")
	public ResponseEntity<List<CertificateDataDto>> getDataOfAllCertificates(){
		return new ResponseEntity<List<CertificateDataDto>>(cetificateService.getAllCertificateData(),HttpStatus.OK);
	}
	
	
	// download url api....
	@GetMapping("/download/{id}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<Resource> downloadCertificate(@PathVariable("id") String id){
		CertificateData data=cetificateService.getCertificateByid(id);
		
		System.out.println(data);
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(data.getFileType()))
				
				.header(HttpHeaders.CONTENT_DISPOSITION,"attatchment;filename=\""+data.getFileName()+"\"")
				
				.body(new ByteArrayResource(data.getData()));
	}
	
	@GetMapping("/CA/{id}")
	@PreAuthorize("hasAuthority('ROLE_CA')")
	public ResponseEntity<CertificateDetailsDto> getCertificateDetails(@PathVariable("id")String id) throws CertificateException{
		return new ResponseEntity<CertificateDetailsDto>(cetificateService.getDetailsOfCertificate(id), HttpStatus.OK);
	}

}
