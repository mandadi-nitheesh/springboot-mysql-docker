package com.nitheesh.certificate.monitoring.service;

import java.io.FileNotFoundException;
import java.security.cert.CertificateException;

public interface CertificateMonitoringService {
	
	
	public void checkIfCertificateExpired() throws CertificateException, FileNotFoundException;

}
