package com.backend.services;


import com.backend.entities.User;
import com.backend.repositories.UserRepository;
import com.backend.configuration.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;// Your JWT service that generates tokens
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public UserService(UserRepository userRepository, JwtService jwtService) {
    this.userRepository = userRepository;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.jwtService = jwtService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
   User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("No user found with username: " + username);
    }
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        user.getAuthorities()

    );
  }

//  public String loginUser(String username, String password) {
//    // Check if the user exists
//    User user = userRepository.findByUsername(username);
//    if (user == null) {
//      return null;  // User not found
//    }
//
//    // Compare the password using the password encoder
//    if (!passwordEncoder.matches(password, user.getPassword())) {
//      return null;  // Password mismatch
//    }
//    // Generate JWT token
//   return jwtService.generateToken(user);
//  }

  public String loginUser(String username, String password) {
    // Check if the user exists in the database
    User user = userRepository.findByUsername(username);
    if (user == null) {
      return null;  // User not found
    }

    // Compare the provided password with the stored password using the password encoder
    if (!passwordEncoder.matches(password, user.getPassword())) {
      return null;  // Password mismatch
    }

    // Log successful login attempt (don't log password or sensitive information)
    logger.info("User {} logged in successfully", username);

    // Generate JWT token for the authenticated user
    return jwtService.generateToken(user);
  }

  // Register a new user
  public void registerUser(String username, String password, String email) {
    User user = new User();
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
  }
}




