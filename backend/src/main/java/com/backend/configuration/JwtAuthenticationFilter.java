package com.backend.configuration;

import com.backend.utills.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // Get token from the "Authorization" header
    String token = request.getHeader("Authorization");

    // Check if token is present and starts with "Bearer "
    if (token != null && token.startsWith("Bearer ")) {
      token = token.substring(7); // Remove the "Bearer " prefix

      try {
        // Validate token and extract claims
        Claims claims = jwtUtil.extractClaims(token);

        // Extract username (or subject) from claims
        String username = claims.getSubject();  // getSubject() returns the username

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          // If authentication context is empty, create a new authentication object
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              username, null, null); // You can add roles/authorities here if needed

          // Set the authentication in the context
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      } catch (Exception e) {
        // Handle invalid or expired token case (e.g., log the error, return 401 response)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Invalid or expired token");
        return;
      }
    }

    // Continue with the request processing
    filterChain.doFilter(request, response);
  }

  }


