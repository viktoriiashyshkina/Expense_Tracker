package com.backend.controllers;

import com.backend.configuration.JwtService;
import com.backend.entities.User;
import com.backend.services.UserService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;
  private final JwtService jwtService;
  private final Logger logger = LoggerFactory.getLogger(UserController.class);

  public UserController(UserService userService, JwtService jwtService) {
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @GetMapping("/dashboard")
  public ResponseEntity<?> getDashboard() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    logger.info("Authentication: {}", authentication);
    if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: Please log in to access the dashboard.");
    }

    String username = authentication.getName();
    return ResponseEntity.ok("Welcome to the Dashboard, " + username + "!");
  }


}
