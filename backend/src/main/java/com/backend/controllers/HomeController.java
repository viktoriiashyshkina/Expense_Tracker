package com.backend.controllers;

import com.backend.entities.User;
import com.backend.services.UserService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

  private final UserService userService;

  public HomeController(UserService userService) {
    this.userService = userService;
  }

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

  //Handle user registration
  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody User user) {
    userService.registerUser(user.getUsername(), user.getEmail(), user.getPassword());
    return ResponseEntity.ok("User registered successfully");
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(
      @RequestBody User user) {
    userService.loginUser(user.getUsername(), user.getPassword());
    return ResponseEntity.ok("User logged in successfully");

  }


}
