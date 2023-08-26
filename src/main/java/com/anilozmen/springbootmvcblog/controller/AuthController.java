package com.anilozmen.springbootmvcblog.controller;

import com.anilozmen.springbootmvcblog.entity.dto.request.UserRequestDTO;
import jakarta.validation.Valid;
import com.anilozmen.springbootmvcblog.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

  private final UserService userService;

  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/register")
  public String register(@ModelAttribute UserRequestDTO userRequestDTO) {
    return "register";
  }

  @PostMapping("/register")
  public String register(@Valid UserRequestDTO userRequestDTO, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      return "register";
    }

    userService.save(userRequestDTO);

    return "redirect:/login";
  }
}
