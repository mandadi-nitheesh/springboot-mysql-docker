package com.nitheesh.certificate.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nitheesh.certificate.KeyPairService.KeyPairGeneratorService;
import com.nitheesh.certificate.Payload.CertificateDataDto;
import com.nitheesh.certificate.Payload.CertificateDetails;
import com.nitheesh.certificate.Payload.CertificateDetailsDto;
import com.nitheesh.certificate.Payload.CertificateResponse;
import com.nitheesh.certificate.entity.CertificateData;
import com.nitheesh.certificate.fileService.KeyService;
import com.nitheesh.certificate.service.CertificateDaoService;
import com.nitheesh.certificate.service.CetificateService;

@Service
public class CertificateServiceImpl implements CetificateService {
	
	
	// key service to store the private key in a .key file..
	
	@Autowired
	private KeyService keyService;
	
	@Autowired
	private KeyPairGeneratorService keyPairGeneratorService;
	
	@Autowired
	private CertificateDaoService certificateDaoService;
	
	@Value("${certificate.selfsignedKey.filepath}")
	private String selfSignedkeyfilename;
	
	@Value("{certificate.cacertificatekey.filepath}")
	private String cafileName;
	
	@Value("${certificate.unsignedcertificatekey.filepath}")
	private String unsignedKeyFileName;
	
	@Value("${certicate.signedcertificatekey.filepath}")
	private String signedKeyFilename;
	
	// generating self signed certificate...

	@Override
	public X509Certificate generateSelfSignedCertificate(CertificateDetails details) throws OperatorCreationException, NoSuchAlgorithmException, CertificateException, IOException {
		
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
      
            
            String name=String.format("CN=%s, O=%s, OU=%s, C=%s, L=%s, ST=%s, emailAddress=%s",details.getCommonName(),details.getOrganization(),details.getOrganizationalUnit(),details.getCountry(),details.getLocality(),details.getState(),details.getEmailAddress());

            X500Name subjectName = new X500Name(name);

            BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());
            
            //current date...

            Date startDate = new Date();
            
            // valid upto 0ne year..
            
            Date endDate = new Date(startDate.getTime() + 365 * 24 * 60 * 60 * 1000); // 1 year validity
            
            SubjectPublicKeyInfo subPubKeyInfo =  SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
            
            // certificate builder...
            
            X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(
                    subjectName,
                    serialNumber,
                    startDate,
                    endDate,
                    new X500Name("CN=SelfSigned"),
                    subPubKeyInfo
            );
            
            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA")
                    .build(keyPair.getPrivate());

            X509Certificate selfSignedCertificate = new JcaX509CertificateConverter()
                    .getCertificate(certificateBuilder.build(contentSigner));
           
            // storing the key to a file..
            keyService.addKeyToFile(keyPair.getPrivate(),selfSignedkeyfilename);
            
