package com.nitheesh.certificate.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CertificateDetails {

	
	private String commonName;
	
	private String organization;
	
	private String organizationalUnit;
	
	
	private String country;
	
	private String locality;
	
	private String state;
	
	private String emailAddress;
	
	
	private String certificateType;
	
	
	
	
}
