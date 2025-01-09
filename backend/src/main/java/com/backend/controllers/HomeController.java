package com.backend.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

  @GetMapping("/home")
  public ResponseEntity<?> home(@RequestHeader(value = "Authorization", required = false) String token) {
    if (token != null && token.startsWith("Bearer ")) {
      // Add logic to validate the token here if necessary
      return ResponseEntity.ok(Map.of(
          "authenticated", true,
          "message", "Welcome back, authenticated user!"
      ));
    }
    return ResponseEntity.ok(Map.of(
        "authenticated", false,
        "message", "Welcome to the Home Page! Please log in to access more features."
    ));
  }
}
