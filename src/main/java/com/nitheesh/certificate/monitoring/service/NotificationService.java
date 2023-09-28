package com.nitheesh.certificate.monitoring.service;

import java.security.cert.X509Certificate;

public interface NotificationService {

	
	public void sendNotification(X509Certificate cert);
	
	
}
