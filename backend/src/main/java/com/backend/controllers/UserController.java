package com.backend.controllers;

import com.backend.services.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }



}
