package com.backend.configuration;

import com.backend.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtService {


  private static final String SECRET_KEY = "bXlfc2VjdXJlXzMyX2J5dGVfa2V5X2Zyb21fZ2VuZXJhdG9y";

  // Method to extract claims from JWT token
  public Claims extractClaims(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .build().parseSignedClaims(token)
        .getBody();
  }
  // Create the JWT token (you might want to set other claims like expiration date, roles, etc.)
  public String generateToken(User user) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration time
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

//  // Extract username from token
  public String extractUsername(String token) {
      Claims claims = extractClaims(token);
      String username = claims.getSubject();
      System.out.println("Extracted Username: " + username);  // Log the extracted username
      return username;
  }
  // Validate token
  public boolean isTokenExpired(String token) {
    return extractClaims(token).getExpiration().before(new Date());
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }




}

