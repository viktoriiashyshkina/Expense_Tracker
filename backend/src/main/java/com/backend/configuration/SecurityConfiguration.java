package com.backend.configuration;

import com.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {


  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Autowired
  public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    // Configure HTTP security
    http
        .securityMatcher("/api/home")
        .csrf().disable()
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers( "/api/home", "/api/signup", "/api/login", "/api/transactions/addTransaction", "/api/transactions/updateTransaction", "/api/categories").permitAll() // Public endpoints
                .requestMatchers("/dashboard").authenticated() // All other requests require authentication
        )
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before default auth filter

    return http.build();
  }

}


