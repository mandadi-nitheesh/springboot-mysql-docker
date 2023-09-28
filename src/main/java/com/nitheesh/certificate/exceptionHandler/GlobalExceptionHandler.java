package com.nitheesh.certificate.exceptionHandler;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
		
	@ExceptionHandler(Exception.class)
	public ProblemDetail handleExceptions(Exception ex) {
		ProblemDetail errorDetail=null;
		
		if(ex instanceof BadCredentialsException) {
			errorDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),"invalid email/password");
		}
	
		if(ex instanceof ExpiredJwtException) {
			errorDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),"token expired please login again");
		}
		
		if(ex instanceof SignatureException) {
			errorDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),"token signature is not valid");
		}
		
		if(ex instanceof MalformedJwtException) {
			errorDetail=ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),"token is malformed invalid token");
		}
		
		
		return errorDetail;
	}
	
	
	
	
	
	
	
	
	

}