		return selfSignedCertificate;
				
}
	
	// genearting signed certificate....
	

	@Override
	public CertificateResponse generateSignedCertificate(CertificateDetails details) throws OperatorCreationException, CertificateException, IOException, NoSuchAlgorithmException {
		
		KeyPair SignedkeyPair=keyPairGeneratorService.generateKetPair();
	
		
		KeyPair caKeyPair=keyPairGeneratorService.generateKetPair();
		
		
		String name=String.format("CN=%s, O=%s, OU=%s, C=%s, L=%s, ST=%s, emailAddress=%s",details.getCommonName(),details.getOrganization(),details.getOrganizationalUnit(),details.getCountry(),details.getLocality(),details.getState(),details.getEmailAddress());
		
		//X509Certificate caCertificate=generaCertificate(name, caKeyPair, "CN=CA",caKeyPair.getPublic(),cafileName);
		
		X509Certificate Signedcertificate=generaCertificate(name,caKeyPair, "CN=Signed",SignedkeyPair.getPublic(),signedKeyFilename);
		
		String filename=details.getCommonName().concat("-certificate");
		
		CertificateResponse response = certificateDaoService.saveCertificate(Signedcertificate,filename,SignedkeyPair.getPublic(),caKeyPair.getPrivate());
		System.out.println(response);
		
		return response ;
	}

	
	
	
	
	//genearating ca certificate..
	

	@Override
	public CertificateResponse generateCaCertificate(CertificateDetails details) throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, IOException {
		
		KeyPair keyPair=keyPairGeneratorService.generateKetPair();
		
		String name=String.format("CN=%s, O=%s, OU=%s, C=%s, L=%s, ST=%s, emailAddress=%s",details.getCommonName(),details.getOrganization(),details.getOrganizationalUnit(),details.getCountry(),details.getLocality(),details.getState(),details.getEmailAddress());
		
		
		X509Certificate CaCertificate=generaCertificate(name, keyPair,"CN=CA",keyPair.getPublic(),cafileName);
		
        String filename=details.getCommonName().concat("-certificate");
		
		CertificateResponse response = certificateDaoService.saveCertificate(CaCertificate,filename,keyPair.getPublic(),keyPair.getPrivate());
		
		
		return response;
	}

	
	// generating unsigned certificate...

	@Override
	public CertificateResponse generateUnsignedCertificate(CertificateDetails details) throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, IOException {
KeyPair keyPair=keyPairGeneratorService.generateKetPair();
		
		String name=String.format("CN=%s, O=%s, OU=%s, C=%s, L=%s, ST=%s, emailAddress=%s",details.getCommonName(),details.getOrganization(),details.getOrganizationalUnit(),details.getCountry(),details.getLocality(),details.getState(),details.getEmailAddress());
		
		X509Certificate unsignedCertificate=generaCertificate(name, keyPair,"CN=Unsigned",keyPair.getPublic(),unsignedKeyFileName);
		
String filename=details.getCommonName().concat("-certificate");
		
		CertificateResponse response = certificateDaoService.saveCertificate(unsignedCertificate,filename,keyPair.getPublic(),keyPair.getPrivate());
		
		
		return response;
	}
	
	
	
	// method to generate the certificate ...
	
	
	public X509Certificate generaCertificate(String name,KeyPair keyPair,String subjectDN,PublicKey key,String filename) throws OperatorCreationException, CertificateException, IOException {
		
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
                 .build(keyPair.getPrivate());

         X509Certificate Certificate = new JcaX509CertificateConverter()
                 .getCertificate(certificateBuilder.build(contentSigner));
        
         // storing the key to a file..
         //keyService.addKeyToFile(keyPair.getPrivate(),filename);
         
		return Certificate;
	}
	
	// to get all the details of the user from the certificate...

	@Override
	public CertificateDetails getDataFromDb(String id) throws CertificateException {
		
		CertificateData certificateData=certificateDaoService.getCertificateData(id);
		
		CertificateFactory fac = CertificateFactory.getInstance("X509");
		
		ByteArrayInputStream stream=new ByteArrayInputStream(certificateData.getData());
		
		X509Certificate cert = (X509Certificate) fac.generateCertificate(stream);
		
		String string = cert.getIssuerX500Principal().toString();
		
		String sub[]=cert.getSubjectDN().toString().split("=");
		
		String[] split = string.split(",");

			String commonName=getStringFromArray(split,"CN=");
			String organisation=getStringFromArray(split,"O=");
			String locality=getStringFromArray(split,"L=");
			String country=getStringFromArray(split,"C=");
			String state=getStringFromArray(split,"ST=");
			String email=getStringFromArray(split,"EMAILADDRESS=");
			String organisationalUnit=getStringFromArray(split,"OU=");
			String subjectDn=sub[1];
			CertificateDetails details=CertificateDetails.builder()
					.commonName(commonName)
					.country(country)
					.emailAddress(email)
					.locality(locality)
					.state(state)
					.organization(organisation)
					.organizationalUnit(organisationalUnit)
					.certificateType(subjectDn)
					.build();
		
		
		return details;
	}
	
	// to get details of the certificate for ca role ..
	
	@Override
	public CertificateDetailsDto getDetailsOfCertificate(String id) throws CertificateException {
CertificateData certificateData=certificateDaoService.getCertificateData(id);
		
		CertificateFactory fac = CertificateFactory.getInstance("X509");
		
		ByteArrayInputStream stream=new ByteArrayInputStream(certificateData.getData());
		
		X509Certificate cert = (X509Certificate) fac.generateCertificate(stream);
		
		String string = cert.getIssuerX500Principal().toString();
		
		String sub[]=cert.getSubjectDN().toString().split("=");
		
		String[] split = string.split(",");

			String commonName=getStringFromArray(split,"CN=");
			
			String creationDate=cert.getNotBefore().toString();
			
			String expiryDate=cert.getNotAfter().toString();
			
			CertificateDetailsDto dto=CertificateDetailsDto.builder().commonName(commonName).createdDate(creationDate).expiryDate(expiryDate).build();
			
		return dto;
	}

	
	// to get all the certificate from the database...
	
	@Override
	public List<CertificateDataDto> getAllCertificateData() {
		List<CertificateData> datas=certificateDaoService.getAllCertificates();
		List<CertificateDataDto> dtos=datas.stream().map((certificatedata)->fromCertificateDataToCertificateDataDto(certificatedata) ).toList();
		
		
		return dtos;
	}
	
	// get certificate data from db by id..
	
	@Override
	public CertificateData getCertificateByid(String id) {
		CertificateData data=certificateDaoService.getCertificateData(id);
		return data;
	}

	// from certificateTocertificateDto
	
	public CertificateDataDto fromCertificateDataToCertificateDataDto(CertificateData certificateData){
		
CertificateFactory fac=null;
try {
	fac = CertificateFactory.getInstance("X509");
} catch (CertificateException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
		ByteArrayInputStream stream=new ByteArrayInputStream(certificateData.getData());
		
		X509Certificate cert=null;
		try {
			cert = (X509Certificate) fac.generateCertificate(stream);
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String string = cert.getIssuerX500Principal().toString();
		
		String sub[]=cert.getSubjectDN().toString().split("=");
		
		String[] split = string.split(",");
		String email=getStringFromArray(split,"EMAILADDRESS=");

		
		String downloadurl=ServletUriComponentsBuilder.fromCurrentContextPath().path("create/certificate/download/").path(certificateData.getFileId()).toUriString();;
		
		CertificateDataDto dto=CertificateDataDto.builder()
				.fileId(certificateData.getFileId())
				.fileName(certificateData.getFileName())
				.fileType(certificateData.getFileType())
				.data(certificateData.getData())
				.privateKeyData(certificateData.getPrivateKeyData())
				.publicKeyData(certificateData.getPublicKeyData())
				.downloadurl(downloadurl)
				.email(email)
				.build();
		
		return dto;
	}

	
	
	public String getStringFromArray(String array[],String element) {
		
		String string = Arrays.stream(array).filter((f)->f.contains(element)).findFirst().get();
		String reString = string.strip();
	return	reString.substring(element.length());
	}

	
	

		
	
	
}
