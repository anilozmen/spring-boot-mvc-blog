package com.anilozmen.springbootmvcblog.controller.common;

import com.anilozmen.springbootmvcblog.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

  @GetMapping("/profile")
  public String profile(@AuthenticationPrincipal User user, Model model) {
    model.addAttribute("user", user);
    return "user/profile";
  }
}
