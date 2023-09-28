package com.nitheesh.certificate.fileService;

import java.io.IOException;
import java.security.Key;

public interface KeyService {
	
	
	public void addKeyToFile(Key key,String filename) throws IOException;

}
