package com.backend.utills;

import com.backend.entities.User;
import com.backend.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtService {


  private String secretKey = "vika"; // Change this to a more secure key for production



  // Method to extract claims from JWT token
  public Claims extractClaims(String token) {
    return Jwts.parser()
        .setSigningKey(secretKey)
        .build().parseSignedClaims(token)
        .getBody();
  }
  // Create the JWT token (you might want to set other claims like expiration date, roles, etc.)
  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration time
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  // Extract username from token
  public String extractUsername(String token) {
    return extractClaims(token).getSubject();
  }

  // Validate token
  public boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().before(new Date());
  }
  // Validate the token
  public boolean validateToken(String token) {
    return !isTokenExpired(token); // You can add more checks here if needed
  }


}

