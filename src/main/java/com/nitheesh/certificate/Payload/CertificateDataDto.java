package com.nitheesh.certificate.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CertificateDataDto {
private String fileId;
	
	private String fileName;
	
	private String fileType;
	private byte[] data;
	
	private byte[] privateKeyData;
	private byte[] publicKeyData;
	
	private String downloadurl;
	
	private String email;


}
