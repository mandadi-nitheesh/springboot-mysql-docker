package com.nitheesh.certificate.Jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtHelper {
	
	private long token_validity=1*60*60;
	
	
	private String key="afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
	
	// generating token....
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims=new HashMap<>();
		
		return dogenerateToken(claims,userDetails.getUsername());
	}
	
	public String dogenerateToken(Map<String, Object> claims,String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+token_validity*1000)).signWith(SignatureAlgorithm.HS512,key).compact();
	}
	
	
	
	public String getUserNameFromToken(String token) {
		return getClaimFromTheToken(token, (c)->c.getSubject());
	}
	
	
	public Date getExpirationFromTheToken(String token) {
		return getClaimFromTheToken(token,(c)->c.getExpiration());
	}
	
	public <T> T getClaimFromTheToken(String token,Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
	}
	
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
	}
	
	
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username=getUserNameFromToken(token);
		return username.equals(userDetails.getUsername())&& !isTokenExpired(token);
		
	}
	
	
	public boolean isTokenExpired(String token) {
	final Date expireDate=getExpirationFromTheToken(token);
	return expireDate.before(new Date());
	}
	
	

}
