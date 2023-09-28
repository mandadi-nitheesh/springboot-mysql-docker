package com.nitheesh.certificate.fileServiceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;

import org.springframework.stereotype.Service;

import com.nitheesh.certificate.fileService.KeyService;

@Service
public class KeyServiceImpl implements KeyService {

	@Override
	public void addKeyToFile(Key key,String filename) throws IOException {
		
		FileOutputStream stream=new FileOutputStream(filename);
		
		stream.write(key.getEncoded());
		
		stream.close();
		
	}

}
