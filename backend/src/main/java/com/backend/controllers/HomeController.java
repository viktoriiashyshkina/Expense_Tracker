package com.backend.controllers;

import com.backend.entities.User;
import com.backend.services.UserService;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<?> signup(@RequestBody User user) {
    // Validate input data
    if (user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
      return ResponseEntity.badRequest().body("All fields are required.");
    }

    // Register the user
    userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());

    // Optional: Automatically login the user after signup
    String token = userService.loginUser(user.getUsername(), user.getPassword());
    if (token != null) {
      return ResponseEntity.ok(Map.of(
          "message", "User registered successfully.",
          "token", token
      ));
    }

    // Default success response
    return ResponseEntity.ok("User registered successfully.");
  }



  @PostMapping("/login")
  public ResponseEntity<?> login(
      @RequestBody User user) {
    String token = userService.loginUser(user.getUsername(), user.getPassword());
    if (token == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
    }

    // Return the token in the response
    return ResponseEntity.ok(Map.of("token", token));
  }


}
