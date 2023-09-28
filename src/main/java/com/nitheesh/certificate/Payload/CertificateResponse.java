package com.nitheesh.certificate.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificateResponse {
	
	
	private String filename;
	
	private String fileType;
	

}
