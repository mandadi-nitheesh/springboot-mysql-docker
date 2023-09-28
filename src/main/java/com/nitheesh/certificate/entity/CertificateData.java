package com.nitheesh.certificate.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CertificateData {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid",strategy = "uuid2")
	private String fileId;
	
	private String fileName;
	
	private String fileType;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] data;
	
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] privateKeyData;
	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] publicKeyData;

}
