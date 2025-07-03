package com.ecommerce.userservice.security.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long expiration; // e.g. 3600000 for 1 hour default

    public String generateToken(UserDetails userDetails) {
       
    	return Jwts.builder()
                   .setSubject(userDetails.getUsername())
                   .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(expiration))
                   .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                   .compact();
    }

    public String extractUsername(String token) {
       
    	return Jwts.parserBuilder()
                   .setSigningKey(secretKey.getBytes())
                   .build()
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        
    	String username = extractUsername(token);
    	
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        
    	Date isExpired = Jwts.parserBuilder()
                			 .setSigningKey(secretKey.getBytes())
                			 .build()
                			 .parseClaimsJws(token)
                			 .getBody()
                			 .getExpiration();
    	
        return isExpired.before(new Date());
    }
    
    
    
}
