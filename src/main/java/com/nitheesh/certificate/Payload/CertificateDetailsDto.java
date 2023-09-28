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
public class CertificateDetailsDto {

	private String commonName;
	
	private String createdDate;
	
	private String expiryDate;
}
