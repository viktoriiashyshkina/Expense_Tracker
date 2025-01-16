package com.backend.services;


import com.backend.entities.User;
import com.backend.repositories.UserRepository;
import com.backend.configuration.JwtService;
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
  private final JwtService jwtService;  // Your JWT service that generates tokens

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

  public void loginUser(String username, String password) {
    // Check if the user exists
    User user = userRepository.findByUsername(username);
    if (user == null) {
      return;  // User not found
    }

    // Compare the password using the password encoder
    if (!passwordEncoder.matches(password, user.getPassword())) {
      return;  // Password mismatch
    }
    // Generate JWT token
   String token = jwtService.generateToken(user);
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




