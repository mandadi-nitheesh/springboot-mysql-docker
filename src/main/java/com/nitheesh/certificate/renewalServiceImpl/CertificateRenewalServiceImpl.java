package com.nitheesh.certificate.renewalServiceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateResponse;
import com.nitheesh.certificate.entity.CertificateData;
import com.nitheesh.certificate.renewalService.CertificationRenewalService;
import com.nitheesh.certificate.repository.CertificateRepo;

@Service
public class CertificateRenewalServiceImpl implements CertificationRenewalService {
	
	
	@Autowired
	private CertificateRepo certificateRepo;
	
	
	

	@Override
	public CertificateResponse renewalCertificate(String id,String subjectDn,CertificateDetails details) throws OperatorCreationException, CertificateException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
		
		Optional<CertificateData> certificateData = certificateRepo.findById(id);
		

		if(certificateData.isPresent()) {
			
			byte[] publicKeyData = certificateData.get().getPublicKeyData();
			
			byte[] privateKeyData = certificateData.get().getPrivateKeyData();
			
			
			String name=String.format("CN=%s, O=%s, OU=%s, C=%s, L=%s, ST=%s, emailAddress=%s",details.getCommonName(),details.getOrganization(),details.getOrganizationalUnit(),details.getCountry(),details.getLocality(),details.getState(),details.getEmailAddress());
			
			X509Certificate generaCertificate = generaCertificate(name,generatePrivateKey(privateKeyData),String.format("CN=%s",subjectDn),generatePubliKey(publicKeyData), null);
			
			CertificateData data=CertificateData.builder()
					.fileId(id)
					.fileName(certificateData.get().getFileName())
					.fileType(certificateData.get().getFileType())
					.data(generaCertificate.getEncoded())
					.publicKeyData(publicKeyData)
					.privateKeyData(privateKeyData)
					.build();		
			CertificateData saved = certificateRepo.save(data);
			
			CertificateResponse response=CertificateResponse.builder().fileType(saved.getFileType()).filename(saved.getFileName()).build();
			
			return response;
		}
		else {
			throw new RuntimeException("no data found to the given id"+id);
		}
		
	
	}
	
	// to generate a certificate ....
	
	public X509Certificate generaCertificate(String name,PrivateKey pkey,String subjectDN,PublicKey key,String filename) throws OperatorCreationException, CertificateException, IOException {
		
		 X500Name subjectName = new X500Name(name);

        BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
		
        Calendar cal = Calendar.getInstance();
        
        // present date and time...
		 Date startDate = cal.getTime();
		 
		 // expires in two days..
		 cal.add(Calendar.YEAR, 1);
        
        // valid upto 0ne year..
        
        Date endDate = cal.getTime(); // 1 year validity
        
        SubjectPublicKeyInfo subPubKeyInfo =  SubjectPublicKeyInfo.getInstance(key.getEncoded());
        
        // certificate builder...
        
        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                subjectName,
                serialNumber,
                startDate,
                endDate,
                new X500Name(subjectDN),
                subPubKeyInfo
        );
        
        
        
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA")
                .build(pkey);

        X509Certificate Certificate = new JcaX509CertificateConverter()
                .getCertificate(certificateBuilder.build(contentSigner));
       
        // storing the key to a file..
        //keyService.addKeyToFile(keyPair.getPrivate(),filename);
        
		return Certificate;
	}
	
	
	public PublicKey generatePubliKey(byte[] publikKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
		//ByteArrayInputStream stream=new ByteArrayInputStream(publikKey);
		
		PublicKey publicKey =  KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publikKey));
        return publicKey;
		
	}
	
	
public PrivateKey generatePrivateKey(byte[] privatekey) throws InvalidKeySpecException, NoSuchAlgorithmException {
	
	PrivateKey privateKey=KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privatekey));
		return privateKey;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
