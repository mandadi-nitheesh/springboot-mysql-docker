package com.nitheesh.certificate;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CetificateManagementApplication {
	
	
	public static void main(String[] args){
		SpringApplication.run(CetificateManagementApplication.class, args);	
		
		//CertificateFactory fac = CertificateFactory.getInstance("X509");
		
		
		
       // FileInputStream stream=new FileInputStream("/Users/mandadinitheesh/eclipse-workspace/Cetificate-Management/certificate/unsig.crt");
//        
//		
       // X509Certificate cert = (X509Certificate) fac.generateCertificate(stream);
//        
       // System.out.println("type is "+cert.getType());
//   
//		System.out.println("From: " + cert.getNotBefore());
//		System.out.println("Until: " + cert.getNotAfter());
//		
		//String string = cert.getIssuerX500Principal().toString();  String resultString="";
		
		//String subjectDn=cert.getSubjectDN().toString();
		
		//System.out.println(subjectDn);
//		
		//System.out.println(string);
//		
		//String array[]=string.split(",");
		
		//Arrays.stream(array).forEach((i)->System.out.println(i));
//		
		//String string2 = Arrays.stream(array).filter((s)->s.contains("EMAILADDRESS")).findFirst().get();
		//System.out.println(string2.substring(13));
		
//		int indexOf = string.indexOf("EMAILADDRESS");
//		System.out.println(indexOf);
        
		//System.out.println(cert.getIssuerX500Principal());
		
		//emailService.sendEmail("nitheesh.19bcn7095@vitap.ac.in","please renew your certificate as it is expiring with in seven days","about certification renewal");
		
				//SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
				
//				Calendar cal = Calendar.getInstance();
//				Date today = cal.getTime();
//				System.out.println(dateFormat.format(today));
//				cal.add(Calendar.DAY_OF_WEEK, 2); // to get previous year add -1
//				Date nextYear = cal.getTime();
//				System.out.println(dateFormat.format(nextYear));

		
	}

}
